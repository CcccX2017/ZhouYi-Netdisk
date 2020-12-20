package cn.codex.netdisk.utils;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * @author CodeX
 * @since 2020-11-03 16:30:55
 */
public class FileCharsetDetectorUtilTest {

    @Test
    public void getFileEncode() {
        String encode = FileCharsetDetectorUtil.getFileEncode(new File("src/main/java/cn/codex/netdisk/utils" +
                "/UsernameEncryptionUtil.java"));
        System.out.println(encode);
    }
}