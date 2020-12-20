package cn.codex.netdisk.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 好友申请表(TbFriendsApplication)实体类
 *
 * @author CodeX
 * @since 2020-11-04 14:56:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TbFriendsApplication implements Serializable {
    private static final long serialVersionUID = 895083217747993633L;
    /**
     * 申请人
     */
    private String applicant;
    /**
     * 被申请人
     */
    private String respondent;
    /**
     * 验证消息
     */
    private String messages;
    /**
     * 申请状态（0-未同意，1-已同意，默认0）
     */
    private Integer state;
    /**
     * 是否查看（0-未查看，1-已查看，默认0）
     */
    private Integer isView;

    private Date createdTime;
}