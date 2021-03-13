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
     * @param dir        文件夹路径
     * @return 结果
     */
    ServerResponse addFolder(String folderName, String dir);
    
    /**
     * 重命名文件夹
     *
     * @param folderId      文件夹ID
     * @param dir           目录
     * @param newFolderName 新文件夹名称
     * @return 结果
     */
    ServerResponse rename(Long folderId, String dir, String newFolderName);
    
    /**
     * 重命名文件夹(文件夹名重复处理)
     *
     * @param folderId      文件夹ID
     * @param dir           目录
     * @param newFolderName 新文件夹名称
     * @return 结果
     */
    ServerResponse renameRepeat(Long folderId, String dir, String newFolderName);
    
    /**
     * 移动文件夹
     *
     * @param folderId 文件夹ID
     * @param dir      目录
     * @return 移动文件夹结果
     */
    ServerResponse move(Long[] folderId, String dir);
}
