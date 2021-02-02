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
 * 支付信息表
 * </p>
 *
 * @author codex
 * @since 2021-02-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_pay_info")
@ApiModel(value="PayInfo对象", description="支付信息表")
public class PayInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "订单号")
    private Long orderNo;

    @ApiModelProperty(value = "支付流水号")
    private String paymentNumber;

    @ApiModelProperty(value = "支付状态")
    private String paymentStatus;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;


    public static final String ID = "id";

    public static final String USERNAME = "username";

    public static final String ORDER_NO = "order_no";

    public static final String PAYMENT_NUMBER = "payment_number";

    public static final String PAYMENT_STATUS = "payment_status";

    public static final String GMT_CREATE = "gmt_create";

    public static final String GMT_MODIFIED = "gmt_modified";

}
