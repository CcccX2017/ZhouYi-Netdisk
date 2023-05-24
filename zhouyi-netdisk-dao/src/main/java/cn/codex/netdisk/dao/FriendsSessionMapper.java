package cn.codex.netdisk.dao;

import cn.codex.netdisk.model.entity.FriendsSession;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 好友会话表 Mapper 接口
 * </p>
 *
 * @author codex
 * @since 2021-01-28
 */
@Mapper
public interface FriendsSessionMapper extends BaseMapper<FriendsSession> {

    /**
     * 获取好友会话列表
     *
     * @param username 用户名
     * @return 好友会话列表集合
     */
    List<FriendsSession> selectSessionList(String username);
}
