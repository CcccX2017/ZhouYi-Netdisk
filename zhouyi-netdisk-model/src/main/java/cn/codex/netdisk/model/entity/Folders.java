package cn.codex.netdisk.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 目录表
 * </p>
 *
 * @author codex
 * @since 2021-02-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_folders")
@ApiModel(value="Folders对象", description="目录表")
public class Folders implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "目录id")
    @TableId(value = "folder_id", type = IdType.ASSIGN_ID)
    private Long folderId;

    @ApiModelProperty(value = "上级目录id")
    private Long parentId;

    @ApiModelProperty(value = "文件夹名")
    private String folderName;

    @ApiModelProperty(value = "是否隐藏（0-未隐藏，1-隐藏）")
    private Boolean hidden;

    @ApiModelProperty(value = "是否删除（0-未删除，1-已删除）")
    @TableLogic
    private Boolean deleted;

    @ApiModelProperty(value = "创建者")
    private String creator;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;


    public static final String FOLDER_ID = "folder_id";

    public static final String PARENT_ID = "parent_id";

    public static final String FOLDER_NAME = "folder_name";

    public static final String IS_HIDDEN = "is_hidden";

    public static final String IS_DELETED = "is_deleted";

    public static final String CREATOR = "creator";

    public static final String GMT_CREATE = "gmt_create";

    public static final String GMT_MODIFIED = "gmt_modified";

}
