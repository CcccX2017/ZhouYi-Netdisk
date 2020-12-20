package cn.codex.netdisk.model.pojo;

import lombok.*;

import java.io.Serializable;

/**
 * 好友请求主键表
 *
 * @author CodeX
 * @since 2020-11-04 15:26:09
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FriendsApplicationKey implements Serializable {
    private static final long serialVersionUID = 539638342243649793L;

    /**
     * 申请人
     */
    private String applicant;

    /**
     * 被申请人
     */
    private String respondent;
}
