package cn.codex.netdisk.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 好友会话显示层对象
 *
 * @author CodeX
 * @since 2020-11-04 15:47:58
 */
@Getter
@Setter
@ToString
@SuppressWarnings("serial")
public class FriendsSessionVo implements Serializable {
    /**
     * 会话id
     */
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
     * 显示i信息
     */
    private String showInfo;

    /**
     * 会话标题
     */
    private String newTitle;

    private String newSharingTime;

    private Integer count;
}
