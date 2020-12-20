package cn.codex.netdisk.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 目录显示层对象
 *
 * @author CodeX
 * @since 2020-11-04 15:38:56
 */

@Getter
@Setter
@ToString
@SuppressWarnings("serial")
public class DirectoryVo implements Serializable {

    /**
     * 目录id
     */
    private Integer folderId;

    /**
     * 上级目录id
     */
    private Integer parentId;

    /**
     * 目录名称
     */
    private String name;

    /**
     * 是否主目录
     */
    private Integer isParent;
}
