package cn.codex.netdisk.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 个人信息显示层对象
 *
 * @author CodeX
 * @since 2020-11-04 15:51:49
 */
@Getter
@Setter
@ToString
@SuppressWarnings("serial")
public class PersonalInfomationVo implements Serializable {
    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 真实姓名
     */
    private String realname;

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
    private String birthday;
}
