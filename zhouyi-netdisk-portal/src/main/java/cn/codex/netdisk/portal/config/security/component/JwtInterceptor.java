package cn.codex.netdisk.portal.config.security.component;

import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.common.enums.ResponseCode;
import cn.codex.netdisk.common.utils.RedisUtil;
import cn.codex.netdisk.model.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Jwt拦截器
 *
 * @author codex
 * @since 2021-02-09
 */
public class JwtInterceptor implements HandlerInterceptor {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            String authHeader = request.getHeader(tokenHeader);
            // 有token值
            if (null != authHeader && authHeader.startsWith(tokenHead)) {
                String authToken = authHeader.substring(tokenHead.length());
                String username = jwtTokenUtil.getUserNameFromToken(authToken);
                User user = redisUtil.getObject(username);
                // redis中没有该用户的信息
                if (user == null) {
                    ServerResponse<Object> errorCodeMeaage =
                            ServerResponse.createByErrorCodeMeaage(ResponseCode.NEED_LOGIN.getCode(),
                                    ResponseCode.NEED_LOGIN.getDesc());
                    out.write(new ObjectMapper().writeValueAsString(errorCodeMeaage));
                    out.flush();
                    return false;
                }
                return true;
            }

            // 没有token值
            ServerResponse<Object> errorCodeMeaage =
                    ServerResponse.createByErrorCodeMeaage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
            out.write(new ObjectMapper().writeValueAsString(errorCodeMeaage));
            out.flush();

            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
