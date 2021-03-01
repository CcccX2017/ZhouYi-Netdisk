package cn.codex.netdisk.portal.controller;


import cn.codex.netdisk.common.constants.Const;
import cn.codex.netdisk.common.constants.ReturnMessage;
import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.common.utils.SecurityUtil;
import cn.codex.netdisk.model.entity.Friends;
import cn.codex.netdisk.model.entity.FriendsApplication;
import cn.codex.netdisk.model.vo.FriendsVo;
import cn.codex.netdisk.service.IFriendsApplicationService;
import cn.codex.netdisk.service.IFriendsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 好友表 前端控制器
 * </p>
 *
 * @author codex
 * @since 2021-01-28
 */
@RestController
@RequestMapping("/portal/friends")
@Api(tags = "好友管理")
@Transactional(rollbackFor = Exception.class)
public class FriendsController {
    
    @Autowired
    private IFriendsApplicationService friendsApplicationService;
    
    @Autowired
    private IFriendsService friendsService;
    
    @ApiOperation("获取好友列表")
    @GetMapping("/")
    public ServerResponse<List<FriendsVo>> getFriends() {
        List<FriendsVo> friends = friendsService.getFriends();
        if (friends.size() > 0) {
            
            return ServerResponse.createBySuccess(friends);
        }
        return ServerResponse.createByErrorMessage("获取好友列表失败");
    }
    
    @ApiOperation("添加好友")
    @PostMapping("/")
    public ServerResponse addFriend(String to, String message) {
        return friendsApplicationService.addFriend(SecurityUtil.getUsername(), to, message);
    }
    
    @ApiOperation("获取好友申请列表")
    @GetMapping("/applicationList")
    public ServerResponse<List<FriendsApplication>> getFriendsApplicationList() {
        List<FriendsApplication> list = friendsApplicationService.getFriendsApplicationList();
        return ServerResponse.createBySuccess(list);
    }
    
    @ApiOperation("更新好友申请信息查看状态")
    @PutMapping("/updateApplicationViewed/{id}")
    public ServerResponse updateApplicationViewed(@PathVariable Long id) {
        FriendsApplication friendsApplication = new FriendsApplication();
        friendsApplication.setId(id);
        friendsApplication.setViewed(true);
        
        return friendsApplicationService.updateById(friendsApplication)
                ? ServerResponse.createBySuccessMessage(ReturnMessage.UPDATE_SUCCESS)
                : ServerResponse.createByErrorMessage(ReturnMessage.UPDATE_ERROR);
    }
    
    @ApiOperation("接受好友申请")
    @PostMapping("/agree")
    public ServerResponse agreeApplication(Long id, String from) {
        // 将用户添加到好友表中
        FriendsApplication friendsApplication = new FriendsApplication();
        friendsApplication.setId(id);
        friendsApplication.setAgreed(Const.AGREED);
        
        if (!friendsApplicationService.updateById(friendsApplication)) {
            return ServerResponse.createByErrorMessage("添加好友失败，请重试");
        }
        
        String username = SecurityUtil.getUsername();
        Friends friends = new Friends();
        friends.setUsername(username);
        friends.setFriend(from);
        friends.setUserToFriendRemark("");
        friends.setFriendToUserRemark("");
        
        return friendsService.save(friends)
                ? ServerResponse.createBySuccessMessage("添加好友成功")
                : ServerResponse.createByErrorMessage("添加好友失败，请重试");
    }
    
    @ApiOperation("拒绝好友申请")
    @PutMapping("/refuse/{id}")
    public ServerResponse refuseApplication(@PathVariable Long id) {
        FriendsApplication friendsApplication = new FriendsApplication();
        friendsApplication.setId(id);
        friendsApplication.setAgreed(Const.REFUSE);
        
        return friendsApplicationService.updateById(friendsApplication)
                ? ServerResponse.createBySuccessMessage("已拒绝好友申请")
                : ServerResponse.createByErrorMessage("拒绝好友申请失败，请重试");
    }
}

