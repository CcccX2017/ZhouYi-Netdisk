package cn.codex.netdisk.portal.controller;

import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.portal.dtos.ForgotDto;
import cn.codex.netdisk.portal.service.ForgotService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 找回密码
 *
 * @author codex
 * @since 2021-02-24
 */
@Api(tags = "找回密码")
@RestController
@RequestMapping("/portal")
public class ForgotController {
    
    @Autowired
    private ForgotService forgotService;
    
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "发送邮箱验证码")
    @GetMapping("/sendCode")
    public ServerResponse<String> sendEmailCode(String email) {
        return forgotService.sendEmailCode(email);
    }
    
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "验证验证码")
    @PostMapping("/validate")
    public ServerResponse<String> validate(@RequestParam("email") String email,
                                           @RequestParam("code") String code,
                                           @RequestParam("uuid") String uuid) {
        return forgotService.validateCode(email, code, uuid);
    }
    
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "重置密码")
    @PostMapping("/forgot")
    public ServerResponse<String> forgot(@RequestBody ForgotDto forgotDto) {
        return forgotService.resetPassword(forgotDto);
    }
    
}
