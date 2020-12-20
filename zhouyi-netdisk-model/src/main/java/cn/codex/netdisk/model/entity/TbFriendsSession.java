package cn.codex.netdisk.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 好友会话表(TbFriendsSession)实体类
 *
 * @author CodeX
 * @since 2020-11-04 14:56:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TbFriendsSession implements Serializable {
    private static final long serialVersionUID = -57403236057457131L;

    private Integer id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 好友
     */
    private String friend;
    /**
     * 最新分享文件名
     */
    private String newTitle;
    /**
     * 最新分享时间
     */
    private Date newSharingTime;
    /**
     * 是否显示(0-不显示，1-显示，默认1,对应username)
     */
    private Integer visitedUser;
    /**
     * 是否显示(0-不显示，1-显示，默认1，对应friend)
     */
    private Integer visitedFriend;
}