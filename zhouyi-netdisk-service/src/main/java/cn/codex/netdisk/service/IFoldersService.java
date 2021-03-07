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
     * @param folders 目录实体类
     * @return 结果
     */
    ServerResponse addFolder(Folders folders);
    
    /**
     * 重命名文件夹
     *
     * @param folderId      文件夹ID
     * @param parentId      父文件夹ID
     * @param newFolderName 新文件夹名称
     * @return 结果
     */
    ServerResponse rename(Long folderId, Long parentId, String newFolderName);
}
