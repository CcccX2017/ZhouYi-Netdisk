package cn.codex.netdisk.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 好友文件分享表(TbFriendsShare)实体类
 *
 * @author CodeX
 * @since 2020-11-04 14:56:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TbFriendsShare implements Serializable {
    private static final long serialVersionUID = -91780738426328071L;

    private Integer id;
    /**
     * 文件ID组
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
     * 接收者
     */
    private String receiver;
    /**
     * 是否查看（0-未查看，1-已查看，默认0）
     */
    private Integer isView;
    /**
     * 分享时间
     */
    private Date sharingTime;
}