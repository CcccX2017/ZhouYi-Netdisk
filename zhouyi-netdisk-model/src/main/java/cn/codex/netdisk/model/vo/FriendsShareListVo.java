package cn.codex.netdisk.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 好友文件分享对象显示层对象
 *
 * @author CodeX
 * @since 2020-11-04 15:48:29
 */
@Getter
@Setter
@ToString
@SuppressWarnings("serial")
public class FriendsShareListVo implements Serializable {
    /**
     * 分享id
     */
    private Integer id;

    /**
     * 文件id组
     */
    private String fileIdGroup;

    /**
     * 分享标题
     */
    private String title;

    /**
     * 分享人
     */
    private String distributors;

    /**
     * 分享时间
     */
    private String sharingTime;

    /**
     * 图标
     */
    private String icon;

    private int isDistributors;

    /**
     * 文件大小
     */
    private String size;

    /**
     * 次数
     */
    private String time;

    private int isMulti;
}
