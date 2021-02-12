package cn.codex.netdisk.portal.config.security.component;

import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.common.enums.ResponseCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;

/**
 * 当未登录或者token失效访问接口时，自定义的返回结果
 *
 * @author codex
 * @since 2021-02-12
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        
        PrintWriter writer = response.getWriter();
        
        String message = "请求访问：{0}，认证失败，无法访问";
        message = MessageFormat.format(message, request.getRequestURI());
        writer.write(new ObjectMapper().writeValueAsString(ServerResponse.createByErrorCodeMeaage(ResponseCode.UNAUTHORIZED.getCode(), message)));
        
        writer.flush();
        writer.close();
    }
}
