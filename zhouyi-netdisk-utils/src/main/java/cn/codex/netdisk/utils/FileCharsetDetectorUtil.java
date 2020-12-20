package cn.codex.netdisk.utils;

import info.monitorenter.cpdetector.io.*;

import java.io.File;
import java.nio.charset.Charset;

/**
 * <pre>
 * 1、cpDetector内置了一些常用的探测实现类,这些探测实现类的实例可以通过add方法加进来,
 * 如:ParsingDetector、 JChardetFacade、ASCIIDetector、UnicodeDetector.
 * 2、detector按照“谁最先返回非空的探测结果,就以该结果为准”的原则.
 * 3、cpDetector是基于统计学原理的,不保证完全正确.
 * </pre>
 *
 * @author CodeX
 * @since 2020-11-02 15:33:58
 */
public class FileCharsetDetectorUtil {
    public static String getFileEncode(File file) {
        CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();

        detector.add(new ParsingDetector(false));
        detector.add(UnicodeDetector.getInstance());
        /*
        内部引用了 chardet.jar的类
         */
        detector.add(JChardetFacade.getInstance());
        detector.add(ASCIIDetector.getInstance());

        Charset charset = null;
        try {
            charset = detector.detectCodepage(file.toURI().toURL());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //默认为GBK
        String charsetName = "GBK";
        if (charset != null) {
            if (charset.name().equals("US-ASCII")) {
                charsetName = "ISO_8859_1";
            } else {
                charsetName = charset.name();
            }
        }
        return charsetName;
    }
}
