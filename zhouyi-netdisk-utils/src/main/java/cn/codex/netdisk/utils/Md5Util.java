package cn.codex.netdisk.utils;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.setting.dialect.Props;

/**
 * @author CodeX
 * @since 2020-11-03 15:54:43
 */
public class Md5Util {

    private final static boolean TO_UPPER_CASE = true;

    /**
     * UTF-8 Md5加密
     *
     * @param origin 需要加密的源数据
     * @return MD5加密后的字符串
     */
    public static String md5EncodeUtf8(String origin) {
        return md5Encode(origin, "UTF-8", TO_UPPER_CASE);
    }

    /**
     * UTF-8 Md5加密
     *
     * @param origin      需要加密的源数据
     * @param toUpperCase 是否将加密的字符串转大写
     * @return MD5加密后的字符串
     */
    public static String md5EncodeUtf8(String origin, boolean toUpperCase) {
        return md5Encode(origin, "UTF-8", toUpperCase);
    }

    /**
     * md5 加密
     *
     * @param origin      需要加密的源数据
     * @param charsetName 编码
     * @return MD5加密后的字符串
     */
    public static String md5Encode(String origin, String charsetName) {
        return md5Encode(origin, charsetName, TO_UPPER_CASE);
    }

    /**
     * md5 加密
     *
     * @param origin      需要加密的源数据
     * @param charsetName 编码
     * @param toUpperCase 是否将加密的字符串转大写
     * @return MD5加密后的字符串
     */
    public static String md5Encode(String origin, String charsetName, Boolean toUpperCase) {
        Props props = new Props("utils.properties", charsetName);

        String salt = props.getProperty("password.salt");

        String md5 = SecureUtil.md5(origin + salt);

        if (toUpperCase == null) {
            toUpperCase = TO_UPPER_CASE;
        }

        if (toUpperCase) {
            return md5.toUpperCase();
        }

        return md5;
    }

}
