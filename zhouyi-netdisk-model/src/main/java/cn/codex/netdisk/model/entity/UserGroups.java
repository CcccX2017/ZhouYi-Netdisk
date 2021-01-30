package cn.codex.netdisk.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户组表
 * </p>
 *
 * @author codex
 * @since 2021-01-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_user_groups")
@ApiModel(value="UserGroups对象", description="用户组表")
public class UserGroups implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户组id")
    @TableId(value = "group_id", type = IdType.AUTO)
    private Integer groupId;

    @ApiModelProperty(value = "组名称")
    private String groupName;

    @ApiModelProperty(value = "父组id")
    private Integer parentId;

    @ApiModelProperty(value = "最大存储空间")
    private Long maxStorageSpace;

    @ApiModelProperty(value = "单个文件最大限制")
    private Long maxFileSize;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;


}
