package cn.codex.netdisk.dao;

import cn.codex.netdisk.model.entity.Friends;
import cn.codex.netdisk.model.vo.FriendsVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 好友表 Mapper 接口
 * </p>
 *
 * @author codex
 * @since 2021-01-28
 */
public interface FriendsMapper extends BaseMapper<Friends> {

    /**
     * 获取好友列表
     *
     * @param username 用户名
     * @return 好友列表集合
     */
    List<FriendsVo> selectFriends(String username);

    /**
     * 查询是否互为好友
     *
     * @param username   用户名
     * @param friendName 要添加的好友用户
     * @return 查询条数
     */
    Integer selectFriendCount(@Param("username") String username, @Param("friendName") String friendName);
}
