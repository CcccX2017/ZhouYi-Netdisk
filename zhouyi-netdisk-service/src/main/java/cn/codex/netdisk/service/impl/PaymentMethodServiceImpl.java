package cn.codex.netdisk.service.impl;

import cn.codex.netdisk.dao.PaymentMethodMapper;
import cn.codex.netdisk.model.entity.PaymentMethod;
import cn.codex.netdisk.service.IPaymentMethodService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付方式表 服务实现类
 * </p>
 *
 * @author codex
 * @since 2021-01-28
 */
@Service
public class PaymentMethodServiceImpl extends ServiceImpl<PaymentMethodMapper, PaymentMethod> implements IPaymentMethodService {

}
