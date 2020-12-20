package cn.codex.netdisk.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 文件和文件夹显示层对象
 *
 * @author CodeX
 * @since 2020-11-04 15:45:25
 */
@Getter
@Setter
@ToString
@SuppressWarnings("serial")
public class FileAndFolderListVo implements Serializable {
    /**
     * 文件或文件夹id
     */
    private Long id;

    /**
     * 文件或文件夹名称
     */
    private String name;

    /**
     * 文件或文件夹所在目录id
     */
    private String directory;

    /**
     * 文件或文件夹大小
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
     * 上级目录id
     */
    private Integer parentId;

    /**
     * 是否可读
     */
    private Integer isRead;

    /**
     * 是否文件夹
     */
    private Integer isFolder;

    /**
     * 是否视频
     */
    private Integer isVideo;

    /**
     * 是否文档
     */
    private Integer isDocument;

    /**
     * 是否图片
     */
    private Integer isImage;

    /**
     * 是否音乐
     */
    private Integer isMusic;

    /**
     * 更新时间
     */
    private String updatedTime;
}
