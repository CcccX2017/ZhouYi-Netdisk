package cn.codex.netdisk.service.impl;

import cn.codex.netdisk.common.constants.Const;
import cn.codex.netdisk.common.constants.ReturnMessage;
import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.common.enums.ResponseCode;
import cn.codex.netdisk.common.exception.ErrorException;
import cn.codex.netdisk.common.utils.RegexUtil;
import cn.codex.netdisk.common.utils.SecurityUtil;
import cn.codex.netdisk.dao.FoldersMapper;
import cn.codex.netdisk.model.dtos.FileRenameDto;
import cn.codex.netdisk.model.entity.Folders;
import cn.codex.netdisk.service.IFoldersService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 目录表 服务实现类
 * </p>
 *
 * @author codex
 * @since 2021-01-28
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FoldersServiceImpl extends ServiceImpl<FoldersMapper, Folders> implements IFoldersService {

    @Autowired
    private FoldersMapper foldersMapper;

    @Autowired
    private Snowflake snowflake;

    /**
     * 新建文件夹
     *
     * @param folderName 文件夹名称
     * @param dir        文件夹路径
     * @return 结果
     */
    @Override
    public ServerResponse addFolder(String folderName, String dir) {
        String username = SecurityUtil.getUsername();
        return addFolder(folderName, dir, username);
    }

    /**
     * 新建文件夹
     *
     * @param folderName 文件夹名称
     * @param dir        文件夹路径
     * @param username   用户名
     * @return 结果
     */
    @Override
    public ServerResponse addFolder(String folderName, String dir, String username) {
        ServerResponse responseServer = checkData(dir, folderName);
        if (responseServer != null) {
            return responseServer;
        }

        // 判断文件夹名称是否重复，重复则重新命名
        Long count = foldersMapper.selectCount(new QueryWrapper<Folders>().eq(Folders.FOLDER_NAME,
                folderName).eq(Folders.DIR, dir).eq(Folders.CREATOR, username));

        Folders folders = new Folders();

        if (count > 0) {
            // 重命名文件夹名称为：文件夹名称_年月日_时分秒
            String suffix = DateUtil.format(new Date(), "_yyyyMMdd_HHmmss");
            folders.setFolderName(folderName + suffix);
        } else {
            folders.setFolderName(folderName);
        }
        folders.setFolderId(snowflake.nextId());
        folders.setCreator(username);
        folders.setDir(dir);

        return foldersMapper.insert(folders) > 0
                ? ServerResponse.createBySuccessMessage(ReturnMessage.CREATE_FOLDER_SUCCESS)
                : ServerResponse.createByErrorMessage(ReturnMessage.CREATE_FOLDER_ERROR);
    }

    /**
     * 重命名文件夹
     *
     * @param folderId 文件夹ID
     * @param dto      文件重命名数据传输对象
     * @return 结果
     */
    @Override
    public ServerResponse rename(Long folderId, FileRenameDto dto) {
        // 第一次重命名
        if (Strings.isNullOrEmpty(dto.getType())) {
            return firstRename(folderId, dto);
        } else if (Const.NEW_COPY.equals(dto.getType())) {
            // 出现同名文件时，用户选择保留两者之后的第二次尝试重命名
            return retryRename(folderId, dto);
        } else {
            return ServerResponse.createByErrorMessage(ReturnMessage.ILLEGAL_REQUEST);
        }
    }

    /**
     * 移动文件夹
     *
     * @param folderId 文件夹ID
     * @param dir      目录
     * @return 移动文件夹结果
     */
    @Override
    public ServerResponse move(Long[] folderId, String dir) {
        if (folderId == null || folderId.length == 0 || Strings.isNullOrEmpty(dir)) {
            throw new ErrorException(ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        if (folderId.length == 1) {
            return foldersMapper.moveFolder(folderId[0], dir) > 0
                    ? ServerResponse.createBySuccessMessage(ReturnMessage.MOVE_FOLDER_SUCCESS)
                    : ServerResponse.createByErrorMessage(ReturnMessage.MOVE_FOLDER_ERROR);
        }

        return foldersMapper.batchMoveFolder(folderId, dir) > 0
                ? ServerResponse.createBySuccessMessage(ReturnMessage.MOVE_FOLDER_SUCCESS)
                : ServerResponse.createByErrorMessage(ReturnMessage.MOVE_FOLDER_ERROR);
    }

    /**
     * 第一次重命名
     */
    private ServerResponse<?> firstRename(Long folderId, FileRenameDto dto) {
        // 判断文件夹名称是否重复，重复则提示用户存在重名文件
        Long count = foldersMapper.selectCount(new QueryWrapper<Folders>().eq(Folders.FOLDER_NAME, dto.getNewName()).eq(Folders.DIR, dto.getDir()).eq(Folders.CREATOR, SecurityUtil.getUsername()).ne(Folders.FOLDER_ID, folderId));
        if (count != null && count > 0) {
            // 存在重名文件，提示用户是否保留两者
            return ServerResponse.createByErrorCodeMessage(ResponseCode.FOLDER_NAME_REPEAT.getCode(),
                    ResponseCode.FOLDER_NAME_REPEAT.getDesc());
        }

        // 不存在重名文件夹，重命名文件夹
        Folders folders = new Folders();
        folders.setFolderId(folderId);
        folders.setFolderName(dto.getNewName());

        return foldersMapper.updateById(folders) > 0
                ? ServerResponse.createBySuccessMessage(ReturnMessage.RENAME_SUCCESS)
                : ServerResponse.createByErrorMessage(ReturnMessage.RENAME_ERROR);
    }

    /**
     * 第二次重命名
     */
    private ServerResponse retryRename(Long folderId, FileRenameDto dto) {
        Folders folders = new Folders();
        folders.setFolderId(folderId);

        // 重命名文件夹名称
        try {
            int count = foldersMapper.selectRenameCount(folderId, dto.getNewName(), dto.getDir(),
                    SecurityUtil.getUsername());
            folders.setFolderName(dto.getNewName() + "(" + count + ")");

            return foldersMapper.updateById(folders) > 0
                    ? ServerResponse.createBySuccess(ReturnMessage.RENAME_SUCCESS, folders.getFolderName())
                    : ServerResponse.createByErrorMessage(ReturnMessage.RENAME_ERROR);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage(ReturnMessage.RENAME_ERROR);
        }
    }

    private ServerResponse checkData(String dir, String newFolderName) {
        if (Strings.isNullOrEmpty(newFolderName.trim())) {
            return ServerResponse.createByErrorMessage(ReturnMessage.FILENAME_NOT_BE_EMPTY);
        }

        if (dir == null) {
            return ServerResponse.createByErrorMessage(ReturnMessage.DIR_NOT_BE_EMPTY);
        }

        // 判断文件夹名称是否超长
        if (newFolderName.getBytes().length > Const.MAX_FILE_NAME_LENGTH) {
            return ServerResponse.createByErrorMessage("文件夹名称不能超过" + Const.MAX_FILE_NAME_LENGTH + "字节");
        }

        // 判断文件名是否合法
        if (!RegexUtil.isFileNameHaveSpecialCharacters(newFolderName)) {
            return ServerResponse.createByErrorMessage(ReturnMessage.FILE_NAME_ILLEGAL);
        }

        return null;
    }
}
