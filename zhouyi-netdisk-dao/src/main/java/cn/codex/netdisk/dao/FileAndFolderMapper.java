package cn.codex.netdisk.dao;

import cn.codex.netdisk.model.dtos.FolderAndFileQueryDto;
import cn.codex.netdisk.model.vo.FolderAndFileVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * 文件和文件夹
 *
 * @author codex
 * @since 2021-03-27
 */
public interface FileAndFolderMapper {

    /**
     * 获取文件夹和文件列表
     *
     * @param page     分页对象
     * @param username 用户名
     * @param dto      查询对象
     * @return 查询结果
     */
    IPage<FolderAndFileVo> getFolderAndFileVo(Page<FolderAndFileVo> page,
                                              @Param("username") String username,
                                              @Param("dto") FolderAndFileQueryDto dto);

    /**
     * 条件查询
     *
     * @param page     分页对象
     * @param username 用户名
     * @param dto      查询对象
     * @return 查询结果
     */
    IPage<FolderAndFileVo> search(Page<FolderAndFileVo> page,
                                  @Param("username") String username,
                                  @Param("dto") FolderAndFileQueryDto dto);
}
