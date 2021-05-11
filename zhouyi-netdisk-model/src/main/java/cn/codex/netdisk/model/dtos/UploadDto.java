package cn.codex.netdisk.model.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 文件上传数据传输对象
 *
 * @author CodeX
 * @since 2021-05-11 10:25:11
 */
@Getter
@Setter
@ToString
@ApiModel(value = "UploadDto对象", description = "文件上传数据传输对象")
public class UploadDto implements Serializable {
    private static final long serialVersionUID = 4883327708693988068L;

    @ApiModelProperty(value = "当前分片")
    private Integer chunkNumber;

    @ApiModelProperty(value = "总分片数")
    private Integer totalChunks;

    @ApiModelProperty(value = "分片大小")
    private Long chunkSize;

    @ApiModelProperty(value = "当前分片大小")
    private Long currentChunkSize;

    @ApiModelProperty(value = "文件总大小")
    private Long totalSize;

    @ApiModelProperty(value = "文件MD5")
    private String identifier;

    @ApiModelProperty(value = "文件名")
    private String filename;

    @ApiModelProperty(value = "所属目录")
    private String targetPath;
}
