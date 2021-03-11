package cn.codex.netdisk.service;

import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.model.entity.Folders;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 目录表 服务类
 * </p>
 *
 * @author codex
 * @since 2021-01-28
 */
public interface IFoldersService extends IService<Folders> {
    
    /**
     * 新建文件夹
     *
     * @param folderName 文件夹名称
     * @param parentId   父文件夹ID
     * @return 结果
     */
    ServerResponse addFolder(String folderName, Long parentId);
    
    /**
     * 重命名文件夹
     *
     * @param folderId      文件夹ID
     * @param parentId      父文件夹ID
     * @param newFolderName 新文件夹名称
     * @return 结果
     */
    ServerResponse rename(Long folderId, Long parentId, String newFolderName);
    
    /**
     * 重命名文件夹(文件夹名重复处理)
     *
     * @param folderId      文件夹ID
     * @param parentId      父文件夹ID
     * @param newFolderName 新文件夹名称
     * @return 结果
     */
    ServerResponse renameRepeat(Long folderId, Long parentId, String newFolderName);
    
    /**
     * 移动文件夹
     *
     * @param folderId 文件夹ID
     * @param parentId 父文件夹ID
     * @return 移动文件夹结果
     */
    ServerResponse move(Long[] folderId, Long parentId);
}
