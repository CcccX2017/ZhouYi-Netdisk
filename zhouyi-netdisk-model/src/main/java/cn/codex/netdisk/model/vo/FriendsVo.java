package cn.codex.netdisk.model.vo;

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
    
    
    @ApiModelProperty(value = "用户名", position = 1)
    private String username;
    
    @ApiModelProperty(value = "用户昵称", position = 2)
    private String nickname;
    
    @ApiModelProperty(value = "用户头像URL", position = 3)
    private String avatar;
    
    @ApiModelProperty(value = "用户给好友的备注", position = 4)
    private String userToFriendRemark;
    
    @ApiModelProperty(value = "好友用户名", position = 5)
    private String friend;
    
    @ApiModelProperty(value = "好友昵称", position = 6)
    private String friendNickname;
    
    @ApiModelProperty(value = "好友头像URL", position = 7)
    private String friendAvatar;
    
    @ApiModelProperty(value = "好友给用户的备注", position = 8)
    private String friendToUserRemark;
}
