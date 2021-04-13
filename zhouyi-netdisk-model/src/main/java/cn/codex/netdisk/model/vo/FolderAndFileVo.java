package cn.codex.netdisk.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件夹和文件显示实体类
 *
 * @author codex
 * @since 2021-03-27
 */
@Getter
@Setter
@ApiModel(value = "文件夹和文件显示实体类", description = "FolderAndFileVo")
public class FolderAndFileVo implements Serializable {
    private static final long serialVersionUID = -6944604326521498739L;
    
    @ApiModelProperty("id")
    private Long id;
    
    @ApiModelProperty("显示名称")
    private String name;
    
    @ApiModelProperty("是否文件夹")
    private Integer isDir;
    
    @ApiModelProperty("文件大小")
    private Long size;
    
    @ApiModelProperty("格式化后的文件大小")
    private String sizeStr;

    @ApiModelProperty("所在目录")
    private String dir;

    @ApiModelProperty("路径")
    private String path;
    
    @ApiModelProperty("文件类型")
    private String type;
    
    @ApiModelProperty("图标")
    private String icon;
    
    @ApiModelProperty("修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date gmtModified;
}
