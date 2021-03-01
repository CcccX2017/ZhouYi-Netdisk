package cn.codex.netdisk.service;

import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.model.entity.Friends;
import cn.codex.netdisk.model.vo.FriendsVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 好友表 服务类
 * </p>
 *
 * @author codex
 * @since 2021-01-28
 */
public interface IFriendsService extends IService<Friends> {
    /**
     * 获取好友列表
     *
     * @return 好友列表集合
     */
    List<FriendsVo> getFriends();
}
