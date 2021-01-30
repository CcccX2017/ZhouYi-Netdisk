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
 * 文件分享表
 * </p>
 *
 * @author codex
 * @since 2021-01-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_url_share")
@ApiModel(value="UrlShare对象", description="文件分享表")
public class UrlShare implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "文件ID组")
    private String fileIdGroup;

    @ApiModelProperty(value = "分享标题")
    private String shareTitle;

    @ApiModelProperty(value = "链接")
    private String link;

    @ApiModelProperty(value = "短地址")
    private String shortUrl;

    @ApiModelProperty(value = "提取码")
    private String fileKey;

    @ApiModelProperty(value = "浏览次数")
    private Integer viewedCount;

    @ApiModelProperty(value = "保存次数")
    private Integer savedCount;

    @ApiModelProperty(value = "下载次数")
    private Integer downloadCount;

    @ApiModelProperty(value = "分享类型(0-公开 1-加密)")
    private Boolean shareType;

    @ApiModelProperty(value = "分享者")
    private String username;

    @ApiModelProperty(value = "是否永久(0-永久 1-非永久)")
    private Boolean permanent;

    @ApiModelProperty(value = "分享状态(0-有效，1-失效， 2-已删除)")
    private Integer shareStatus;

    @ApiModelProperty(value = "失效时间")
    private Date failureTime;

    @ApiModelProperty(value = "分享时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;


}
