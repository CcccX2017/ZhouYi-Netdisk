package cn.codex.netdisk.model.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 文件复制数据传输对象
 *
 * @author CodeX
 * @since 2021-03-18 10:32:47
 */
@Getter
@Setter
@ApiModel(value = "FileCopyDto对象", description = "文件复制数据传输对象")
public class FileCopyDto implements Serializable {
    private static final long serialVersionUID = 2531337987594618360L;

    @ApiModelProperty(value = "文件路径")
    private String path;

    @ApiModelProperty(value = "类型")
    private String type;
}
