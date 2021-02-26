package cn.codex.netdisk.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author codex
 * @since 2021-02-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_user")
@ApiModel(value = "User对象", description = "用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    private Long userId;

    @ApiModelProperty(value = "用户组id")
    private Integer groupId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "用户密码")
    private String password;

    @ApiModelProperty(value = "盐")
    private String salt;

    @ApiModelProperty(value = "账号状态（0-停用 1-启用）")
    private Boolean status;

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

    @ApiModelProperty(value = "性别（0-男 1-女 2-未知）")
    private String sex;

    @ApiModelProperty(value = "生日")
    @JsonFormat(pattern = "yyyy-MM-dd", locale = "Asia/Shanghai", timezone = "GMT+8")
    private Date birthday;

    @ApiModelProperty(value = "已用存储空间")
    private Long usedStorageSpace;

    @ApiModelProperty(value = "资料是否完善（0-未完善，1-已完善）")
    @TableField("is_data_perfect")
    private Boolean dataPerfect;

    @ApiModelProperty(value = "会员过期时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "Asia/Shanghai", timezone = "GMT+8")
    private Date expirationTime;

    @ApiModelProperty(value = "最后登录ip")
    private String loginIp;

    @ApiModelProperty(value = "最后登录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "Asia/Shanghai", timezone = "GMT+8")
    private Date loginDate;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "Asia/Shanghai", timezone = "GMT+8")
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "Asia/Shanghai", timezone = "GMT+8")
    private Date gmtModified;

    @ApiModelProperty(value = "用户组")
    @TableField(exist = false)
    private UserGroups userGroups;

    public static final String USER_ID = "user_id";

    public static final String GROUP_ID = "group_id";

    public static final String USERNAME = "username";

    public static final String PASSWORD = "password";

    public static final String SALT = "salt";

    public static final String STATUS = "status";

    public static final String NICKNAME = "nickname";

    public static final String AVATAR = "avatar";

    public static final String REAL_NAME = "real_name";

    public static final String PHONE = "phone";

    public static final String EMAIL = "email";

    public static final String SEX = "sex";

    public static final String BIRTHDAY = "birthday";

    public static final String USED_STORAGE_SPACE = "used_storage_space";

    public static final String IS_DATA_PERFECT = "is_data_perfect";

    public static final String EXPIRATION_TIME = "expiration_time";

    public static final String LOGIN_IP = "login_ip";

    public static final String LOGIN_DATE = "login_date";

    public static final String GMT_CREATE = "gmt_create";

    public static final String GMT_MODIFIED = "gmt_modified";
    
    @JsonIgnore
    public String getSalt() {
        return salt;
    }
}
