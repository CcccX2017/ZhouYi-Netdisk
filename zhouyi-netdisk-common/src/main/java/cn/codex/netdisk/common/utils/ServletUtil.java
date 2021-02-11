package cn.codex.netdisk.common.utils;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Servlet工具类
 *
 * @author codex
 * @since 2021-02-11
 */
public class ServletUtil {
    
    public static ServletRequestAttributes getRequestAttributes() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }
    
    /**
     * 获取request
     *
     * @return request
     */
    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }
}
