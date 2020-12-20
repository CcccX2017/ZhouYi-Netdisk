package cn.codex.netdisk.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 好友申请显示层对象
 *
 * @author CodeX
 * @since 2020-11-04 15:47:19
 */
@Getter
@Setter
@ToString
@SuppressWarnings("serial")
public class FriendsApplicationVo implements Serializable {

    /**
     * 申请人
     */
    private String applicant;

    /**
     * 被申请人
     */
    private String respondent;

    private String decodeApplicant;

    /**
     * 申请状态
     */
    private Integer state;

    /**
     * 验证消息
     */
    private String messages;

    private Integer count;

    /**
     * 申请时间
     */
    private String createdTime;
}
