package cn.codex.netdisk.model.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 文件夹和文件查询实体类
 *
 * @author codex
 * @since 2021-03-27
 */
@Getter
@Setter
@ApiModel(value = "文件夹和文件查询实体类", description = "FolderAndFileQueryDto")
public class FolderAndFileQueryDto implements Serializable {
    private static final long serialVersionUID = -4529087909682362550L;
    
    @ApiModelProperty("当前页")
    private Long page;
    
    @ApiModelProperty("页面条数")
    private Long limit;
    
    @ApiModelProperty("所属目录")
    private String dir;
    
    @ApiModelProperty("排序")
    private String order;
    
    @ApiModelProperty("是否降序 1-降序，0-升序")
    private Integer desc;
}
