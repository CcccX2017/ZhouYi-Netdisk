package cn.codex.netdisk.service.impl;

import cn.codex.netdisk.common.constants.Const;
import cn.codex.netdisk.common.constants.ReturnMessage;
import cn.codex.netdisk.common.dtos.LoginUser;
import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.common.enums.ResponseCode;
import cn.codex.netdisk.common.utils.JwtTokenUtil;
import cn.codex.netdisk.common.utils.RedisUtil;
import cn.codex.netdisk.common.utils.SecurityUtil;
import cn.codex.netdisk.dao.UserMapper;
import cn.codex.netdisk.model.entity.User;
import cn.codex.netdisk.model.vo.UserVo;
import cn.codex.netdisk.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author codex
 * @since 2021-01-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 根据用户名获取用户信息(包括用户组信息)
     *
     * @param username 用户名
     * @return 用户信息
     */
    @Override
    public User selectUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    /**
     * 获取登录用户信息
     *
     * @return 登录用户信息
     */
    @Override
    public ServerResponse<UserVo> getLoginUserInfo(HttpServletRequest request) {
        LoginUser loginUser = jwtTokenUtil.getLoginUser(request);
        loginUser.getUser().setPassword(null);

        UserVo userVo = new UserVo();

        BeanUtils.copyProperties(loginUser.getUser(), userVo);
        userVo.setGroupName(loginUser.getUser().getUserGroups().getGroupName());
        userVo.setMaxFileSize(loginUser.getUser().getUserGroups().getMaxFileSize());
        userVo.setMaxStorageSpace(loginUser.getUser().getUserGroups().getMaxStorageSpace());

        return ServerResponse.createBySuccess(userVo);
    }

    /**
     * 修改密码
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @param code        验证码
     * @param uuid        验证码唯一标识符
     * @return 密码修改结果
     */
    @Override
    public ServerResponse updatePwd(String oldPassword, String newPassword, String code, String uuid) {
        if (Strings.isNullOrEmpty(oldPassword) || Strings.isNullOrEmpty(newPassword) || Strings.isNullOrEmpty(code) || Strings.isNullOrEmpty(uuid)) {
            return ServerResponse.createByErrorMessage(ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        // 获取登录的用户信息
        LoginUser loginUser = SecurityUtil.getLoginUser();
        // 旧密码不正确
        if (!passwordEncoder.matches(oldPassword, loginUser.getPassword())) {
            return ServerResponse.createByErrorMessage(ReturnMessage.OLD_PASSWORD_ERROR);
        }

        if (newPassword.trim().length() < Const.PASSWORD_MIN_LENGTH || newPassword.trim().length() > Const.PASSWORD_MAX_LENGTH) {
            return ServerResponse.createByErrorMessage(ReturnMessage.PASSWORD_ILLEGAL);
        }

        // 验证验证码是否正确
        redisUtil.validateCaptcha(Const.UPDATE_KEY + uuid, code);

        // 修改密码
        newPassword = passwordEncoder.encode(newPassword.trim());
        User user = new User();
        user.setUserId(loginUser.getUser().getUserId());
        user.setPassword(newPassword);
        int resultCount = userMapper.updateById(user);

        if (resultCount > 0) {
            // 清除redis中的登录信息，要求用户重新登录
            jwtTokenUtil.delLoginUser(loginUser.getToken());
            return ServerResponse.createBySuccessMessage(ReturnMessage.UPDATE_PASSWORD_SUCCESS);
        }

        return ServerResponse.createByErrorMessage(ReturnMessage.UPDATE_PASSWORD_ERROR);
    }
}
