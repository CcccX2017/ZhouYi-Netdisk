package cn.codex.netdisk.portal.service;

import cn.codex.netdisk.common.constants.Const;
import cn.codex.netdisk.common.constants.ReturnMessage;
import cn.codex.netdisk.common.dtos.LoginDto;
import cn.codex.netdisk.common.dtos.LoginUser;
import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.common.exception.CaptchaException;
import cn.codex.netdisk.common.exception.CustomException;
import cn.codex.netdisk.common.exception.UserPasswordNotMatchException;
import cn.codex.netdisk.common.utils.JwtTokenUtil;
import cn.codex.netdisk.common.utils.RedisUtil;
import cn.codex.netdisk.common.utils.RegexUtil;
import cn.codex.netdisk.dao.UserMapper;
import cn.codex.netdisk.model.entity.User;
import cn.codex.netdisk.portal.dtos.RegisterDto;
import cn.codex.netdisk.service.IFoldersService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
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
@Transactional(rollbackFor = Exception.class)
public class LoginService {
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    @Autowired
    private RedisUtil redisUtil;
    
    @Resource
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private IFoldersService foldersService;
    
    /**
     * 用户登录并返回token
     *
     * @param loginDto 用户登录对象
     * @return token
     */
    public String login(LoginDto loginDto) {
        // 校验验证码
        validCode(loginDto.getUuid(), loginDto.getCode());
        
        Authentication authentication = null;
        try {
            authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
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
    
    /**
     * 用户注册
     *
     * @param registerDto 用户登录对象
     * @return 注册结果
     */
    public ServerResponse<String> register(RegisterDto registerDto) {
        
        // 校验验证码
        validCode(registerDto.getUuid(), registerDto.getCode());
        
        // 验证数据准确性
        ServerResponse<String> responseServer = verifyData(registerDto);
        if (responseServer != null) {
            return responseServer;
        }
        
        // 注册用户
        User user = new User();
        user.setGroupId(Const.DEFAULT_GROUP_ID);
        user.setUsername(registerDto.getUsername().trim());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setSalt(BCrypt.gensalt());
        user.setStatus(true);
        user.setNickname(registerDto.getNickname().trim());
        user.setAvatar("");
        user.setRealName("");
        user.setPhone("");
        user.setEmail(registerDto.getEmail().trim());
        // 默认未知
        user.setSex(Const.DEFAULT_SEX);
        user.setUsedStorageSpace(0L);
        user.setDataPerfect(false);
        
        int resultCount = userMapper.insert(user);
        
        // 用户注册成功，默认生成我的资源文件夹
        if (resultCount > 0) {
            foldersService.addFolder("我的资源", "/", user.getUsername());
            return ServerResponse.createBySuccessMessage(ReturnMessage.REGISTER_SUCCESS);
        }
        
        return ServerResponse.createByErrorMessage(ReturnMessage.REGISTER_ERROR);
    }
    
    /**
     * 验证数据准确性
     */
    private ServerResponse<String> verifyData(RegisterDto registerDto) {
        
        if (Strings.isNullOrEmpty(registerDto.getUsername())) {
            return ServerResponse.createByErrorMessage(ReturnMessage.USERNAME_EMPTY);
        }
        // 验证用户名是否合法
        if (!RegexUtil.isAccountLegal(registerDto.getUsername())) {
            return ServerResponse.createByErrorMessage(ReturnMessage.USERNAME_ILLEGAL);
        }
        // 验证用户名是否已存在
        Integer count = userMapper.selectCount(new QueryWrapper<User>().eq(User.USERNAME, registerDto.getUsername()));
        if (count > 0) {
            return ServerResponse.createByErrorMessage(ReturnMessage.USERNAME_EXIST);
        }
        
        if (Strings.isNullOrEmpty(registerDto.getPassword())) {
            return ServerResponse.createByErrorMessage(ReturnMessage.PASSWORD_EMPTY);
        }
        if (registerDto.getPassword().trim().length() < Const.PASSWORD_MIN_LENGTH || registerDto.getPassword().trim().length() > Const.PASSWORD_MAX_LENGTH) {
            return ServerResponse.createByErrorMessage(ReturnMessage.PASSWORD_ILLEGAL);
        }
        
        if (Strings.isNullOrEmpty(registerDto.getNickname())) {
            return ServerResponse.createByErrorMessage(ReturnMessage.NICKNAME_EMPTY);
        }
        // 验证昵称是否合法
        if (!RegexUtil.isNicknameLegal(registerDto.getNickname())) {
            return ServerResponse.createByErrorMessage(ReturnMessage.NICKNAME_ILLEGAL);
        }
        // 验证昵称是否已被使用
        count = userMapper.selectCount(new QueryWrapper<User>().eq(User.NICKNAME, registerDto.getNickname()));
        if (count > 0) {
            return ServerResponse.createByErrorMessage(ReturnMessage.NICKNAME_EXIST);
        }
        
        if (Strings.isNullOrEmpty(registerDto.getEmail())) {
            return ServerResponse.createByErrorMessage(ReturnMessage.EMAIL_EMPTY);
        }
        // 校验邮箱是否合法
        if (!RegexUtil.isEmail(registerDto.getEmail())) {
            return ServerResponse.createByErrorMessage(ReturnMessage.EMAIL_ILLEGAL);
        }
        // 判断邮箱是否被使用
        count = userMapper.selectCount(new QueryWrapper<User>().eq(User.EMAIL, registerDto.getEmail()));
        if (count > 0) {
            return ServerResponse.createByErrorMessage(ReturnMessage.EMAIL_EXIST);
        }
        
        return null;
    }
    
    /**
     * 验证码验证通用方法
     *
     * @param uuid 验证码唯一标识
     * @param code 验证码
     */
    private void validCode(String uuid, String code) {
        String captchaKey = Const.CAPTCHA_KEY + uuid;
        String captcha = redisUtil.getObject(captchaKey);
        
        if (captcha == null) {
            throw new CaptchaException(ReturnMessage.CAPTCHA_EXPIRE);
        }
        
        if (!code.equalsIgnoreCase(captcha)) {
            throw new CaptchaException(ReturnMessage.CAPTCHA_ERROR);
        }
        
        // 验证码正确，删除redis中缓存的验证码
        redisUtil.deleteObject(captchaKey);
    }
}
