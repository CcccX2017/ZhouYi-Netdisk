package cn.codex.netdisk.portal.config.security.component;

import cn.codex.netdisk.common.constants.Const;
import cn.codex.netdisk.common.utils.RedisUtil;
import cn.codex.netdisk.model.entity.User;
import cn.codex.netdisk.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT登录授权过滤器
 *
 * @author codex
 * @since 2021-01-24
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private IUserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(tokenHeader);
        if (null != authHeader && authHeader.startsWith(tokenHead)) {
            String authToken = authHeader.substring(tokenHead.length());
            String username = jwtTokenUtil.getUserNameFromToken(authToken);

            // token存在用户名
            if (!Strings.isNullOrEmpty(username)) {
                User user = redisUtil.getObject(Const.LOGINUSER_KEY + username);
                // 用户未登录
                if (user == null) {
                    // 登录
                    user = userService.getOne(new QueryWrapper<User>().eq(User.USERNAME, username));
                    if (jwtTokenUtil.validateToken(authToken, user)) {
                        user.setPassword("");
                        user.setSalt("");
                        // 将登录的用户信息缓存到redis中
                        redisUtil.setObject(Const.LOGINUSER_KEY + username, user);
                    }
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}