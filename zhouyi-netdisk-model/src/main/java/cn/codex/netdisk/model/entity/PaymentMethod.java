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
 * 支付方式表
 * </p>
 *
 * @author codex
 * @since 2021-02-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_payment_method")
@ApiModel(value="PaymentMethod对象", description="支付方式表")
public class PaymentMethod implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "支付方式")
    private String paymentMethod;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;


    public static final String ID = "id";

    public static final String PAYMENT_METHOD = "payment_method";

    public static final String GMT_CREATE = "gmt_create";

    public static final String GMT_MODIFIED = "gmt_modified";

}
