package cn.codex.netdisk.service.impl;

import cn.codex.netdisk.common.dtos.LoginDto;
import cn.codex.netdisk.dao.UserMapper;
import cn.codex.netdisk.model.entity.User;
import cn.codex.netdisk.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
    
    @Resource
    private AuthenticationManager authenticationManager;
    
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
    
}
