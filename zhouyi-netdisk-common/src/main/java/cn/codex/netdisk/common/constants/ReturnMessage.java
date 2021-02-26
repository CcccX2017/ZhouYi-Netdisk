package cn.codex.netdisk.common.constants;

/**
 * 公共返回消息类
 *
 * @author CodeX
 * @since 2021-02-26 09:52:39
 */
public class ReturnMessage {

    public static final String CAPTCHA_ERROR = "验证码错误";
    public static final String CAPTCHA_EXPIRE = "验证码已失效";
    public static final String CAPTCHA_SEND_ERROR = "验证码发送失败，请重试";
    public static final String CAPTCHA_SEND_SUCCESS = "验证码已发送，请注意查收";

    public static final String LOGIN_SUCCESS = "登录成功";

    public static final String INSERT_SUCCESS = "添加成功";
    public static final String INSERT_ERROR = "添加失败";

    public static final String DELETE_SUCCESS = "删除成功";
    public static final String DELETE_ERROR = "删除失败";

    public static final String UPDATE_SUCCESS = "更新成功";
    public static final String UPDATE_ERROR = "更新失败";
    public static final String UPDATE_PASSWORD_SUCCESS = "修改密码成功";
    public static final String UPDATE_PASSWORD_ERROR = "修改密码失败";

    public static final String REGISTER_SUCCESS = "注册成功";
    public static final String REGISTER_ERROR = "注册失败";

    public static final String USERNAME_PASSWORD_ERROR = "用户名不存在或密码错误";
    public static final String USERNAME_EMPTY = "请输入用户名";
    public static final String USERNAME_ILLEGAL = "用户名须以英文字母开头长度为5-16位的英文/数字/下划线'_'";
    public static final String USERNAME_NOT_EXIST = "用户名不存在";
    public static final String USERNAME_EXIST = "用户名已存在，请更换";

    public static final String EMAIL_EMPTY = "请输入邮箱";
    public static final String EMAIL_ILLEGAL = "请输入正确的邮箱";
    public static final String EMAIL_ERROR = "绑定的邮箱不正确";
    public static final String EMAIL_EXIST = "邮箱已被使用，请更换";

   public static final String PASSWORD_EMPTY = "请输入密码";
   public static final String PASSWORD_ILLEGAL = "密码长度为6-16位";

   public static final String NICKNAME_EMPTY = "请输入用户昵称";
   public static final String NICKNAME_ILLEGAL = "用户昵称只能是长度为5-12位的中英文/数字/下划线'_'";
   public static final String NICKNAME_EXIST = "昵称已被使用，请更换";
}
