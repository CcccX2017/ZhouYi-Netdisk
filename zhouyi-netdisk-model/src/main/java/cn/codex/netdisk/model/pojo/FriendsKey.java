package cn.codex.netdisk.model.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 好友主键表
 *
 * @author CodeX
 * @since 2020-11-04 15:29:33
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FriendsKey implements Serializable {
    private static final long serialVersionUID = 539638342243649794L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 好友用户名
     */
    private String friendname;
}
