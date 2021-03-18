package cn.codex.netdisk.model.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 文件重命名数据传输对象
 *
 * @author CodeX
 * @since 2021-03-18 13:27:42
 */
@Getter
@Setter
@ApiModel(value = "FileRenameDto对象", description = "文件重命名数据传输对象")
public class FileRenameDto implements Serializable {
    private static final long serialVersionUID = 2311730775632078533L;

    @ApiModelProperty(value = "新文件名")
    private String newName;

    @ApiModelProperty(value = "文件所在路径")
    private String dir;

    @ApiModelProperty(value = "重命名类型")
    private String type;
}
