package cn.codex.netdisk.common.utils;

import cn.hutool.core.util.ReUtil;

/**
 * 正则表达式工具类
 *
 * @author CodeX
 * @since 2021-02-23 16:36:57
 */
public class RegexUtil {
    
    /**
     * 验证输入的邮箱格式是否正确
     *
     * @param email 邮箱
     * @return true - 正确, false - 错误
     */
    public static boolean isEmail(String email) {
        String pattern = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)" +
                "+[\\w](?:[\\w-]*[\\w])?";
        return ReUtil.isMatch(pattern, email);
    }
    
    /**
     * 账号是否合法(字母开头，允许5-16字节，允许字母数字下划线组合)
     *
     * @param account 账号
     * @return true - 合法, false - 不合法
     */
    public static boolean isAccountLegal(String account) {
        String pattern = "^[a-zA-Z]\\w{4,15}$";
        return ReUtil.isMatch(pattern, account);
    }
    
    /**
     * 验证用户昵称是否合法(中英文、下划线、数字，长度5到12位)
     *
     * @param nickname 用户昵称
     * @return true - 合法, false - 不合法
     */
    public static boolean isNicknameLegal(String nickname) {
        String pattern = "^((?:[\\u3400-\\u4DB5\\u4E00-\\u9FEA\\uFA0E\\uFA0F\\uFA11\\uFA13\\uFA14\\uFA1F\\uFA21" +
                "\\uFA23\\uFA24\\uFA27-\\uFA29]|[\\uD840-\\uD868\\uD86A-\\uD86C\\uD86F-\\uD872\\uD874-\\uD879" +
                "][\\uDC00-\\uDFFF]|\\uD869[\\uDC00-\\uDED6\\uDF00-\\uDFFF]|\\uD86D[\\uDC00-\\uDF34\\uDF40-\\uDFFF" +
                "]|\\uD86E[\\uDC00-\\uDC1D\\uDC20-\\uDFFF]|\\uD873[\\uDC00-\\uDEA1\\uDEB0-\\uDFFF]|\\uD87A[\\uDC00" +
                "-\\uDFE0])|(\\w)){5,12}$";
        
        return ReUtil.isMatch(pattern, nickname);
    }
}
