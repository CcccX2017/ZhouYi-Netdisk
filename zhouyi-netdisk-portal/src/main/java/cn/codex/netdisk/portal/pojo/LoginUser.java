package cn.codex.netdisk.portal.pojo;

import cn.codex.netdisk.model.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 登录用户权限信息
 *
 * @author codex
 * @since 2021-02-11
 */
@ApiModel(value = "登录用户权限信息", description = "")
public class LoginUser implements UserDetails {
    
    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty(value = "用户唯一标识")
    private String token;
    
    @ApiModelProperty(value = "登录时间")
    private Long loginTime;
    
    @ApiModelProperty(value = "登录失效时间")
    private Long expireTime;
    
    @ApiModelProperty(value = "用户信息")
    private User user;
    
    @ApiModelProperty(value = "登录ip")
    private String ipAddr;
    
    public String getIpAddr() {
        return ipAddr;
    }
    
    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public Long getLoginTime() {
        return loginTime;
    }
    
    public void setLoginTime(Long loginTime) {
        this.loginTime = loginTime;
    }
    
    public Long getExpireTime() {
        return expireTime;
    }
    
    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public LoginUser() {
    }
    
    public LoginUser(User user) {
        this.user = user;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
    
    @Override
    @JsonIgnore
    public String getPassword() {
        return user.getPassword();
    }
    
    @Override
    public String getUsername() {
        return user.getUsername();
    }
    
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return user.getStatus();
    }
}
