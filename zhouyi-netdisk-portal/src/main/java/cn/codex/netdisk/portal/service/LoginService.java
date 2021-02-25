package cn.codex.netdisk.portal.service;

import cn.codex.netdisk.common.constants.Const;
import cn.codex.netdisk.common.dtos.LoginDto;
import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.common.exception.CaptchaException;
import cn.codex.netdisk.common.exception.CustomException;
import cn.codex.netdisk.common.exception.UserPasswordNotMatchException;
import cn.codex.netdisk.common.utils.RedisUtil;
import cn.codex.netdisk.common.utils.RegexUtil;
import cn.codex.netdisk.dao.UserMapper;
import cn.codex.netdisk.model.entity.User;
import cn.codex.netdisk.model.vo.UserVo;
import cn.codex.netdisk.portal.dtos.RegisterDto;
import cn.codex.netdisk.portal.pojo.LoginUser;
import cn.codex.netdisk.portal.utils.JwtTokenUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
        user.setGroupId(1001);
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setSalt(BCrypt.gensalt());
        user.setStatus(true);
        user.setNickname(registerDto.getNickname());
        user.setAvatar("");
        user.setRealName("");
        user.setPhone("");
        user.setEmail(registerDto.getEmail());
        // 默认未知
        user.setSex("2");
        user.setUsedStorageSpace(0L);
        user.setDataPerfect(false);
        
        int resultCount = userMapper.insert(user);
        
        return resultCount > 0
                ? ServerResponse.createBySuccessMessage("注册成功")
                : ServerResponse.createByErrorMessage("注册失败");
    }
    
    /**
     * 获取登录用户信息
     *
     * @return 登录用户信息
     */
    public ServerResponse<UserVo> getLoginUserInfo(HttpServletRequest request) {
        LoginUser loginUser = jwtTokenUtil.getLoginUser(request);
        if (loginUser == null) {
            return ServerResponse.createByErrorMessage("尚未登录，请登录");
        }
        
        loginUser.getUser().setPassword(null);
    
        UserVo userVo = new UserVo();
        
        BeanUtils.copyProperties(loginUser.getUser(), userVo);
        userVo.setGroupName(loginUser.getUser().getUserGroups().getGroupName());
        userVo.setMaxFileSize(loginUser.getUser().getUserGroups().getMaxFileSize());
        userVo.setMaxStorageSpace(loginUser.getUser().getUserGroups().getMaxStorageSpace());
        
        return ServerResponse.createBySuccess(userVo);
    }
    
    /**
     * 验证数据准确性
     */
    private ServerResponse<String> verifyData(RegisterDto registerDto) {
        
        if (Strings.isNullOrEmpty(registerDto.getUsername())) {
            return ServerResponse.createByErrorMessage("请输入用户名");
        }
        // 验证用户名是否合法
        if (!RegexUtil.isAccountLegal(registerDto.getUsername())) {
            return ServerResponse.createByErrorMessage("用户名须以英文字母开头长度为5-16位的英文/数字/下划线'_'");
        }
        // 验证用户名是否已存在
        Integer count = userMapper.selectCount(new QueryWrapper<User>().eq(User.USERNAME, registerDto.getUsername()));
        if (count > 0) {
            return ServerResponse.createByErrorMessage("该用户名已存在，请更换新用户名");
        }
        
        if (Strings.isNullOrEmpty(registerDto.getPassword())) {
            return ServerResponse.createByErrorMessage("请输入密码");
        }
        if (registerDto.getPassword().trim().length() < Const.PASSWORD_MIN_LENGTH || registerDto.getPassword().trim().length() > Const.PASSWORD_MAX_LENGTH){
            return ServerResponse.createByErrorMessage("密码长度为6-16位");
        }
        
        if (Strings.isNullOrEmpty(registerDto.getNickname())) {
            return ServerResponse.createByErrorMessage("请输入用户昵称");
        }
        // 验证昵称是否合法
        if (!RegexUtil.isNicknameLegal(registerDto.getNickname())) {
            return ServerResponse.createByErrorMessage("用户昵称只能是长度为5-12位的中英文/数字/下划线'_'");
        }
        // 验证昵称是否已被使用
        count = userMapper.selectCount(new QueryWrapper<User>().eq(User.NICKNAME, registerDto.getNickname()));
        if (count > 0) {
            return ServerResponse.createByErrorMessage("该昵称已被使用，请更换新昵称");
        }
        
        if (Strings.isNullOrEmpty(registerDto.getEmail())) {
            return ServerResponse.createByErrorMessage("请输入邮箱");
        }
        // 校验邮箱是否合法
        if (!RegexUtil.isEmail(registerDto.getEmail())) {
            return ServerResponse.createByErrorMessage("请输入正确的邮箱");
        }
        // 判断邮箱是否被使用
        count = userMapper.selectCount(new QueryWrapper<User>().eq(User.EMAIL, registerDto.getEmail()));
        if (count > 0) {
            return ServerResponse.createByErrorMessage("该邮箱已被使用，请更换新邮箱");
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
            throw new CaptchaException(Const.CAPTCHA_EXPIRE);
        }
        
        if (!code.equalsIgnoreCase(captcha)) {
            throw new CaptchaException(Const.CAPTCHA_ERROR);
        }
        
        // 验证码正确，删除redis中缓存的验证码
        redisUtil.deleteObject(captchaKey);
    }
}
