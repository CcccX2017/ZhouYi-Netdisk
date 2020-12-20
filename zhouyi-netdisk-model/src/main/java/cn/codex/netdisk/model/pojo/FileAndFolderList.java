package cn.codex.netdisk.model.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件夹和文件集合
 *
 * @author CodeX
 * @since 2020-11-04 15:21:00
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class FileAndFolderList implements Serializable {
    private static final long serialVersionUID = 539638342243649792L;

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
    private Integer directory;

    /**
     * 文件或文件夹大小
     */
    private Long size;

    /**
     * 文件扩展名
     */
    private String fileExtension;

    /**
     * 文件短链接
     */
    private String fileShortUrl;

    /**
     * 更新时间
     */
    private Date updatedTime;
}
