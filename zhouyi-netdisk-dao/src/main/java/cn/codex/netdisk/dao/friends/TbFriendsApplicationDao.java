package cn.codex.netdisk.dao.friends;

import cn.codex.netdisk.model.entity.TbFriendsApplication;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 好友申请表(TbFriendsApplication)表数据库访问层
 *
 * @author CodeX
 * @since 2020-11-04 16:37:25
 */
public interface TbFriendsApplicationDao {
    /**
     * 通过ID查询单条数据
     *
     * @param applicant 主键
     * @return 实例对象
     */
    TbFriendsApplication queryById(String applicant);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<TbFriendsApplication> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tbFriendsApplication 实例对象
     * @return 对象列表
     */
    List<TbFriendsApplication> queryAll(TbFriendsApplication tbFriendsApplication);

    /**
     * 新增数据
     *
     * @param tbFriendsApplication 实例对象
     * @return 影响行数
     */
    int insert(TbFriendsApplication tbFriendsApplication);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TbFriendsApplication> entities);

    /**
     * 选择性新增
     *
     * @param tbFriendsApplication 实例对象
     * @return 影响行数
     */
    int insertSelective(TbFriendsApplication tbFriendsApplication);

    /**
     * 修改数据
     *
     * @param tbFriendsApplication 实例对象
     * @return 影响行数
     */
    int update(TbFriendsApplication tbFriendsApplication);

    /**
     * 通过主键删除数据
     *
     * @param applicant 主键
     * @return 影响行数
     */
    int deleteById(String applicant);
}
