package cn.codex.netdisk.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 个人信息
 *
 * @author codex
 * @since 2021-02-25
 */
@Getter
@Setter
@ApiModel(value = "个人信息对象", description = "UserVo")
public class UserVo implements Serializable {
    public static final long serialVersionUID = 1L;
    
    @ApiModelProperty(value = "用户ID")
    private Long userId;
    
    @ApiModelProperty(value = "用户名")
    private String username;
    
    @ApiModelProperty(value = "用户昵称")
    private String nickname;
    
    @ApiModelProperty(value = "头像URL")
    private String avatar;
    
    @ApiModelProperty(value = "真实姓名")
    private String realName;
    
    @ApiModelProperty(value = "手机号码")
    private String phone;
    
    @ApiModelProperty(value = "邮箱")
    private String email;
    
    @ApiModelProperty(value = "生日")
    @JsonFormat(pattern = "yyyy-MM-dd", locale = "Asia/Shanghai", timezone = "GMT+8")
    private Date birthday;
    
    @ApiModelProperty(value = "性别（0-男 1-女 2-未知）")
    private String sex;
    
    @ApiModelProperty(value = "已用存储空间")
    private Long usedStorageSpace;
    
    @ApiModelProperty(value = "资料是否完善（0-未完善，1-已完善）")
    private boolean dataPerfect;
    
    @ApiModelProperty(value = "会员过期时间")
    @JsonFormat(pattern = "yyyy-MM-dd", locale = "Asia/Shanghai", timezone = "GMT+8")
    private Date expirationTime;
    
    @ApiModelProperty(value = "用户组id")
    private Integer groupId;
    
    @ApiModelProperty(value = "组名称")
    private String groupName;
    
    @ApiModelProperty(value = "最大存储空间")
    private Long maxStorageSpace;
    
    @ApiModelProperty(value = "单个文件最大限制")
    private Long maxFileSize;
}
