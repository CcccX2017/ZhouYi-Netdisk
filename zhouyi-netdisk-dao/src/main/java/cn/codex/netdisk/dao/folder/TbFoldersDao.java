package cn.codex.netdisk.dao.folder;

import cn.codex.netdisk.model.entity.TbFolders;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 目录表(TbFolders)表数据库访问层
 *
 * @author CodeX
 * @since 2020-11-04 16:19:02
 */
public interface TbFoldersDao {
    /**
     * 通过ID查询单条数据
     *
     * @param folderId 主键
     * @return 实例对象
     */
    TbFolders queryById(Integer folderId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<TbFolders> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param tbFolders 实例对象
     * @return 对象列表
     */
    List<TbFolders> queryAll(TbFolders tbFolders);

    /**
     * 新增数据
     *
     * @param tbFolders 实例对象
     * @return 影响行数
     */
    int insert(TbFolders tbFolders);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TbFolders> entities);

    /**
     * 选择性新增
     *
     * @param tbFolders 实例对象
     * @return 影响行数
     */
    int insertSelective(TbFolders tbFolders);

    /**
     * 修改数据
     *
     * @param tbFolders 实例对象
     * @return 影响行数
     */
    int update(TbFolders tbFolders);

    /**
     * 通过主键删除数据
     *
     * @param folderId 主键
     * @return 影响行数
     */
    int deleteById(Integer folderId);
}
