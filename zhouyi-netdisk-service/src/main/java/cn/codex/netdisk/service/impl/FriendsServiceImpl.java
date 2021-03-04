package cn.codex.netdisk.service.impl;

import cn.codex.netdisk.common.constants.ReturnMessage;
import cn.codex.netdisk.common.dtos.LoginUser;
import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.common.enums.ResponseCode;
import cn.codex.netdisk.common.exception.ErrorException;
import cn.codex.netdisk.common.utils.SecurityUtil;
import cn.codex.netdisk.common.utils.UsernameEncryptionUtil;
import cn.codex.netdisk.dao.FriendsMapper;
import cn.codex.netdisk.dao.UserMapper;
import cn.codex.netdisk.model.entity.Friends;
import cn.codex.netdisk.model.entity.User;
import cn.codex.netdisk.model.vo.FriendsVo;
import cn.codex.netdisk.service.IFriendsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    @Autowired
    private UserMapper userMapper;

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
     * 搜索用户添加好友
     *
     * @param keyword 关键词
     * @return 用户信息
     */
    @Override
    public Map<String, Object> searchFriends(String keyword) {
        keyword = keyword.trim();

        Map<String, Object> map = Maps.newHashMap();
        // 判断是否为自己
        LoginUser loginUser = SecurityUtil.getLoginUser();
        if (loginUser.getUsername().equals(keyword) || loginUser.getUser().getEmail().equals(keyword)) {
            map.put("type", 1);
            map.put("showName", loginUser.getUser().getNickname());
            map.put("account", loginUser.getUsername());
            map.put("avatar", loginUser.getUser().getAvatar());
            return map;
        }

        User user = userMapper.selectByUsernameOrEmail(keyword);
        if (user == null) {
            throw new ErrorException(ReturnMessage.USER_NOT_FIND);
        }

        // 判断是否已经互为好友
        Integer count = friendsMapper.selectFriendCount(loginUser.getUsername(), user.getUsername());
        if (count > 0) {
            // 已经是好友
            map.put("type", 2);
            map.put("showName", user.getNickname());
        } else {
            // 不是好友
            map.put("type", 3);
        }

        map.put("account", UsernameEncryptionUtil.encryptionUsername(user.getUsername()));
        map.put("avatar", user.getAvatar());

        return map;
    }

    /**
     * 添加好友备注
     *
     * @param id     好友表主键ID
     * @param remark 备注
     * @return 添加结果
     */
    @Override
    public ServerResponse setRemark(Long id, String remark) {
        if (remark.length() > 10) {
            throw new ErrorException("备注过长，请重新输入");
        }
        Friends friends = friendsMapper.selectById(id);
        if (friends == null) {
            throw new ErrorException(ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Friends update = new Friends();
        update.setId(id);
        if (friends.getUsername().equals(SecurityUtil.getUsername())) {
            update.setUserToFriendRemark(remark);
        } else if (friends.getFriend().equals(SecurityUtil.getUsername())) {
            update.setFriendToUserRemark(remark);
        }

        return friendsMapper.updateById(update) > 0
                ? ServerResponse.createBySuccessMessage(ReturnMessage.UPDATE_SUCCESS)
                : ServerResponse.createByErrorMessage(ReturnMessage.UPDATE_ERROR);
    }

    /**
     * 组装FriendsVo
     */
    private void assembleFriendsVo(FriendsVo friendsVo, String username) {
        // 用户发出的添加好友申请
        if (!friendsVo.getUsername().equals(username)) {
            friendsVo.setAvatar(friendsVo.getAvatar());
            friendsVo.setRemarks(friendsVo.getFriendToUserRemark());
            friendsVo.setAccount(UsernameEncryptionUtil.encryptionUsername(friendsVo.getUsername()));
        } else {
            // 好友发出的添加好友申请
            friendsVo.setAvatar(friendsVo.getFriendAvatar());
            friendsVo.setRemarks(friendsVo.getUserToFriendRemark());
            friendsVo.setAccount(UsernameEncryptionUtil.encryptionUsername(friendsVo.getFriend()));
            friendsVo.setNickname(friendsVo.getFriendNickname());
        }

        // 设置好友显示名称
        if (!Strings.isNullOrEmpty(friendsVo.getRemarks())) {
            friendsVo.setShowName(friendsVo.getRemarks());
        } else if (!Strings.isNullOrEmpty(friendsVo.getNickname())) {
            friendsVo.setShowName(friendsVo.getNickname());
        } else {
            friendsVo.setShowName(friendsVo.getAccount());
        }
    }
}
