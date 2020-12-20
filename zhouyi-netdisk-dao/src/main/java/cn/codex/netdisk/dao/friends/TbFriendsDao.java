package cn.codex.netdisk.dao.friends;

import cn.codex.netdisk.model.entity.TbFriends;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 好友表(TbFriends)表数据库访问层
 *
 * @author CodeX
 * @since 2020-11-04 16:46:45
 */
public interface TbFriendsDao {
    /**
     * 通过ID查询单条数据
     *
     * @param username 主键
     * @return 实例对象
     */
    TbFriends queryById(String username);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<TbFriends> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tbFriends 实例对象
     * @return 对象列表
     */
    List<TbFriends> queryAll(TbFriends tbFriends);

    /**
     * 新增数据
     *
     * @param tbFriends 实例对象
     * @return 影响行数
     */
    int insert(TbFriends tbFriends);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TbFriends> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<TbFriends> entities);

    /**
     * 选择性新增
     *
     * @param tbFriends 实例对象列表
     * @return 影响行数
     */
    int insertSelective(TbFriends tbFriends);

    /**
     * 修改数据
     *
     * @param tbFriends 实例对象
     * @return 影响行数
     */
    int update(TbFriends tbFriends);

    /**
     * 通过主键删除数据
     *
     * @param username 主键
     * @return 影响行数
     */
    int deleteById(String username);
}
