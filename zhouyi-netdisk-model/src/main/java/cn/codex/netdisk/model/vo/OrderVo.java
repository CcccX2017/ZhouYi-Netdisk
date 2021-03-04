package cn.codex.netdisk.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单表现层对象
 *
 * @author codex
 * @since 2021-03-04
 */
@Getter
@Setter
@ApiModel(value = "订单表现层对象", description = "OrderVo")
public class OrderVo implements Serializable {
    public static final long serialVersionUID = 1L;
    
    @ApiModelProperty(value = "订单号")
    private Long orderNo;
    
    @ApiModelProperty(value = "支付方式")
    private String paymentMethod;
    
    @ApiModelProperty(value = "流水号")
    private String paymentNumber;
    
    @ApiModelProperty(value = "购买商品")
    private String groupName;
    
    @ApiModelProperty(value = "支付金额")
    private BigDecimal paymentAmount;
    
    @ApiModelProperty(value = "订单状态")
    private Integer orderStatus;
    
    @ApiModelProperty(value = "订单状态（中文）")
    private String status;
    
    @ApiModelProperty(value = "下单时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreated;
}
