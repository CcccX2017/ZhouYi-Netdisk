package cn.codex.netdisk.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单表(TbOrder)实体类
 *
 * @author CodeX
 * @since 2020-11-04 14:56:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TbOrder implements Serializable {
    private static final long serialVersionUID = 565672010157246653L;
    /**
     * 订单号
     */
    private Long orderNo;
    /**
     * 用户名
     */
    private String username;

    private Integer groupId;
    /**
     * 实际付款金额,单位是元,保留两位小数
     */
    private Double payment;
    /**
     * 支付类型,1-在线支付
     */
    private Integer paymentType;
    /**
     * 订单状态:0-已取消-10-未付款，20-已付款，40-交易成功，50-交易关闭
     */
    private Integer status;
    /**
     * 支付时间
     */
    private Date paymentTime;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 更新时间
     */
    private Date updatedTime;
    /**
     * 支付信息表(TbPayInfo)实体类
     */
    private TbPayInfo payInfo;
    /**
     * 用户组表(TbUserGroups)实体类
     */
    private TbUserGroups userGroups;
}