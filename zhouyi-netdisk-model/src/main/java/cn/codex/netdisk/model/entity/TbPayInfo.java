package cn.codex.netdisk.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 支付信息表(TbPayInfo)实体类
 *
 * @author CodeX
 * @since 2020-11-04 14:56:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TbPayInfo implements Serializable {
    private static final long serialVersionUID = 615483614596909542L;

    private Integer id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 订单号
     */
    private Long orderNo;
    /**
     * 支付平台:1-支付宝,2-微信
     */
    private Integer payPlatform;
    /**
     * 支付宝支付流水号
     */
    private String platformNumber;
    /**
     * 支付宝支付状态
     */
    private String platformStatus;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 更新时间
     */
    private Date updatedTime;
}