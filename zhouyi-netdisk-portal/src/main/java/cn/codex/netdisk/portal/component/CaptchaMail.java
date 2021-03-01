package cn.codex.netdisk.portal.component;

import cn.codex.netdisk.common.constants.Const;
import cn.codex.netdisk.common.constants.ReturnMessage;
import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.common.utils.RedisUtil;
import cn.codex.netdisk.portal.config.PortalConfig;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 发送邮件验证码
 *
 * @author CodeX
 * @since 2021-03-01 09:34:20
 */
@Component
public class CaptchaMail {

    private static final Log log = LogFactory.get();

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailProperties mailProperties;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private PortalConfig portalConfig;

    /**
     * 发送邮件
     * @param to 收件人
     * @param subject 主题
     * @return 发送结果
     */
    public ServerResponse<String> sendCaptchaMail(String to, String subject){
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            // 发件人
            helper.setFrom(mailProperties.getUsername());
            // 收件人
            helper.setTo(to);
            helper.setSubject("【" + portalConfig.getNameZh() + "】" + subject);
            // 发送日期
            helper.setSentDate(new Date());
            // 邮件内容
            String code = RandomUtil.randomString(RandomUtil.BASE_CHAR_NUMBER + RandomUtil.BASE_CHAR.toUpperCase(), 4);
            helper.setText("【" + portalConfig.getNameZh() + "】您用于" + subject + "的验证码为：<b>" + code + "</b>，有效期" + Const.FORGOT_CAPTCHA_EXPIRATION +
                    "分钟，<b style='color:red;'>如非本人操作，请忽略该邮件内容，并尽快修改密码确保账号安全。</b>", true);

            String uuid = IdUtil.simpleUUID();
            try {
                // 将验证码存入redis服务器中，并返回唯一标识
                redisUtil.setObject(Const.FORGOT_KEY + uuid, code, Const.FORGOT_CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
            } catch (Exception ex) {
                log.error("发送给 {} 的邮件发送失败=========> {}", to, ex.getMessage());
                return ServerResponse.createByErrorMessage(ReturnMessage.CAPTCHA_SEND_ERROR);
            }

            // 发送邮件
            javaMailSender.send(message);

            log.info("发送给 {} 的邮件发送成功", to);
            return ServerResponse.createBySuccess(ReturnMessage.CAPTCHA_SEND_SUCCESS, uuid);
        } catch (Exception e) {
            log.error("发送给 {} 的邮件发送失败=========> {}", to, e.getMessage());
            return ServerResponse.createByErrorMessage(ReturnMessage.CAPTCHA_SEND_ERROR);
        }
    }
}
