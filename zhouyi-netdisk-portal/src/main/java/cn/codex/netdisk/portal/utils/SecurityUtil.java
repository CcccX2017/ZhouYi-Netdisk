package cn.codex.netdisk.portal.utils;

import cn.codex.netdisk.common.enums.ResponseCode;
import cn.codex.netdisk.common.exception.CustomException;
import cn.codex.netdisk.portal.pojo.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Security工具类
 *
 * @author codex
 * @since 2021-02-11
 */
public class SecurityUtil {
    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
    
    /**
     * 获取用户信息
     */
    public static LoginUser getLoginUser() {
        try {
            return (LoginUser) getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new CustomException(ResponseCode.UNAUTHORIZED.getCode(), "获取用户信息异常");
        }
    }
    
    /**
     * 获取用户名
     */
    public static String getUsername() {
        try {
            return getLoginUser().getUsername();
        } catch (Exception e) {
            throw new CustomException(ResponseCode.UNAUTHORIZED.getCode(), "获取用户名异常");
        }
    }
}
