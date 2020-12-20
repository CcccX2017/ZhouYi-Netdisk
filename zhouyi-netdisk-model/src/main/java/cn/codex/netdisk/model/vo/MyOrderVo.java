package cn.codex.netdisk.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单显示层对象
 *
 * @author CodeX
 * @since 2020-11-04 15:49:05
 */
@Getter
@Setter
@ToString
@SuppressWarnings("serial")
public class MyOrderVo implements Serializable {
    /**
     * 订单号
     */
    private Long orderNo;

    /**
     * 实际付款金额
     */
    private BigDecimal payment;

    /**
     * 支付类型
     */
    private String paymentType;

    /**
     * 订单状态
     */
    private String status;

    /**
     * 支付平台
     */
    private String payPlatform;

    /**
     * 平台编号
     */
    private String platformNumber;

    /**
     * 商品名称
     */
    private String tradeName;

    /**
     * 创建时间
     */
    private String createTime;
}
