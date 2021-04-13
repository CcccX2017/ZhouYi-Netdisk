package cn.codex.netdisk.service;

import cn.codex.netdisk.model.dtos.FolderAndFileQueryDto;
import cn.codex.netdisk.model.vo.FolderAndFileVo;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 文件和文件夹
 *
 * @author codex
 * @since 2021-03-27
 */
public interface IFileAndFolderService {
    
    /**
     * 获取文件夹和文件列表
     *
     * @param username 用户名
     * @param dto      文件夹和文件查询实体类
     * @return 查询结果
     */
    IPage<FolderAndFileVo> getList(String username, FolderAndFileQueryDto dto);

    /**
     * 条件查询
     *
     * @param username 用户名
     * @param dto      文件夹和文件查询实体类
     * @return 查询结果
     */
    IPage<FolderAndFileVo> search(String username, FolderAndFileQueryDto dto);
}
