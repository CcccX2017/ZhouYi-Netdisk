package cn.codex.netdisk.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 目录表(TbFolders)实体类
 *
 * @author CodeX
 * @since 2020-11-04 14:56:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TbFolders implements Serializable {
    private static final long serialVersionUID = 539638342243649791L;
    /**
     * 目录id
     */
    private Integer folderId;
    /**
     * 上级目录id
     */
    private Integer parentId;
    /**
     * 文件夹名
     */
    private String folderName;
    /**
     * 是否删除（1-未删除，2-已删除）
     */
    private Integer inRecycle;
    /**
     * 所属者
     */
    private String username;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 更新时间
     */
    private Date updatedTime;
}