package cn.codex.netdisk.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 昵称显示层对象
 *
 * @author CodeX
 * @since 2020-11-04 15:49:39
 */
@Getter
@Setter
@ToString
@SuppressWarnings("serial")
public class NicknameVo implements Serializable {
    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;
}
