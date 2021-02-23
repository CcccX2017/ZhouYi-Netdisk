package cn.codex.netdisk.common.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 用户登录对象
 *
 * @author codex
 * @since 2021-02-11
 */
@ApiModel(value = "用户登录对象", description = "LoginDto")
public class LoginDto implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty(value = "用户名")
    private String username;
    
    @ApiModelProperty(value = "用户密码", position = 1)
    private String password;
    
    @ApiModelProperty(value = "验证码", position = 2)
    private String code;
    
    @ApiModelProperty(value = "验证码唯一标识", position = 3)
    private String uuid = "";
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getUuid() {
        return uuid;
    }
    
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
