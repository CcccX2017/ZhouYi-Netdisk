package cn.codex.netdisk.service;

import cn.codex.netdisk.model.entity.FriendsSession;
import cn.codex.netdisk.model.vo.FriendsSessionVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 好友会话表 服务类
 * </p>
 *
 * @author codex
 * @since 2021-01-28
 */
public interface IFriendsSessionService extends IService<FriendsSession> {

    /**
     * 获取好友会话列表
     *
     * @return 好友会话列表集合
     */
    List<FriendsSessionVo> sessionList();
}
