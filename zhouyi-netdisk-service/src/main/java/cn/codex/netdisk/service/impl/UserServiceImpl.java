package cn.codex.netdisk.service.impl;

import cn.codex.netdisk.common.dtos.LoginUser;
import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.common.utils.JwtTokenUtil;
import cn.codex.netdisk.dao.UserMapper;
import cn.codex.netdisk.model.entity.User;
import cn.codex.netdisk.model.vo.UserVo;
import cn.codex.netdisk.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
}
