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
 * 好友申请表
 * </p>
 *
 * @author codex
 * @since 2021-01-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_friends_application")
@ApiModel(value="FriendsApplication对象", description="好友申请表")
public class FriendsApplication implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "申请人")
    private String applicant;

    @ApiModelProperty(value = "被申请人")
    private String respondent;

    @ApiModelProperty(value = "验证消息")
    private String message;

    @ApiModelProperty(value = "申请状态（0-未同意，1-已同意，默认0）")
    private Boolean agreed;

    @ApiModelProperty(value = "是否查看（0-未查看，1-已查看，默认0）")
    private Boolean viewed;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;


}
