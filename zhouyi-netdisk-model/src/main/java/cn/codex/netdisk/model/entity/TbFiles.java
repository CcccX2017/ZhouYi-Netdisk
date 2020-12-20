package cn.codex.netdisk.model.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件表(TbFiles)实体类
 *
 * @author CodeX
 * @since 2020-11-04 14:56:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TbFiles implements Serializable {
    private static final long serialVersionUID = 665874269868829338L;
    /**
     * 文件id
     */
    private Long fileId;
    /**
     * 文件真实名称
     */
    private String fileRealName;
    /**
     * 加密后的名称
     */
    private String fileEncryptionName;
    /**
     * 文件存储路径
     */
    private String fileStoragePath;
    /**
     * 文件扩展名
     */
    private String fileExtension;
    /**
     * 文件短地址
     */
    private String fileShortUrl;
    /**
     * 文件大小
     */
    private Long fileSize;
    /**
     * 所有者
     */
    private String username;
    /**
     * 所属文件夹id
     */
    private Integer folderId;
    /**
     * 是否已删除（1-未删除，2-已删除）
     */
    private Integer inRecycle;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 更新时间
     */
    private Date updatedTime;
}