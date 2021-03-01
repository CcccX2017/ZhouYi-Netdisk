package cn.codex.netdisk.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 好友申请表
 * </p>
 *
 * @author codex
 * @since 2021-02-04
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
    @TableField("is_agreed")
    private Boolean agreed;

    @ApiModelProperty(value = "是否查看（0-未查看，1-已查看，默认0）")
    @TableField("is_viewed")
    private Boolean viewed;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


    public static final String ID = "id";

    public static final String APPLICANT = "applicant";

    public static final String RESPONDENT = "respondent";

    public static final String MESSAGE = "message";

    public static final String IS_AGREED = "is_agreed";

    public static final String IS_VIEWED = "is_viewed";

    public static final String GMT_CREATE = "gmt_create";

    public static final String GMT_MODIFIED = "gmt_modified";

}
