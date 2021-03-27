package cn.codex.netdisk.model.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 分页查询返回
 *
 * @author codex
 * @since 2021-03-27
 */
@Getter
@Setter
@ApiModel(value = "分页查询返回", description = "PageResult")
public class PageResult implements Serializable {
    private static final long serialVersionUID = -4965798725710480322L;
    
    @ApiModelProperty(value = "数据条数")
    private Integer count;
    
    @ApiModelProperty(value = "是否全部加载")
    private Integer isAll;
    
    @ApiModelProperty(value = "数据")
    private Object list;
}
