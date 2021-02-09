package cn.codex.netdisk.portal.config.security.component;

import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.common.enums.ResponseCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 请求Url拦截器
 *
 * @author codex
 * @since 2021-02-09
 */
public class RestUrlInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 门户网站，拦截所有对/backend/**的请求

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        PrintWriter out = response.getWriter();

        ServerResponse<Object> errorCodeMeaage = ServerResponse.createByErrorCodeMeaage(ResponseCode.NO_PERMISSION.getCode(),
                ResponseCode.NO_PERMISSION.getDesc());
        out.write(new ObjectMapper().writeValueAsString(errorCodeMeaage));
        out.flush();
        out.close();

        return false;
    }
}
