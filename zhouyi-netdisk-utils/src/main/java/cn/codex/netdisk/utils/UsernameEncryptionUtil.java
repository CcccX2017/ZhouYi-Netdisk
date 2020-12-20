package cn.codex.netdisk.utils;

/**
 * @author CodeX
 * @since 2020-11-03 16:25:27
 */
public class UsernameEncryptionUtil {

    /**
     * 加密显示用户名
     *
     * @param username 需要加密的用户名
     * @return 加密后的用户名
     */
    public static String encryptionUsername(String username) {

        int length = username.length();

        if (length >= 3 && length <= 6) {
            username = username.replaceAll("(?<=[\u4e00-\u9fa5\\w]{1})[\u4e00-\u9fa5\\w](?=[\u4e00-\u9fa5\\w]{1})", "*");
        } else if (length == 7) {
            username = username.replaceAll("(?<=[\u4e00-\u9fa5\\w]{1})[\u4e00-\u9fa5\\w](?=[\u4e00-\u9fa5\\w]{2})", "*");
        } else if (length == 8) {
            username = username.replaceAll("(?<=[\u4e00-\u9fa5\\w]{2})[\u4e00-\u9fa5\\w](?=[\u4e00-\u9fa5\\w]{2})", "*");
        } else if (length == 9) {
            username = username.replaceAll("(?<=[\u4e00-\u9fa5\\w]{2})[\u4e00-\u9fa5\\w](?=[\u4e00-\u9fa5\\w]{3})", "*");
        } else if (length == 10) {
            username = username.replaceAll("(?<=[\u4e00-\u9fa5\\w]{3})[\u4e00-\u9fa5\\w](?=[\u4e00-\u9fa5\\w]{3})", "*");
        } else if (length >= 11) {
            username = username.replaceAll("(?<=[\u4e00-\u9fa5\\w]{3})[\u4e00-\u9fa5\\w](?=[\u4e00-\u9fa5\\w]{4})", "*");
        }
        return username;
    }
}
