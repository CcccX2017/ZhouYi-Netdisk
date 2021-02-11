package cn.codex.netdisk.portal.config.security.component;

import cn.codex.netdisk.common.constants.Const;
import cn.codex.netdisk.common.utils.RedisUtil;
import cn.codex.netdisk.portal.pojo.LoginUser;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Jwt工具类
 *
 * @author codex
 * @since 2021-02-09
 */
@Component
public class JwtTokenUtil {
    
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    
    @Value("${jwt.secret}")
    private String secret;
    
    @Value("${jwt.expiration}")
    private Long expiration;
    
    @Autowired
    private RedisUtil redisUtil;
    
    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;
    
    /**
     * 获取登录用户信息
     *
     * @return 登录用户信息
     */
    public LoginUser getLoginUser(HttpServletRequest request) {
        String token = getToken(request);
        
        if (!Strings.isNullOrEmpty(token)) {
            Claims claims = getClaimsFromToken(token);
            String uuid = (String) claims.get(Const.LOGIN_USER_KEY);
            return redisUtil.getObject(getTokenKey(uuid));
        }
        
        return null;
    }
    
    /**
     * 更新用户信息
     *
     * @param loginUser 登录用户权限信息
     */
    public void updateLoginUser(LoginUser loginUser) {
        if (loginUser != null && !Strings.isNullOrEmpty(loginUser.getToken())) {
            refreshToken(loginUser);
        }
    }
    
    /**
     * 删除登录用户信息
     *
     * @param uuid 用户token
     */
    public void delLoginUser(String uuid) {
        if (!Strings.isNullOrEmpty(uuid)) {
            redisUtil.deleteObject(getTokenKey(uuid));
        }
    }
    
    /**
     * 生成token
     *
     * @param loginUser 登录用户权限信息
     * @return token
     */
    public String generateToken(LoginUser loginUser) {
        String uuid = IdUtil.fastUUID();
        loginUser.setToken(uuid);
        setUserAgent(loginUser);
        refreshToken(loginUser);
        
        Map<String, Object> claims = Maps.newHashMap();
        claims.put(Const.LOGIN_USER_KEY, uuid);
        
        return generateToken(claims);
    }
    
    /**
     * 验证token是否有效，相差不足20分钟自动刷新redis缓存
     *
     * @param loginUser 登录用户权限信息
     */
    public void validateToken(LoginUser loginUser) {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN) {
            refreshToken(loginUser);
        }
        
    }
    
    /**
     * 生成token
     *
     * @param claims 荷载
     * @return token
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
    
    /**
     * 刷新token
     *
     * @param loginUser 登录用户权限信息
     */
    private void refreshToken(LoginUser loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expiration * 60 * 1000);
        // 将loginUser缓存到redis中
        redisUtil.setObject(getTokenKey(loginUser.getToken()), loginUser, expiration, TimeUnit.MINUTES);
    }
    
    /**
     * 设置用户登录ip
     *
     * @param loginUser 登录用户权限信息
     */
    private void setUserAgent(LoginUser loginUser) {
        String clientIP = ServletUtil.getClientIP(cn.codex.netdisk.common.utils.ServletUtil.getRequest());
        loginUser.setIpAddr(clientIP);
    }
    
    /**
     * 根据token获取荷载
     *
     * @param token token
     * @return 荷载
     */
    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
    
    /**
     * 获取token
     *
     * @return token
     */
    private String getToken(HttpServletRequest request) {
        String authToken = request.getHeader(tokenHeader);
        if (!Strings.isNullOrEmpty(authToken) && authToken.startsWith(tokenHead)) {
            authToken = authToken.substring(tokenHead.length() + 1);
        }
        
        return authToken;
    }
    
    /**
     * 通过uuid获取redis中存储的key
     *
     * @param uuid 唯一标识
     * @return redis中存储的key
     */
    private String getTokenKey(String uuid) {
        return Const.LOGIN_TOKEN_KEY + uuid;
    }
}
