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
     * @param parentId      父文件夹ID
     * @param username      用户名
     * @return 影响条数
     */
    Integer selectRenameCount(@Param("folderId") Long folderId,
                              @Param("newFolderName") String newFolderName,
                              @Param("parentId") Long parentId,
                              @Param("username") String username);
}
