package cn.codex.netdisk.portal.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 用户注册对象
 *
 * @author codex
 * @since 2021-02-21
 */
@Getter
@Setter
@ApiModel(value="用户注册对象", description="用户注册对象")
public class RegisterDto implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty(value = "用户名")
    private String username;
    
    @ApiModelProperty(value = "密码")
    private String password;
    
    @ApiModelProperty(value = "用户昵称")
    private String nickname;
    
    @ApiModelProperty(value = "验证码唯一标识")
    private String uuid;
    
    @ApiModelProperty(value = "验证码")
    private String code;
    
    
}
