package cn.codex.netdisk.portal.service.impl;

import cn.codex.netdisk.model.entity.User;
import cn.codex.netdisk.common.dtos.LoginUser;
import cn.codex.netdisk.service.IUserService;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Security用户验证处理实现类
 *
 * @author codex
 * @since 2021-02-11
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    private IUserService userService;
    
    public static final Log log = LogFactory.get();
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.selectUserByUsername(username);
        
        if (user == null) {
            log.info("用户名：{}，不存在", username);
            throw new UsernameNotFoundException("用户名：" + username + "，不存在");
        } else if (!user.getStatus()) {
            log.info("用户名：{}，已停用", username);
            throw new UsernameNotFoundException("用户名：" + username + "，已停用");
        }
        
        return new LoginUser(user);
    }
}
