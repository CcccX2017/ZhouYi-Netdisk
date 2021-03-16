package cn.codex.netdisk.service.impl;

import cn.codex.netdisk.common.utils.SecurityUtil;
import cn.codex.netdisk.dao.FriendsSessionMapper;
import cn.codex.netdisk.model.entity.FriendsSession;
import cn.codex.netdisk.model.vo.FriendsSessionVo;
import cn.codex.netdisk.service.IFriendsSessionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 好友会话表 服务实现类
 * </p>
 *
 * @author codex
 * @since 2021-01-28
 */
@Service
public class FriendsSessionServiceImpl extends ServiceImpl<FriendsSessionMapper, FriendsSession> implements IFriendsSessionService {

    @Autowired
    private FriendsSessionMapper friendsSessionMapper;

    /**
     * 获取好友会话列表
     *
     * @return 好友会话列表集合
     */
    @Override
    public List<FriendsSessionVo> sessionList() {
        List<FriendsSession> friendsSessions = friendsSessionMapper.selectSessionList(SecurityUtil.getUsername());
        List<FriendsSessionVo> list = Lists.newArrayList();
        friendsSessions.forEach(friendsSession -> {
            list.add(assembleFriendsSessionVo(friendsSession));
        });
        return list;
    }


    /**
     * 组装 FriendsSessionVo
     */
    private FriendsSessionVo assembleFriendsSessionVo(FriendsSession friendsSession) {
        FriendsSessionVo friendsSessionVo = new FriendsSessionVo();

        String dateFormat = "MM-dd HH:mm";
        String username = SecurityUtil.getUsername();
        // 由用户发送给好友的会话
        if (username.equals(friendsSession.getUsername())) {

        } else {
            // 由好友发送给用户的会话
        }

        return friendsSessionVo;
    }
}
