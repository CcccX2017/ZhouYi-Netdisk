package cn.codex.netdisk.portal.controller;

import cn.codex.netdisk.common.constants.ReturnMessage;
import cn.codex.netdisk.common.dtos.LoginDto;
import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.portal.component.CaptchaMail;
import cn.codex.netdisk.portal.dtos.RegisterDto;
import cn.codex.netdisk.portal.service.LoginService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 登录、注册、用户信息
 *
 * @author codex
 * @since 2021-02-12
 */
@Api(tags = "登录、注册")
@RestController
@RequestMapping("/portal")
public class LoginController {
    
    @Autowired
    private LoginService loginService;
    
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private CaptchaMail captchaMail;

    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "登录并返回token")
    @PostMapping("/login")
    public ServerResponse<Map<String, Object>> login(@RequestBody LoginDto loginDto) {
        // 获取令牌
        String token = loginService.login(loginDto);
        
        Map<String, Object> map = Maps.newHashMap();
        map.put("tokenHead", tokenHead);
        map.put("token", token);
        
        return ServerResponse.createBySuccess(ReturnMessage.LOGIN_SUCCESS, map);
    }

    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "发送邮箱验证码")
    @GetMapping("/registerCode")
    public ServerResponse<String> sendRegisterCode(String to){
        return captchaMail.sendRegisterMail(to);
    }

    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public ServerResponse<String> register(@RequestBody RegisterDto registerDto){
        return loginService.register(registerDto);
    }
}
