package cn.codex.netdisk.common.utils;

import java.text.DecimalFormat;

/**
 * 字节转显示大小转换工具类
 *
 * @author CodeX
 * @since 2020-11-03 14:17:18
 */
public class FileUtil {

    private static final long ONE_GB = 1073741824;

    private static final long ONE_MB = 1048576;

    private static final long ONE_KB = 1024;

    /**
     * 字节转显示大小
     *
     * @param size 文件大小
     * @return 1G/1M/1KB/1B
     */
    public static String byteCountToDisplaySize(long size) {
        DecimalFormat df = new DecimalFormat("0.00");
        return getDisplaySize(size, df);
    }

    /**
     * 无限制的小数点数转显示大小
     *
     * @param size 文件大小
     * @return 1G/1M/1KB/1B
     */
    public static String unreservedDecimalPoint(long size) {
        DecimalFormat df = new DecimalFormat("0");
        return getDisplaySize(size, df);
    }

    private static String getDisplaySize(long size, DecimalFormat df) {
        String displaySize;
        if (size / ONE_GB > 0) {
            displaySize = df.format((float) size / ONE_GB) + "G";
        } else if (size / ONE_MB > 0) {
            displaySize = df.format((float) size / ONE_MB) + "M";
        } else if (size / ONE_KB > 0) {
            displaySize = df.format((float) size / ONE_KB) + "KB";
        } else {
            displaySize = size + "B";
        }

        return displaySize;
    }
}
