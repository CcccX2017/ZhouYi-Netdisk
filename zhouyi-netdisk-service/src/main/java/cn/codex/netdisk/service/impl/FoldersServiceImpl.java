package cn.codex.netdisk.service.impl;

import cn.codex.netdisk.common.constants.Const;
import cn.codex.netdisk.common.constants.ReturnMessage;
import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.common.enums.ResponseCode;
import cn.codex.netdisk.common.exception.ErrorException;
import cn.codex.netdisk.common.utils.RegexUtil;
import cn.codex.netdisk.common.utils.SecurityUtil;
import cn.codex.netdisk.dao.FoldersMapper;
import cn.codex.netdisk.model.entity.Folders;
import cn.codex.netdisk.service.IFoldersService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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
        Integer count = foldersMapper.selectCount(new QueryWrapper<Folders>().eq(Folders.FOLDER_NAME,
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
     * @param folderId      文件夹ID
     * @param dir           目录
     * @param newFolderName 新文件夹名称
     * @return 结果
     */
    @Override
    public ServerResponse rename(Long folderId, String dir, String newFolderName) {
        ServerResponse responseServer = checkData(dir, newFolderName);
        if (responseServer != null) {
            return responseServer;
        }
        
        // 判断文件夹名称是否重复，重复则重新命名
        Integer count =
                foldersMapper.selectCount(new QueryWrapper<Folders>().eq(Folders.FOLDER_NAME, newFolderName).eq(Folders.DIR, dir).eq(Folders.CREATOR, SecurityUtil.getUsername()).ne(Folders.FOLDER_ID, folderId));
        if (count > 0) {
            return ServerResponse.createByErrorCodeMeaage(ResponseCode.FOLDER_NAME_REPEAT.getCode(), ResponseCode.FOLDER_NAME_REPEAT.getDesc());
        }
        
        Folders folders = new Folders();
        folders.setFolderId(folderId);
        folders.setFolderName(newFolderName);
        return foldersMapper.updateById(folders) > 0
                ? ServerResponse.createBySuccessMessage(ReturnMessage.RENAME_SUCCESS)
                : ServerResponse.createByErrorMessage(ReturnMessage.RENAME_ERROR);
    }
    
    /**
     * 重命名文件夹(文件夹名重复处理)
     *
     * @param folderId      文件夹ID
     * @param dir           目录
     * @param newFolderName 新文件夹名称
     * @return 结果
     */
    @Override
    public ServerResponse renameRepeat(Long folderId, String dir, String newFolderName) {
        ServerResponse responseServer = checkData(dir, newFolderName);
        if (responseServer != null) {
            return responseServer;
        }
        
        Folders folders = new Folders();
        folders.setFolderId(folderId);
        
        // 重命名文件夹名称
        try {
            int count = foldersMapper.selectRenameCount(folderId, newFolderName, dir, SecurityUtil.getUsername());
            folders.setFolderName(newFolderName + "(" + count + ")");
            
            return foldersMapper.updateById(folders) > 0
                    ? ServerResponse.createBySuccessMessage(ReturnMessage.RENAME_SUCCESS)
                    : ServerResponse.createByErrorMessage(ReturnMessage.RENAME_ERROR);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage(ReturnMessage.RENAME_ERROR);
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
        if (!RegexUtil.isFileNameHaveSpecialCharacters(newFolderName)){
            return ServerResponse.createByErrorMessage(ReturnMessage.FILE_NAME_ILLEGAL);
        }

        return null;
    }
}
