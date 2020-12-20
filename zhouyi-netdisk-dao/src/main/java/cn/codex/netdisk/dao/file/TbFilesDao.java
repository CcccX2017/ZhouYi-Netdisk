package cn.codex.netdisk.dao.file;

import cn.codex.netdisk.model.entity.TbFiles;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文件表(TbFiles)表数据库访问层
 *
 * @author CodeX
 * @since 2020-11-04 16:12:11
 */
public interface TbFilesDao {
    /**
     * 通过ID查询单条数据
     *
     * @param fileId 主键
     * @return 实例对象
     */
    TbFiles queryById(Long fileId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<TbFiles> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tbFiles 实例对象
     * @return 对象列表
     */
    List<TbFiles> queryAll(TbFiles tbFiles);

    /**
     * 新增数据
     *
     * @param tbFiles 实例对象
     * @return 影响行数
     */
    int insert(TbFiles tbFiles);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TbFiles> entities);

    /**
     * 选择性新增
     *
     * @param tbFiles 实例对象
     * @return 影响行数
     */
    int insertSelective(TbFiles tbFiles);

    /**
     * 修改数据
     *
     * @param tbFiles 实例对象
     * @return 影响行数
     */
    int update(TbFiles tbFiles);

    /**
     * 通过主键删除数据
     *
     * @param fileId 主键
     * @return 影响行数
     */
    int deleteById(Long fileId);
}
