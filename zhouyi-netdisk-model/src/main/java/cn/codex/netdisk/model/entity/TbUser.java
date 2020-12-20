package cn.codex.netdisk.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表(TbUser)实体类
 *
 * @author CodeX
 * @since 2020-11-04 14:56:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TbUser implements Serializable {
    private static final long serialVersionUID = -73909243847620653L;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 性别
     */
    private String sex;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * 用户组id
     */
    private Integer groupId;
    /**
     * 已用存储空间
     */
    private Long usedStorageSpace;
    /**
     * 资料是否完善（0-未完善，1-已完善）
     */
    private Integer isDataPerfect;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 更新时间
     */
    private Date updatedTime;
    /**
     * 用户组表(TbUserGroups)实体类
     */
    private TbUserGroups userGroups;
}