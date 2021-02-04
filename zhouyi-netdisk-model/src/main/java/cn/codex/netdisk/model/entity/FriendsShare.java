package cn.codex.netdisk.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 好友文件分享表
 * </p>
 *
 * @author codex
 * @since 2021-02-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_friends_share")
@ApiModel(value="FriendsShare对象", description="好友文件分享表")
public class FriendsShare implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "文件ID组")
    private String fileIdGroup;

    @ApiModelProperty(value = "分享的标题")
    private String shareTitle;

    @ApiModelProperty(value = "分享人")
    private String distributors;

    @ApiModelProperty(value = "接收者")
    private String receiver;

    @ApiModelProperty(value = "是否查看（0-未查看，1-已查看）")
    @TableField("is_viewed")
    private Boolean viewed;

    @ApiModelProperty(value = "分享时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;


    public static final String ID = "id";

    public static final String FILE_ID_GROUP = "file_id_group";

    public static final String SHARE_TITLE = "share_title";

    public static final String DISTRIBUTORS = "distributors";

    public static final String RECEIVER = "receiver";

    public static final String IS_VIEWED = "is_viewed";

    public static final String GMT_CREATE = "gmt_create";

    public static final String GMT_MODIFIED = "gmt_modified";

}
