package cn.codex.netdisk.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 目录表
 * </p>
 *
 * @author codex
 * @since 2021-02-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_folders")
@ApiModel(value = "Folders对象", description = "目录表")
public class Folders implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty(value = "目录id", position = 1)
    @TableId(value = "folder_id", type = IdType.INPUT)
    private Long folderId;
    
    @ApiModelProperty(value = "文件夹名", position = 2)
    private String folderName;
    
    @ApiModelProperty(value = "文件夹路径", position = 3)
    private String dir;
    
    @ApiModelProperty(value = "是否隐藏（0-未隐藏，1-隐藏）", position = 4)
    @TableField("is_hidden")
    private Boolean hidden;
    
    @ApiModelProperty(value = "是否删除（0-未删除，1-已删除）", position = 5)
    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;
    
    @ApiModelProperty(value = "创建者", position = 6)
    private String creator;
    
    @ApiModelProperty(value = "创建时间", position = 7)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;
    
    @ApiModelProperty(value = "更新时间", position = 8)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;
    
    
    public static final String FOLDER_ID = "folder_id";
    
    public static final String DIR = "dir";
    
    public static final String FOLDER_NAME = "folder_name";
    
    public static final String IS_HIDDEN = "is_hidden";
    
    public static final String IS_DELETED = "is_deleted";
    
    public static final String CREATOR = "creator";
    
    public static final String GMT_CREATE = "gmt_create";
    
    public static final String GMT_MODIFIED = "gmt_modified";
    
}
