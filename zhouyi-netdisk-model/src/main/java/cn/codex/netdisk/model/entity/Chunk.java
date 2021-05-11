package cn.codex.netdisk.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 存储已经上传的分片
 * </p>
 * @author CodeX
 * @since 2021-05-11 11:00:52
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_chunk")
@ApiModel(value="Chunk对象", description="存储已经上传的分片")
public class Chunk implements Serializable {
    private static final long serialVersionUID = -4849919640396032243L;

    @ApiModelProperty(value = "自增id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "文件md5")
    private String md5;

    @ApiModelProperty(value = "已上传的分片")
    private Integer chunk;

    public static final String ID = "id";

    public static final String MD5 = "md5";

    public static final String CHUNK = "chunk";
}
