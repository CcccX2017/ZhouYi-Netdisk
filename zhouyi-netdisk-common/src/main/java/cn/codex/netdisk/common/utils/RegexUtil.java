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
     * @param email 邮箱
     * @return true - 正确, false - 错误
     */
    public static boolean isEmail(String email) {
        String pattern = "^(([^<>()[\\\\]\\\\\\\\.,;:\\\\s@\"]+(\\\\.[^<>()[\\\\]\\\\\\\\.,;:\\\\s@\"]+)*)|(\".+\"))@" +
                "((\\\\[[0-9]{1,3}\\\\.[0-9]{1,3}\\\\.[0-9]{1,3}\\\\.[0-9]{1,3}\\\\])|(([a-zA-Z\\\\-0-9]+\\\\.)+[a-zA-Z]{2,}))$";
        return ReUtil.isMatch(pattern, email);
    }
}
