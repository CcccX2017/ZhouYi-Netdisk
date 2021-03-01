package cn.codex.netdisk.service.impl;

import cn.codex.netdisk.common.dtos.LoginUser;
import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.common.enums.ResponseCode;
import cn.codex.netdisk.common.utils.RedisUtil;
import cn.codex.netdisk.common.utils.SecurityUtil;
import cn.codex.netdisk.dao.FriendsApplicationMapper;
import cn.codex.netdisk.dao.UserMapper;
import cn.codex.netdisk.model.entity.FriendsApplication;
import cn.codex.netdisk.model.entity.User;
import cn.codex.netdisk.service.IFriendsApplicationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 好友申请表 服务实现类
 * </p>
 *
 * @author codex
 * @since 2021-01-28
 */
@Service
public class FriendsApplicationServiceImpl extends ServiceImpl<FriendsApplicationMapper, FriendsApplication> implements IFriendsApplicationService {

    @Autowired
    private FriendsApplicationMapper friendsApplicationMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 获取好友申请列表
     *
     * @return 申请列表
     */
    @Override
    public List<FriendsApplication> getFriendsApplicationList() {
        LoginUser loginUser = SecurityUtil.getLoginUser();
        return friendsApplicationMapper.selectList(new QueryWrapper<FriendsApplication>().eq(FriendsApplication.RESPONDENT,
                loginUser.getUser().getUsername()).or().eq(FriendsApplication.RESPONDENT,
                loginUser.getUser().getEmail()));
    }

    /**
     * 发送添加好友请求
     *
     * @param from    申请人
     * @param to      被申请人
     * @param message 验证消息
     * @return 发送添加好友请求结果
     */
    @Override
    public ServerResponse addFriend(String from, String to, String message) {
        if (Strings.isNullOrEmpty(to)) {
            return ServerResponse.createByErrorMessage(ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        // 判断是否是系统用户
        User user = userMapper.selectByUsername(to);
        if (user != null) {
            return ServerResponse.createByErrorMessage("非本系统用户");
        }
        
        // 判断30分钟内是否重复发送过请求
        String repeat = redisUtil.getObject("ADD_FRIENDS_" + from + "_" + to);
        if (!Strings.isNullOrEmpty(repeat)){
            return ServerResponse.createByErrorMessage("您已发送过好友添加请求，请耐心等待");
        }

        FriendsApplication friendsApplication = new FriendsApplication();
        friendsApplication.setApplicant(from);
        friendsApplication.setRespondent(to);
        friendsApplication.setMessage(message);

        int resultCount = friendsApplicationMapper.insert(friendsApplication);

        if (resultCount < 0) {
            return ServerResponse.createByErrorMessage("您的好友添加请求发送失败");
        }

        // 将请求结果存入redis中，防止30分钟内重复发送请求
        redisUtil.setObject("ADD_FRIENDS_" + from + "_" + to, "OK", 30, TimeUnit.MINUTES);
        return ServerResponse.createBySuccessMessage("您的好友添加请求已经发送成功，等待对方确认");
    }
}
