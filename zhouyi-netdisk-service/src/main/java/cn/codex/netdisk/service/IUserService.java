package cn.codex.netdisk.service;

import cn.codex.netdisk.common.dtos.LoginDto;
import cn.codex.netdisk.common.dtos.ServerResponse;
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
     * 用户登录
     *
     * @param loginDto 用户登录对象
     * @return 登录结果
     */
    ServerResponse login(LoginDto loginDto);
}
