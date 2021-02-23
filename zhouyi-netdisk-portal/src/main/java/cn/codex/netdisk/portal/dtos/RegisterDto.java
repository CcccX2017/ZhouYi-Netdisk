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
@ApiModel(value = "用户注册对象", description = "用户注册对象")
public class RegisterDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码", position = 1)
    private String password;

    @ApiModelProperty(value = "用户昵称", position = 2)
    private String nickname;

    @ApiModelProperty(value = "邮箱地址", position = 3)
    private String email;

    @ApiModelProperty(value = "验证码", position = 4)
    private String code;

    @ApiModelProperty(value = "验证码唯一标识", position = 5)
    private String uuid;
}
