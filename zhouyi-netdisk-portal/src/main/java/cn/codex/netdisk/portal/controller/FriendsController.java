package cn.codex.netdisk.portal.controller;


import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.common.utils.SecurityUtil;
import cn.codex.netdisk.model.entity.FriendsApplication;
import cn.codex.netdisk.service.IFriendsApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class FriendsController {

    @Autowired
    private IFriendsApplicationService friendsApplicationService;

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

}

