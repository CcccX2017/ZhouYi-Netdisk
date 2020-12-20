package cn.codex.netdisk.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 路径分享显示层对象
 *
 * @author CodeX
 * @since 2020-11-04 15:52:21
 */
@Getter
@Setter
@ToString
@SuppressWarnings("serial")
public class UrlShareListVo implements Serializable {
    /**
     * 路径分享id
     */
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 文件id组
     */
    private String fileIdGroup;

    /**
     * 链接
     */
    private String link;

    /**
     * 短地址
     */
    private String shortUrl;

    /**
     * 分享时间
     */
    private String sharingTime;

    /**
     * 失效时间
     */
    private String failureTime;

    /**
     * 用户名
     */
    private String username;

    /**
     * 浏览次数
     */
    private Integer viewed;

    /**
     * 保存次数
     */
    private Integer saveTimes;

    /**
     * 下载次数
     */
    private Integer downloads;

    /**
     * 提取码
     */
    private String fileKey;

    /**
     * 分享类型
     */
    private String type;

    /**
     * 图标
     */
    private String icon;
}
