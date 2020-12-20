package cn.codex.netdisk.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 文件显示层对象
 *
 * @author CodeX
 * @since 2020-11-04 15:46:38
 */
@Getter
@Setter
@ToString
@SuppressWarnings("serial")
public class FileListVo implements Serializable {
    /**
     * 文件id
     */
    private Long fileId;

    /**
     * 文件真实名称
     */
    private String fileRealName;

    /**
     * 文件大小
     */
    private String fileSize;

    /**
     * 文件扩展名
     */
    private String fileExtension;

    /**
     * 文件短链接
     */
    private String fileShortUrl;

    /**
     * 文件图标
     */
    private String icon;

    /**
     * 更新时间
     */
    private String updatedTime;
}
