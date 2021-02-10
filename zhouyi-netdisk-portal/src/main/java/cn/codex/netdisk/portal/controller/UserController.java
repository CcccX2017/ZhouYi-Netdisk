package cn.codex.netdisk.portal.controller;


import cn.codex.netdisk.common.constants.Const;
import cn.codex.netdisk.common.dtos.LoginDto;
import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.model.entity.User;
import cn.codex.netdisk.portal.config.security.component.JwtTokenUtil;
import cn.codex.netdisk.service.IUserService;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author codex
 * @since 2021-01-28
 */
@RestController
@RequestMapping("/portal/user")
@Api(tags = "个人信息管理(UserController)")
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @ApiOperation(value = "登录并返回token")
    @PostMapping("/login")
    public ServerResponse login(@RequestBody LoginDto loginDto) {
        ServerResponse responseServer = userService.login(loginDto);

        if (!responseServer.isSuccess()) {
            return responseServer;
        }

        User user = (User) responseServer.getData();
        String token = jwtTokenUtil.generateToken(user);
        Map<String, Object> map = Maps.newHashMap();
        map.put("token", token);
        map.put("tokenHead", tokenHead);

        return ServerResponse.createBySuccess(Const.LOGIN_SUCCESS, map);
    }
}

