package cn.codex.netdisk.service;

import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.model.entity.User;
import cn.codex.netdisk.model.vo.UserVo;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

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

    /**
     * 获取登录用户信息
     *
     * @param request HttpServletRequest
     * @return 登录用户信息
     */
    ServerResponse<UserVo> getLoginUserInfo(HttpServletRequest request);

    /**
     * 修改密码
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @param code        验证码
     * @param uuid        验证码唯一标识符
     * @return 密码修改结果
     */
    ServerResponse updatePwd(String oldPassword, String newPassword, String code, String uuid);
}
