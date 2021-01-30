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
 * 文件表
 * </p>
 *
 * @author codex
 * @since 2021-01-28
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

    @ApiModelProperty(value = "所属文件夹id")
    private Integer folderId;

    @ApiModelProperty(value = "是否隐藏（0-未隐藏，1-隐藏）")
    private Boolean hidden;

    @ApiModelProperty(value = "是否已删除（0-未删除，1-已删除）")
    @TableLogic
    private Boolean deleted;

    @ApiModelProperty(value = "创建者")
    private String creator;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;


}
