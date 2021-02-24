package cn.codex.netdisk.portal.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 门户网站全局配置类
 *
 * @author codex
 * @since 2021-01-30
 */
@Component
@ConfigurationProperties(prefix = "portal")
public class PortalConfig {
    /**
     * 作者
     */
    private String author;
    /**
     * 系统名称
     */
    private String name;
    /**
     * 系统中文名
     */
    private String nameZh;
    /**
     * 版本号
     */
    private String version;
    /**
     * 上传路径
     */
    private static String profile;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public static String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        PortalConfig.profile = profile;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    
    public String getNameZh() {
        return nameZh;
    }
    
    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }
    
    /**
     * 获取文件上传路径
     */
    public static String getUploadPath() {
        return getProfile() + "/upload";
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath() {
        return getProfile() + "/avatar";
    }
}
