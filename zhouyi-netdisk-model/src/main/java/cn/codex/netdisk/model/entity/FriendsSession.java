package cn.codex.netdisk.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 好友会话表
 * </p>
 *
 * @author codex
 * @since 2021-02-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_friends_session")
@ApiModel(value="FriendsSession对象", description="好友会话表")
public class FriendsSession implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "好友用户名")
    private String friend;

    @ApiModelProperty(value = "显示标题")
    private String title;

    @ApiModelProperty(value = "是否显示(0-不显示，1-显示，默认1,对应username)")
    @TableField("is_visited_by_user")
    private Boolean visitedByUser;

    @ApiModelProperty(value = "是否显示(0-不显示，1-显示，默认1，对应friend)")
    @TableField("is_visited_by_friend")
    private Boolean visitedByFriend;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    @ApiModelProperty(value = "好友用户信息")
    @TableField(exist = false)
    private User userInfo;

    @ApiModelProperty(value = "好友备注信息")
    @TableField(exist = false)
    private Friends friends;

    public static final String ID = "id";

    public static final String USERNAME = "username";

    public static final String FRIEND = "friend";

    public static final String TITLE = "title";

    public static final String IS_VISITED_BY_USER = "is_visited_by_user";

    public static final String IS_VISITED_BY_FRIEND = "is_visited_by_friend";

    public static final String GMT_CREATE = "gmt_create";

    public static final String GMT_MODIFIED = "gmt_modified";

}
