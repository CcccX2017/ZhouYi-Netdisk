package cn.codex.netdisk.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件分享表(TbUrlShare)实体类
 *
 * @author makejava
 * @since 2020-11-04 14:56:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TbUrlShare implements Serializable {
    private static final long serialVersionUID = -74920012773868075L;

    private Integer id;
    /**
     * 文件ID组
     */
    private String fileIdGroup;
    /**
     * 分享标题
     */
    private String title;
    /**
     * 链接
     */
    private String link;
    /**
     * 短地址
     */
    private String shortUrl;
    /**
     * 提取码
     */
    private String fileKey;
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
     * 分享时间
     */
    private Date sharingTime;
    /**
     * 失效时间
     */
    private String failureTime;
    /**
     * 分享类型
     */
    private String type;
    /**
     * 分享者
     */
    private String username;
}