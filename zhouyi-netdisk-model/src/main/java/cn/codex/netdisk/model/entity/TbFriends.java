package cn.codex.netdisk.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 好友表(TbFriends)实体类
 *
 * @author CodeX
 * @since 2020-11-04 14:56:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TbFriends implements Serializable {
    private static final long serialVersionUID = -48229473477551457L;
    /**
     * 用户名
     */
    private String username;
    /**
     * 好友名
     */
    private String friendname;
    /**
     * 用户给好友的备注
     */
    private String userToFrindRemark;
    /**
     * 好友给用户的备注
     */
    private String frindToUserRemark;
}