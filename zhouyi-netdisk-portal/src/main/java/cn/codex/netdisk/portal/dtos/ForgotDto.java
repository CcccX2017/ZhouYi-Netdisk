package cn.codex.netdisk.portal.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 重置密码对象
 *
 * @author CodeX
 * @since 2021-02-22 15:12:11
 */
@Getter
@Setter
@ApiModel(value = "重置密码对象", description = "ForgotDto")
public class ForgotDto implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty(value = "邮箱", position = 1)
    private String email;
    
    @ApiModelProperty(value = "验证码", position = 2)
    private String code;
    
    @ApiModelProperty(value = "新密码", position = 3)
    private String password;
    
    @ApiModelProperty(value = "验证码唯一标识", position = 4)
    private String uuid;
}
