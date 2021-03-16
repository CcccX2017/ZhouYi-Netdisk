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
 * 好友分享文件关系表
 *
 * @author CodeX
 * @since 2021-03-16 09:08:48
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_friends_share_group")
@ApiModel(value="FriendsShareGroup对象", description="好友分享文件关系表")
public class FriendsShareGroup implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "分享ID")
    private Long shareId;

    @ApiModelProperty(value = "文件(夹)ID")
    private Long fileId;
}
