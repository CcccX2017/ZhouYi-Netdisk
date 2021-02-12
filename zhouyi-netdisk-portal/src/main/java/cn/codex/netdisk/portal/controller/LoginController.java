package cn.codex.netdisk.portal.controller;

import cn.codex.netdisk.common.constants.Const;
import cn.codex.netdisk.common.dtos.LoginDto;
import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.portal.service.LoginService;
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
 * 登录、注册、用户信息
 *
 * @author codex
 * @since 2021-02-12
 */
@Api(tags = "登录、注册、用户信息")
@RestController
@RequestMapping("/portal")
public class LoginController {
    
    @Autowired
    private LoginService loginService;
    
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    
    
    @ApiOperation(value = "登录并返回token")
    @PostMapping("/login")
    public ServerResponse<Map<String, Object>> login(@RequestBody LoginDto loginDto) {
        // 获取令牌
        String token = loginService.login(loginDto);
        
        Map<String, Object> map = Maps.newHashMap();
        map.put("tokenHead", tokenHead);
        map.put("token", token);
        
        return ServerResponse.createBySuccess(Const.LOGIN_SUCCESS, map);
    }
}
