package cn.codex.netdisk.portal.controller;

import cn.codex.netdisk.common.constants.Const;
import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.common.utils.RedisUtil;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.IdUtil;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 验证码
 *
 * @author codex
 * @since 2021-02-10
 */
@RestController
@Api(tags = "验证码")
public class CaptchaController {
    
    @Autowired
    private RedisUtil redisUtil;
    
    @ApiOperation("生成验证码")
    @GetMapping("/captcha")
    public ServerResponse<Map<String, Object>> createCaptcha() {
        
        Map<String, Object> map = Maps.newHashMap();
        
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(100, 40);
        
        String uuid = IdUtil.simpleUUID();
        
        redisUtil.setObject(Const.CAPTCHA_KEY + uuid, lineCaptcha.getCode(), Const.CAPTCHA_EXPIRATION,
                TimeUnit.MINUTES);
        
        map.put("uuid", uuid);
        map.put("img", lineCaptcha.getImageBase64());
        
        return ServerResponse.createBySuccess(map);
    }
    
}
