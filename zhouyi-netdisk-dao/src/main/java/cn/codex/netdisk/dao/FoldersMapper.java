package cn.codex.netdisk.dao;

import cn.codex.netdisk.model.entity.Folders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 目录表 Mapper 接口
 * </p>
 *
 * @author codex
 * @since 2021-01-28
 */
public interface FoldersMapper extends BaseMapper<Folders> {
    
    /**
     * 获取重复名称个数
     *
     * @param folderId      文件夹ID
     * @param newFolderName 要修改的文件夹名称
     * @param dir           目录
     * @param username      用户名
     * @return 影响条数
     */
    Integer selectRenameCount(@Param("folderId") Long folderId,
                              @Param("newFolderName") String newFolderName,
                              @Param("dir") String dir,
                              @Param("username") String username);
    
    /**
     * 移动文件夹
     *
     * @param folderId 文件夹ID
     * @param dir      目录
     * @return 影响条数
     */
    Integer moveFolder(@Param("folderId") Long folderId, @Param("dir") String dir);
    
    /**
     * 批量移动文件夹
     *
     * @param folderId 文件夹ID
     * @param dir      目录
     * @return 影响条数
     */
    Integer batchMoveFolder(@Param("folderIds") Long[] folderId, @Param("dir") String dir);
}
