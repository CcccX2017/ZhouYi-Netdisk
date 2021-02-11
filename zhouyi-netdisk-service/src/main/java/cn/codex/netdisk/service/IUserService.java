package cn.codex.netdisk.service;

import cn.codex.netdisk.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author codex
 * @since 2021-01-28
 */
public interface IUserService extends IService<User> {
    
    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    User selectUserByUsername(String username);
    
    
}
