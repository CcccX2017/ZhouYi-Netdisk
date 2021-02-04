package cn.codex.netdisk.model.entity;

import java.math.BigDecimal;
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
 * 订单表
 * </p>
 *
 * @author codex
 * @since 2021-02-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_order")
@ApiModel(value="Order对象", description="订单表")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单号")
    @TableId(value = "order_no", type = IdType.ASSIGN_ID)
    private Long orderNo;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "实际付款金额,单位是元,保留两位小数")
    private BigDecimal paymentAmount;

    @ApiModelProperty(value = "支付方式")
    private Integer paymentMethodId;

    @ApiModelProperty(value = "订单状态:0-已取消，10-未付款，20-已付款，40-交易成功，50-交易关闭")
    private Integer status;

    @ApiModelProperty(value = "购买商品的ID（角色组ID）")
    private Integer groupId;

    @ApiModelProperty(value = "支付时间")
    private Date paymentTime;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;


    public static final String ORDER_NO = "order_no";

    public static final String USERNAME = "username";

    public static final String PAYMENT_AMOUNT = "payment_amount";

    public static final String PAYMENT_METHOD_ID = "payment_method_id";

    public static final String STATUS = "status";

    public static final String GROUP_ID = "group_id";

    public static final String PAYMENT_TIME = "payment_time";

    public static final String GMT_CREATE = "gmt_create";

    public static final String GMT_MODIFIED = "gmt_modified";

}
