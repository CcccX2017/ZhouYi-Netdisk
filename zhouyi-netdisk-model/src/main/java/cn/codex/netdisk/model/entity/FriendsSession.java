package cn.codex.netdisk.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 好友会话表
 * </p>
 *
 * @author codex
 * @since 2021-01-28
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

    @ApiModelProperty(value = "是否显示(0-不显示，1-显示，默认1,对应username)")
    private Boolean visitedByUser;

    @ApiModelProperty(value = "是否显示(0-不显示，1-显示，默认1，对应friend)")
    private Boolean visitedByFriend;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;


}
