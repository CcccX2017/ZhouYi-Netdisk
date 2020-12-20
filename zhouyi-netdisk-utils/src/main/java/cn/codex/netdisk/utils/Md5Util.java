package cn.codex.netdisk.utils;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.setting.dialect.Props;

/**
 * @author CodeX
 * @since 2020-11-03 15:54:43
 */
public class Md5Util {

    /**
     * UTF-8 Md5加密
     *
     * @param origin 需要加密的源数据
     * @return MD5加密后的字符串
     */
    public static String md5EncodeUtf8(String origin) {
        Props props = new Props("utils.properties", "UTF-8");

        String salt = props.getProperty("password.salt");

        return SecureUtil.md5(origin + salt).toUpperCase();
    }

}
