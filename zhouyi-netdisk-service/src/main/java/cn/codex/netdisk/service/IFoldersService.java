package cn.codex.netdisk.service;

import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.model.dtos.FileRenameDto;
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
     * 新建文件夹
     *
     * @param folderName 文件夹名称
     * @param dir        文件夹路径
     * @param username   用户名
     * @return 结果
     */
    ServerResponse addFolder(String folderName, String dir, String username);

    /**
     * 重命名文件夹
     *
     * @param folderId 文件夹ID
     * @param dto      文件重命名数据传输对象
     * @return 结果
     */
    ServerResponse rename(Long folderId, FileRenameDto dto);

    /**
     * 移动文件夹
     *
     * @param folderId 文件夹ID
     * @param dir      目录
     * @return 移动文件夹结果
     */
    ServerResponse move(Long[] folderId, String dir);
}
