package cn.codex.netdisk.portal.controller;

import cn.codex.netdisk.common.constants.Const;
import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.common.utils.RedisUtil;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 验证码
 *
 * @author codex
 * @since 2021-02-10
 */
@Api(tags = "验证码")
@RestController
public class CaptchaController {

    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation("生成验证码")
    @GetMapping("/portal/captcha")
    public ServerResponse<Map<String, Object>> createCaptcha(
            @RequestParam(defaultValue = "100") Integer width,
            @RequestParam(defaultValue = "40") Integer height) {

        Map<String, Object> map = Maps.newHashMap();

        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(width, height, Const.CAPTCHA_LENGTH, Const.LINE_COUNT);

        lineCaptcha.setGenerator(new RandomGenerator(RandomUtil.BASE_CHAR_NUMBER + RandomUtil.BASE_CHAR.toUpperCase()
                , Const.CAPTCHA_LENGTH));
        lineCaptcha.setFont(new Font("Arial,Courier", Font.BOLD, (int) (height * 0.78)));
        lineCaptcha.setBackground(new Color(232, 240, 254));

        String uuid = IdUtil.simpleUUID();

        redisUtil.setObject(Const.CAPTCHA_KEY + uuid, lineCaptcha.getCode(), Const.CAPTCHA_EXPIRATION,
                TimeUnit.MINUTES);

        map.put("uuid", uuid);
        map.put("img", lineCaptcha.getImageBase64());
        return ServerResponse.createBySuccess(map);
    }

}
