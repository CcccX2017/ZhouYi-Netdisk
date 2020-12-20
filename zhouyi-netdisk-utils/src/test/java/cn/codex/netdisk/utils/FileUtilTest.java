package cn.codex.netdisk.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author CodeX
 * @since 2020-11-03 16:36:42
 */
public class FileUtilTest {

    @Test
    public void byteCountToDisplaySize() {
        String s = FileUtil.byteCountToDisplaySize((long) (1024 * 1024 * 1024 + 48884888L));
        System.out.println(s);
    }

    @Test
    public void unreservedDecimalPoint() {
        long size = (long) (1024 * 1024 + (1.0 / 3));
        String s = FileUtil.unreservedDecimalPoint(size);
        System.out.println(s);
    }
}