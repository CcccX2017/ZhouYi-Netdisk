package cn.codex.netdisk.common.constants;

/**
 * 定义系统常量
 *
 * @author CodeX
 * @since 2020-11-03 22:00:49
 */
public class Const {
    /**
     * 普通用户
     */
    public static final int DEFAULT_GROUP_ID = 1001;
    /**
     * 未知性别
     */
    public static final String DEFAULT_SEX = "2";

    /**
     * 虚拟目录
     */
    public static final String VIRTUAL_DIRECTORY = "/rs/";
    
    public static final String CUSTOMER = "普通用户";
    
    public static final String REGULAR_MEMBERS = "普通会员";
    
    public static final String SUPER_MEMBERS = "超级会员";
    
    /**
     * 验证码key
     */
    public static final String CAPTCHA_KEY = "captcha_key_";
    
    /**
     * 验证码有效期（分钟）
     */
    public static final Integer CAPTCHA_EXPIRATION = 2;
    
    /**
     * 找回密码验证码key
     */
    public static final String FORGOT_KEY = "forgot_key_";
    
    /**
     * 找回密码验证码有效期（分钟）
     */
    public static final Integer FORGOT_CAPTCHA_EXPIRATION = 5;

    /**
     * 用户信息key
     */
    public static final String LOGIN_USER_KEY = "login_user_key";
    
    /**
     * 登录用户token key
     */
    public static final String LOGIN_TOKEN_KEY = "login_token_";

    /**
     * 密码最小长度
     */
    public static final int PASSWORD_MIN_LENGTH = 6;
    
    /**
     * 密码最大长度
     */
    public static final int PASSWORD_MAX_LENGTH = 16;
}
