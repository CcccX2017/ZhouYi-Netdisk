package cn.codex.netdisk.dao;

import cn.codex.netdisk.model.entity.FriendsShare;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 好友文件分享表 Mapper 接口
 * </p>
 *
 * @author codex
 * @since 2021-01-28
 */
@Mapper
public interface FriendsShareMapper extends BaseMapper<FriendsShare> {
    /**
     * 获取未读的消息条数
     *
     * @param username 用户名
     * @param friend   好友
     * @return 未读条数
     */
    int selectUnreadCount(@Param("username") String username, @Param("friend") String friend);
}
