package cn.codex.netdisk.dao;

import cn.codex.netdisk.model.entity.Files;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 文件表 Mapper 接口
 * </p>
 *
 * @author codex
 * @since 2021-01-28
 */
@Mapper
public interface FilesMapper extends BaseMapper<Files> {

    /**
     * 物理删除文件
     *
     * @param realName 文件名
     * @param path     文件路径
     * @param username 用户名
     * @return 影响行数
     */
    Integer physicalDelete(@Param("realName") String realName,
                           @Param("dir") String path,
                           @Param("username") String username);

    /**
     * 获取重名的最大数，如123.zip 123(3).zip 则返回3
     *
     * @param realName 文件名
     * @param path     文件路径
     * @param username 用户名
     * @return 重名的最大数
     */
    Integer selectRepeatNameCount(@Param("realName") String realName,
                                  @Param("path") String path,
                                  @Param("username") String username);
}
