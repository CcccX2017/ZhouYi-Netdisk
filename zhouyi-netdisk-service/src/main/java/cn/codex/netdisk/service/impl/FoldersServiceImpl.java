package cn.codex.netdisk.service.impl;

import cn.codex.netdisk.common.constants.ReturnMessage;
import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.common.enums.ResponseCode;
import cn.codex.netdisk.common.utils.SecurityUtil;
import cn.codex.netdisk.dao.FoldersMapper;
import cn.codex.netdisk.model.entity.Folders;
import cn.codex.netdisk.service.IFoldersService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class FoldersServiceImpl extends ServiceImpl<FoldersMapper, Folders> implements IFoldersService {
    
    @Autowired
    private FoldersMapper foldersMapper;
    
    /**
     * 新建文件夹
     *
     * @param folders 目录实体类
     * @return 结果
     */
    @Override
    public ServerResponse addFolder(Folders folders) {
        // 判断文件夹名称是否为空
        if (Strings.isNullOrEmpty(folders.getFolderName().trim())) {
            return ServerResponse.createByErrorMessage(ReturnMessage.FILENAME_NOT_BE_EMMPTY);
        }
        // 判断文件夹名称是否超长
        int length = folders.getFolderName().getBytes().length;
        System.out.println(length);
        if (length > 32) {
            return ServerResponse.createByErrorMessage("文件夹名称不能超过32字节");
        }
        String username = SecurityUtil.getUsername();
        folders.setCreator(username);
        // 判断文件夹名称是否重复，重复则重新命名
        Integer count = foldersMapper.selectCount(new QueryWrapper<Folders>().eq(Folders.FOLDER_NAME,
                folders.getFolderName()).eq(Folders.PARENT_ID, folders.getParentId()).eq(Folders.CREATOR, username));
        if (count > 0) {
            // 重命名文件夹名称为：文件夹名称_年月日_时分秒
            String suffix = DateUtil.format(new Date(), "_yyyyMMdd_HHmmss");
            folders.setFolderName(folders.getFolderName() + suffix);
        }
        return foldersMapper.insert(folders) > 0
                ? ServerResponse.createBySuccessMessage("创建文件夹成功")
                : ServerResponse.createByErrorMessage("创建文件夹失败");
    }
    
    /**
     * 重命名文件夹
     *
     * @param folderId      文件夹ID
     * @param parentId      父文件夹ID
     * @param newFolderName 新文件夹名称
     * @return 结果
     */
    @Override
    public ServerResponse rename(Long folderId, Long parentId, String newFolderName) {
        if (Strings.isNullOrEmpty(newFolderName.trim())) {
            return ServerResponse.createByErrorMessage(ReturnMessage.FILENAME_NOT_BE_EMMPTY);
        }
        
        if (parentId == null){
            return ServerResponse.createByErrorMessage(ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        
        // 判断文件夹名称是否超长
        int length = newFolderName.getBytes().length;
        System.out.println(length);
        if (length > 32) {
            return ServerResponse.createByErrorMessage("文件夹名称不能超过32字节");
        }
        // 判断文件夹名称是否重复，重复则重新命名
        Integer count =
                foldersMapper.selectCount(new QueryWrapper<Folders>().eq(Folders.FOLDER_NAME, newFolderName).eq(Folders.PARENT_ID, parentId));
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
}
