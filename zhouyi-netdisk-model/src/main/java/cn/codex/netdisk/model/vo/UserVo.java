package cn.codex.netdisk.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 用户显示层对象
 *
 * @author CodeX
 * @since 2020-11-04 15:52:54
 */
@Getter
@Setter
@ToString
@SuppressWarnings("serial")
public class UserVo implements Serializable {
    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 加密后的用户名
     */
    private String encryptionName;

    /**
     * 备注
     */
    private String remark;
}
