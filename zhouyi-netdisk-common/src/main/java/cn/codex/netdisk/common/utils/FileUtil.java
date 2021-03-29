package cn.codex.netdisk.common.utils;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.util.ArrayUtil;

import java.io.InputStream;
import java.text.DecimalFormat;

/**
 * 字节转显示大小转换工具类
 *
 * @author CodeX
 * @since 2020-11-03 14:17:18
 */
public class FileUtil {
    
    public static final String[] IMG_TYPES = {"jpg", "jpeg", "gif", "png", "bmp", "ico"};
    public static final String[] MUSIC_TYPES = {"mp3", "wav", "wma", "flac", "ape", "aac", "ogg"};
    public static final String[] ZIP_TYPES = {"rar", "zip", "7z", "iso", "cab", "arj", "jar", "rar5"};
    public static final String[] VIDEO_TYPES = {"avi", "wmv", "mpeg", "mp4", "mov", "mkv", "flv", "f4v", "m4v", "rmvb", "rm", "3gp", "dat", "ts", "mts", "vob", "mvb"};
    public static final String[] DEVELOP_TYPES = {"html", "htm", "jsp", "java", "js", "vue", "asp", "aspx", "cs", "cpp", "py", "kt", "go", "c", "php"};
    public static final String[] DOC_TYPE = {"txt", "doc", "docx", "xls", "xlsx", "ppt", "pptx"};
    public static final String[] EXECUTABLE_FILE = {"exe", "dmg", "bat", "vbs", "reg"};
    public static final String[] NAVISWORKS = {"nwd", "nwf", "nwc"};
    public static final String[] REVIT = {"rvt", "rfa", "rte", "RFT"};
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
    
    /**
     * 获取文件格式
     *
     * @param inputStream 输入流
     * @param fileName    文件名
     * @return picture，doc，video，seed，music，other
     */
    public static String getFileType(InputStream inputStream, String fileName) {
        String type = FileTypeUtil.getType(inputStream, fileName);
        
        // 图片格式
        if (ArrayUtil.contains(IMG_TYPES, type)) {
            return "picture";
        }
        
        // 音频格式
        if (ArrayUtil.contains(MUSIC_TYPES, type)) {
            return "music";
        }
        
        // 文档格式
        if (ArrayUtil.contains(DOC_TYPE, type)) {
            return "doc";
        }
        
        // 视频格式
        if (ArrayUtil.contains(VIDEO_TYPES, type)) {
            return "video";
        }
        
        // 种子格式
        if (ArrayUtil.contains(VIDEO_TYPES, type)) {
            return "seed";
        }
        
        return "other";
    }
    
    /**
     * 根据后缀名获取文件图标
     *
     * @param extName 后缀名
     * @return 图标名称
     */
    public static String getFileIcon(String extName) {
        if (ArrayUtil.contains(IMG_TYPES, extName)) {
            return "picture.png";
        }
        
        if (ArrayUtil.contains(MUSIC_TYPES, extName)) {
            return "audio.png";
        }
        
        if (ArrayUtil.contains(ZIP_TYPES, extName)) {
            return "zip.png";
        }
        
        if (ArrayUtil.contains(VIDEO_TYPES, extName)) {
            return "video.png";
        }
        
        if (ArrayUtil.contains(DEVELOP_TYPES, extName)) {
            return "html.png";
        }
        
        // word
        if ("doc".equalsIgnoreCase(extName) || "docx".equalsIgnoreCase(extName)) {
            return "word.png";
        }
        
        // excel
        if ("xls".equalsIgnoreCase(extName) || "xlsx".equalsIgnoreCase(extName)) {
            return "excel.png";
        }
        
        // ppt
        if ("ppt".equalsIgnoreCase(extName) || "pptx".equalsIgnoreCase(extName)) {
            return "ppt.png";
        }
        
        // visio
        if ("vsd".equalsIgnoreCase(extName) || "vsdx".equalsIgnoreCase(extName)) {
            return "vsdx.png";
        }
        
        // apk
        if ("apk".equalsIgnoreCase(extName)) {
            return "apk.png";
        }
        
        // torrent
        if ("torrent".equalsIgnoreCase(extName)) {
            return "bt.png";
        }
        
        if ("txt".equalsIgnoreCase(extName)) {
            return "text.png";
        }
        
        if ("ipa".equalsIgnoreCase(extName)) {
            return "ipa.png";
        }
        
        if (ArrayUtil.contains(EXECUTABLE_FILE, extName)) {
            return "obj.png";
        }
        
        if ("ttf".equalsIgnoreCase(extName) || "otf".equalsIgnoreCase(extName)) {
            return "font.png";
        }
        
        if ("ifc".equalsIgnoreCase(extName)) {
            return "ifc.png";
        }
        
        if (ArrayUtil.contains(NAVISWORKS, extName)) {
            return "navisworks.png";
        }
        
        if ("vcf".equalsIgnoreCase(extName)) {
            return "vcf.png";
        }
        
        if ("pdf".equalsIgnoreCase(extName)) {
            return "pdf.png";
        }
        
        if (ArrayUtil.contains(REVIT,extName)){
            return "revit.png";
        }
        
        if ("svg".equalsIgnoreCase(extName)){
            return "vector.png";
        }
    
        if ("dgn".equalsIgnoreCase(extName)){
            return "dgn.png";
        }
        
        if ("dwg".equalsIgnoreCase(extName)){
            return "dwg.png";
        }
        
        return "unknow.png";
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
