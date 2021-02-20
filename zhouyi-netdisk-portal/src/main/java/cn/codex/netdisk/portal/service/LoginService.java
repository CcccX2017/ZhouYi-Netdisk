package cn.codex.netdisk.portal.service;

import cn.codex.netdisk.common.constants.Const;
import cn.codex.netdisk.common.dtos.LoginDto;
import cn.codex.netdisk.common.exception.CaptchaException;
import cn.codex.netdisk.common.exception.CustomException;
import cn.codex.netdisk.common.exception.UserPasswordNotMatchException;
import cn.codex.netdisk.common.utils.RedisUtil;
import cn.codex.netdisk.dao.UserMapper;
import cn.codex.netdisk.model.entity.User;
import cn.codex.netdisk.portal.utils.JwtTokenUtil;
import cn.codex.netdisk.portal.pojo.LoginUser;
import cn.hutool.core.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 登录、注册、用户信息服务类
 *
 * @author codex
 * @since 2021-02-12
 */
@Component
public class LoginService {
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    @Autowired
    private RedisUtil redisUtil;
    
    @Resource
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserMapper userMapper;
    
    /**
     * 用户登录并返回token
     *
     * @param loginDto 用户登录对象
     * @return token
     */
    @Transactional(rollbackFor = Exception.class)
    public String login(LoginDto loginDto) {
        
        String captchaKey = Const.CAPTCHA_KEY + loginDto.getUuid();
        String captcha = redisUtil.getObject(captchaKey);
        
        if (captcha == null) {
            throw new CaptchaException(Const.CAPTCHA_EXPIRE);
        }
        
        if (!loginDto.getCode().equalsIgnoreCase(captcha)) {
            throw new CaptchaException(Const.CAPTCHA_ERROR);
        }
        
        // 验证码正确，删除redis中缓存的验证码
        redisUtil.deleteObject(captchaKey);
        
        Authentication authentication = null;
        try {
            authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(),
                            loginDto.getPassword()));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                throw new UserPasswordNotMatchException();
            } else {
                throw new CustomException(e.getMessage());
            }
        }
        
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 生成token
        String token = jwtTokenUtil.generateToken(loginUser);
        
        // 更新登录ip和最后登录时间
        User user = new User();
        user.setUserId(loginUser.getUser().getUserId());
        user.setLoginIp(loginUser.getIpAddr());
        user.setLoginDate(DateUtil.date(loginUser.getLoginTime()));
        userMapper.updateById(user);
        
        return token;
    }
}
