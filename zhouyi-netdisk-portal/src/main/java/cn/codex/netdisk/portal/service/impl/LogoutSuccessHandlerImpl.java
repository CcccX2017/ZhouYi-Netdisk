package cn.codex.netdisk.portal.service.impl;

import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.common.utils.JwtTokenUtil;
import cn.codex.netdisk.common.dtos.LoginUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 成功退出处理实现类
 *
 * @author codex
 * @since 2021-02-12
 */
@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        
        LoginUser loginUser = jwtTokenUtil.getLoginUser(request);
        if (loginUser != null) {
            // 删除用户在redis中的缓存
            jwtTokenUtil.delLoginUser(loginUser.getToken());
        }
        
        PrintWriter writer = response.getWriter();
        
        writer.write(new ObjectMapper().writeValueAsString(ServerResponse.createBySuccessMessage("退出成功")));
        
        writer.flush();
        writer.close();
    }
}
