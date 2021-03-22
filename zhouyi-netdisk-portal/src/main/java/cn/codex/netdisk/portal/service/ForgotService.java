package cn.codex.netdisk.portal.service;

import cn.codex.netdisk.common.constants.Const;
import cn.codex.netdisk.common.constants.ReturnMessage;
import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.common.enums.ResponseCode;
import cn.codex.netdisk.common.exception.CaptchaException;
import cn.codex.netdisk.common.utils.Md5Util;
import cn.codex.netdisk.common.utils.RedisUtil;
import cn.codex.netdisk.common.utils.RegexUtil;
import cn.codex.netdisk.dao.UserMapper;
import cn.codex.netdisk.model.entity.User;
import cn.codex.netdisk.portal.component.CaptchaMail;
import cn.codex.netdisk.portal.dtos.ForgotDto;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * 找回密码服务类
 *
 * @author codex
 * @since 2021-02-24
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class ForgotService {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CaptchaMail captchaMail;

    /**
     * 发送邮箱验证码
     *
     * @param email    邮箱
     * @return 发送结果
     */
    public ServerResponse<String> sendEmailCode(String email) {
        if (Strings.isNullOrEmpty(email)) {
            return ServerResponse.createByErrorMessage(ReturnMessage.EMAIL_EMPTY);
        }
        if (!RegexUtil.isEmail(email)) {
            return ServerResponse.createByErrorMessage(ReturnMessage.EMAIL_ILLEGAL);
        }
        if (userMapper.selectCount(new QueryWrapper<User>().eq(User.EMAIL, email)) == 0) {
            return ServerResponse.createByErrorMessage(ReturnMessage.EMAIL_ERROR);
        }

        return captchaMail.sendCaptchaMail(email, "找回密码", Const.FORGOT_KEY + email);
    }
    
    /**
     * 重置密码验证验证码
     *
     * @param email 邮箱
     * @param code 验证码
     * @param uuid 验证码唯一标识
     * @return 验证结果
     */
    public ServerResponse<String> validateCode(String email, String code, String uuid){
        String key = Const.FORGOT_KEY + email + uuid;
        String captcha = redisUtil.getObject(key);
        if (Strings.isNullOrEmpty(captcha)){
            throw new CaptchaException(ReturnMessage.CAPTCHA_EXPIRE);
        }
    
        if (!code.equalsIgnoreCase(captcha)) {
            throw new CaptchaException(ReturnMessage.CAPTCHA_ERROR);
        }
        
        // 验证成功，删除redis缓存并生成验证成功标识
        redisUtil.deleteObject(key);
        String newKey = Md5Util.md5EncodeUtf8(uuid);
        redisUtil.setObject(Const.FORGOT_KEY + newKey, "ok", Const.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
    
        return ServerResponse.createBySuccess(newKey);
    }
    
    /**
     * 重置密码
     *
     * @param forgotDto 重置密码对象
     * @return 重置结果
     */
    public ServerResponse<String> resetPassword(ForgotDto forgotDto) {
        if (forgotDto == null) {
            return ServerResponse.createByErrorMessage(ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        String captchaKey = Const.FORGOT_KEY + forgotDto.getUuid();
        String captcha = redisUtil.getObject(captchaKey);

        if (Strings.isNullOrEmpty(captcha)) {
            throw new CaptchaException(ReturnMessage.CAPTCHA_EXPIRE);
        }

        // 验证码正确，删除redis中缓存的验证码
        redisUtil.deleteObject(captchaKey);

        if (Strings.isNullOrEmpty(forgotDto.getPassword())) {
            return ServerResponse.createByErrorMessage(ReturnMessage.PASSWORD_EMPTY);
        }
        if (forgotDto.getPassword().trim().length() < Const.PASSWORD_MIN_LENGTH || forgotDto.getPassword().trim().length() > Const.PASSWORD_MAX_LENGTH) {
            return ServerResponse.createByErrorMessage(ReturnMessage.PASSWORD_ILLEGAL);
        }

        // 修改用户密码
        int resultCount = userMapper.update(null, new UpdateWrapper<User>().set(User.PASSWORD, BCrypt.hashpw(forgotDto.getPassword())).eq(User.EMAIL,
                forgotDto.getEmail()));

        return resultCount > 0
                ? ServerResponse.createBySuccessMessage(ReturnMessage.RESET_PASSWORD_SUCCESS)
                : ServerResponse.createByErrorMessage(ReturnMessage.RESET_PASSWORD_ERROR);
    }
}
