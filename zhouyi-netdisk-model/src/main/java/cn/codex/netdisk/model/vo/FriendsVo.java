package cn.codex.netdisk.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 好友列表集合
 *
 * @author codex
 * @since 2021-03-01
 */
@Getter
@Setter
@ApiModel(value = "好友列表集合对象", description = "FriendsVo")
public class FriendsVo implements Serializable {
    public static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名")
    @JsonIgnore
    private String username;

    @ApiModelProperty(value = "用户昵称")
    @JsonIgnore
    private String nickname;

    @ApiModelProperty(value = "用户给好友的备注")
    @JsonIgnore
    private String userToFriendRemark;

    @ApiModelProperty(value = "好友用户名")
    @JsonIgnore
    private String friend;

    @ApiModelProperty(value = "好友昵称")
    @JsonIgnore
    private String friendNickname;

    @ApiModelProperty(value = "好友头像URL")
    @JsonIgnore
    private String friendAvatar;

    @ApiModelProperty(value = "加密的好友用户名")
    @JsonIgnore
    private String encryptionName;

    @ApiModelProperty(value = "好友给用户的备注")
    @JsonIgnore
    private String friendToUserRemark;

    @ApiModelProperty(value = "主键ID", position = 1)
    private Long id;

    @ApiModelProperty(value = "好友显示名称", position = 2)
    private String showName;

    @ApiModelProperty(value = "好友加密账号", position = 3)
    private String account;

    @ApiModelProperty(value = "用户头像URL", position = 4)
    private String avatar;

    @ApiModelProperty(value = "备注", position = 5)
    private String remarks;
}
