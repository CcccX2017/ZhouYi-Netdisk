package cn.codex.netdisk.utils;

import org.junit.Test;

/**
 * @author CodeX
 * @since 2020-11-03 16:04:46
 */
public class Md5UtilTest {

    @Test
    public void md5EncodeUtf8(){
        String s = Md5Util.md5EncodeUtf8("123");
        System.out.println(s);
    }

}