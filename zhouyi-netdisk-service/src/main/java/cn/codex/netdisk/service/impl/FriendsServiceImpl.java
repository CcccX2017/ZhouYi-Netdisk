package cn.codex.netdisk.service.impl;

import cn.codex.netdisk.common.utils.SecurityUtil;
import cn.codex.netdisk.common.utils.UsernameEncryptionUtil;
import cn.codex.netdisk.dao.FriendsMapper;
import cn.codex.netdisk.model.entity.Friends;
import cn.codex.netdisk.model.vo.FriendsVo;
import cn.codex.netdisk.service.IFriendsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 好友表 服务实现类
 * </p>
 *
 * @author codex
 * @since 2021-01-28
 */
@Service
public class FriendsServiceImpl extends ServiceImpl<FriendsMapper, Friends> implements IFriendsService {
    
    @Autowired
    private FriendsMapper friendsMapper;
    
    /**
     * 获取好友列表
     *
     * @return 好友列表集合
     */
    @Override
    public List<FriendsVo> getFriends() {
        String username = SecurityUtil.getUsername();
        List<FriendsVo> list = friendsMapper.selectFriends(username);
        
        // 处理返回结果，只返回好友信息
        list.forEach(friendsVo -> assembleFriendsVo(friendsVo, username));
        
        return list;
    }
    
    /**
     * 组装FriendsVo
     */
    private void assembleFriendsVo(FriendsVo friendsVo, String username) {
        // 用户发出的添加好友申请
        if (!friendsVo.getUsername().equals(username)) {
            friendsVo.setFriend(friendsVo.getUsername());
            friendsVo.setFriendAvatar(friendsVo.getAvatar());
            friendsVo.setFriendNickname(friendsVo.getNickname());
            friendsVo.setUserToFriendRemark(friendsVo.getFriendToUserRemark());
            friendsVo.setEncryptionName(UsernameEncryptionUtil.encryptionUsername(friendsVo.getUsername()));
        } else {
            // 好友发出的添加好友申请
            friendsVo.setEncryptionName(UsernameEncryptionUtil.encryptionUsername(friendsVo.getFriend()));
        }
        
        // 设置好友显示名称
        if (!Strings.isNullOrEmpty(friendsVo.getUserToFriendRemark())) {
            friendsVo.setShowName(friendsVo.getUserToFriendRemark());
        } else if (!Strings.isNullOrEmpty(friendsVo.getFriendNickname())) {
            friendsVo.setShowName(friendsVo.getFriendNickname());
        } else {
            friendsVo.setShowName(friendsVo.getEncryptionName());
        }
    }
}
