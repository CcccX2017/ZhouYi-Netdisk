package cn.codex.netdisk.dao;

import cn.codex.netdisk.model.entity.Friends;
import cn.codex.netdisk.model.vo.FriendsVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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
}
