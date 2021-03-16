package cn.codex.netdisk.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 好友会话VO
 *
 * @author CodeX
 * @since 2021-03-16 09:23:34
 */
@Getter
@Setter
@ApiModel(value = "好友会话VO", description = "FriendsSessionVo")
public class FriendsSessionVo implements Serializable {
    public static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "好友")
    private String friend;

    @ApiModelProperty(value = "最新显示信息")
    private String showInfo;

    @ApiModelProperty(value = "标题")
    private String newTitle;

    @ApiModelProperty(value = "最新共享时间")
    private String newSharingTime;

    @ApiModelProperty(value = "条数")
    private Integer count;
}
