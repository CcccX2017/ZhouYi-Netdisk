package cn.codex.netdisk.portal.service;

import cn.codex.netdisk.common.constants.Const;
import cn.codex.netdisk.common.constants.ReturnMessage;
import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.common.enums.ResponseCode;
import cn.codex.netdisk.common.exception.CaptchaException;
import cn.codex.netdisk.common.utils.RedisUtil;
import cn.codex.netdisk.common.utils.RegexUtil;
import cn.codex.netdisk.dao.UserMapper;
import cn.codex.netdisk.model.entity.User;
import cn.codex.netdisk.portal.config.PortalConfig;
import cn.codex.netdisk.portal.dtos.ForgotDto;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 找回密码服务类
 *
 * @author codex
 * @since 2021-02-24
 */
@Component
public class ForgotService {
    private static final Log log = LogFactory.get();

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailProperties mailProperties;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private PortalConfig portalConfig;
    @Autowired
    private UserMapper userMapper;

    /**
     * 发送邮箱验证码
     *
     * @param username 用户名
     * @param email    邮箱
     * @return 发送结果
     */
    public ServerResponse<String> sendEmailCode(String username, String email) {

        if (Strings.isNullOrEmpty(username)) {
            return ServerResponse.createByErrorMessage(ReturnMessage.USERNAME_EMPTY);
        }
        if (!RegexUtil.isAccountLegal(username)) {
            return ServerResponse.createByErrorMessage(ReturnMessage.USERNAME_ILLEGAL);
        }
        if (userMapper.selectCount(new QueryWrapper<User>().eq(User.USERNAME, username)) == 0) {
            return ServerResponse.createByErrorMessage(ReturnMessage.USERNAME_NOT_EXIST);
        }

        if (Strings.isNullOrEmpty(email)) {
            return ServerResponse.createByErrorMessage(ReturnMessage.EMAIL_EMPTY);
        }
        if (!RegexUtil.isEmail(email)) {
            return ServerResponse.createByErrorMessage(ReturnMessage.EMAIL_ILLEGAL);
        }
        if (userMapper.selectCount(new QueryWrapper<User>().eq(User.EMAIL, email)) == 0) {
            return ServerResponse.createByErrorMessage(ReturnMessage.EMAIL_ERROR);
        }

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            // 发件人
            helper.setFrom(mailProperties.getUsername());
            // 收件人
            helper.setTo(email);
            helper.setSubject("【" + portalConfig.getNameZh() + "】找回密码");
            // 发送日期
            helper.setSentDate(new Date());
            // 邮件内容
            String code = RandomUtil.randomString(RandomUtil.BASE_CHAR_NUMBER + RandomUtil.BASE_CHAR.toUpperCase(), 4);
            helper.setText("【" + portalConfig.getNameZh() + "】您用于找回密码的验证码为：<b>" + code + "</b>，有效期" + Const.FORGOT_CAPTCHA_EXPIRATION +
                    "分钟，<b style='color:red;'>如非本人操作，请忽略该邮件内容，并尽快修改密码确保账号安全。</b>", true);

            String uuid = IdUtil.simpleUUID();
            try {
                // 将验证码存入redis服务器中，并返回唯一标识
                redisUtil.setObject(Const.FORGOT_KEY + uuid, code, Const.FORGOT_CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
            } catch (Exception ex) {
                log.error("发送给 {} 的邮件发送失败=========> {}", email, ex.getMessage());
                return ServerResponse.createByErrorMessage(ReturnMessage.CAPTCHA_SEND_ERROR);
            }

            // 发送邮件
            javaMailSender.send(message);

            log.info("发送给 {} 的邮件发送成功", email);
            return ServerResponse.createBySuccess(ReturnMessage.CAPTCHA_SEND_SUCCESS, uuid);
        } catch (Exception e) {
            log.error("发送给 {} 的邮件发送失败=========> {}", email, e.getMessage());
        }

        return ServerResponse.createByErrorMessage(ReturnMessage.CAPTCHA_SEND_ERROR);
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

        if (Strings.isNullOrEmpty(forgotDto.getEmail())) {
            return ServerResponse.createByErrorMessage(ReturnMessage.EMAIL_EMPTY);
        }
        if (!RegexUtil.isEmail(forgotDto.getEmail())) {
            return ServerResponse.createByErrorMessage(ReturnMessage.EMAIL_ILLEGAL);
        }
        if (userMapper.selectCount(new QueryWrapper<User>().eq(User.EMAIL, forgotDto.getEmail())) == 0) {
            return ServerResponse.createByErrorMessage(ReturnMessage.EMAIL_ERROR);
        }

        String captchaKey = Const.FORGOT_KEY + forgotDto.getUuid();
        String captcha = redisUtil.getObject(captchaKey);

        if (captcha == null) {
            throw new CaptchaException(ReturnMessage.CAPTCHA_EXPIRE);
        }

        if (!forgotDto.getCode().equalsIgnoreCase(captcha)) {
            throw new CaptchaException(ReturnMessage.CAPTCHA_ERROR);
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
                ? ServerResponse.createBySuccessMessage(ReturnMessage.UPDATE_PASSWORD_SUCCESS)
                : ServerResponse.createByErrorMessage(ReturnMessage.UPDATE_PASSWORD_ERROR);
    }
}
