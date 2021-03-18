package cn.codex.netdisk.service.impl;

import cn.codex.netdisk.common.utils.SecurityUtil;
import cn.codex.netdisk.common.utils.UsernameEncryptionUtil;
import cn.codex.netdisk.dao.FriendsSessionMapper;
import cn.codex.netdisk.dao.FriendsShareMapper;
import cn.codex.netdisk.model.entity.FriendsSession;
import cn.codex.netdisk.model.vo.FriendsSessionVo;
import cn.codex.netdisk.service.IFriendsSessionService;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
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
    
    @Autowired
    private FriendsShareMapper friendsShareMapper;
    
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
     * 删除好友会话列表
     *
     * @param id 会话id
     * @return 影响行数
     */
    @Override
    public int delSessionList(Long id) {
        FriendsSession friendsSession = friendsSessionMapper.selectById(id);
        FriendsSession updateEntity = new FriendsSession();
        updateEntity.setId(id);
        if (SecurityUtil.getUsername().equals(friendsSession.getUsername())){
            updateEntity.setVisitedByUser(false);
        }else{
            updateEntity.setVisitedByFriend(false);
        }

        return friendsSessionMapper.updateById(updateEntity);
    }


    /**
     * 组装 FriendsSessionVo
     */
    private FriendsSessionVo assembleFriendsSessionVo(FriendsSession friendsSession) {
        FriendsSessionVo friendsSessionVo = new FriendsSessionVo();
        String username = SecurityUtil.getUsername();
        
        friendsSessionVo.setId(friendsSession.getId());
        friendsSessionVo.setFriendId(friendsSession.getUserInfo().getUserId());
        friendsSessionVo.setFriend(UsernameEncryptionUtil.encryptionUsername(friendsSession.getFriend()));
        friendsSessionVo.setAvatar(friendsSession.getUserInfo().getAvatar());
        friendsSessionVo.setShowName(setShowName(friendsSession, username));
        friendsSessionVo.setTitle(friendsSession.getTitle());
        friendsSessionVo.setTime(setTime(friendsSession.getShareTime()));
        friendsSessionVo.setCount(setCount(friendsSession, username));
        
        return friendsSessionVo;
    }
    
    /**
     * 设置好友显示名称
     */
    private String setShowName(FriendsSession friendsSession, String username) {
        String showName;
        // 设置好友显示名称
        if (username.equals(friendsSession.getFriends().getUsername())) {
            // 用户在好友表的username字段
            if (!Strings.isNullOrEmpty(friendsSession.getFriends().getUserToFriendRemark())) {
                showName = friendsSession.getFriends().getUserToFriendRemark();
            } else {
                showName = friendsSession.getUserInfo().getNickname();
            }
        } else {
            // 用户在好友表的friend字段
            if (!Strings.isNullOrEmpty(friendsSession.getFriends().getFriendToUserRemark())) {
                showName = friendsSession.getFriends().getFriendToUserRemark();
            } else {
                showName = friendsSession.getUserInfo().getNickname();
            }
        }
        
        return showName;
    }
    
    /**
     * 设置时间
     */
    private String setTime(Date shareTime) {
        String time;
        // 分享时间与当前时间超过一年
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(shareTime);
        if (Math.abs(year - calendar.get(Calendar.YEAR)) > 0) {
            time = DateUtil.format(shareTime, "yyyy-MM-dd");
        } else if (DateUtil.between(
                DateUtil.parse(DateUtil.now(), "yyyy-MM-dd"),
                DateUtil.parse(DateUtil.formatDate(calendar.getTime()), "yyyy-MM-dd"),
                DateUnit.DAY) > 0) {
            // 分享时间与当前时间超过一天
            time = DateUtil.format(shareTime, "MM-dd HH:mm");
        } else {
            // 当天
            time = DateUtil.format(shareTime, "HH:mm");
        }
        
        return time;
    }
    
    /**
     * 设置未读数
     */
    private Integer setCount(FriendsSession friendsSession, String username) {
        int count;
        // 由用户发送给好友的会话
        if (username.equals(friendsSession.getUsername())) {
            // 获取未读的消息条数
            count = friendsShareMapper.selectUnreadCount(friendsSession.getFriend(), username);
        } else {
            // 由好友发送给用户的会话
            count = friendsShareMapper.selectUnreadCount(username, friendsSession.getFriend());
        }
        return count;
    }
}
