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
 * 文件表
 * </p>
 *
 * @author codex
 * @since 2021-02-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_files")
@ApiModel(value="Files对象", description="文件表")
public class Files implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文件id")
    @TableId(value = "file_id", type = IdType.ASSIGN_ID)
    private Long fileId;

    @ApiModelProperty(value = "文件真实名称")
    private String realName;

    @ApiModelProperty(value = "加密后的名称")
    private String encryptionName;

    @ApiModelProperty(value = "文件存储路径")
    private String storagePath;

    @ApiModelProperty(value = "文件扩展名")
    private String extension;

    @ApiModelProperty(value = "文件短地址")
    private String shortUrl;

    @ApiModelProperty(value = "文件大小")
    private Long size;

    @ApiModelProperty(value = "文件类型")
    private String fileType;
    
    @ApiModelProperty(value = "文件图标")
    private String icon;

    @ApiModelProperty(value = "所属文件夹路径")
    private String dir;

    @ApiModelProperty(value = "是否隐藏（0-未隐藏，1-隐藏）")
    @TableField("is_hidden")
    private Boolean hidden;

    @ApiModelProperty(value = "是否已删除（0-未删除，1-已删除）")
    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;

    @ApiModelProperty(value = "创建者")
    private String creator;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


    public static final String FILE_ID = "file_id";

    public static final String REAL_NAME = "real_name";

    public static final String ENCRYPTION_NAME = "encryption_name";

    public static final String STORAGE_PATH = "storage_path";

    public static final String EXTENSION = "extension";

    public static final String SHORT_URL = "short_url";

    public static final String SIZE = "size";
    
    public static final String FILE_TYPE = "file_type";
    
    public static final String ICON = "icon";

    public static final String DIR = "dir";

    public static final String IS_HIDDEN = "is_hidden";

    public static final String IS_DELETED = "is_deleted";

    public static final String CREATOR = "creator";

    public static final String GMT_CREATE = "gmt_create";

    public static final String GMT_MODIFIED = "gmt_modified";

}
