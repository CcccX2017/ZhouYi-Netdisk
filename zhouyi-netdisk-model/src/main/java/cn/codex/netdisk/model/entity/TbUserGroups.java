package cn.codex.netdisk.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户组表(TbUserGroups)实体类
 *
 * @author makejava
 * @since 2020-11-04 14:56:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TbUserGroups implements Serializable {
    private static final long serialVersionUID = 137687551181566513L;
    /**
     * 用户组id
     */
    private Integer groupId;
    /**
     * 组名称
     */
    private String groupName;
    /**
     * 父组id
     */
    private Integer parentId;
    /**
     * 最大存储空间
     */
    private Long maxStorageSpace;
    /**
     * 单个文件最大限制
     */
    private Long maxFileSize;
}