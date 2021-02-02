package cn.codex.netdisk.service.impl;

import cn.codex.netdisk.dao.OrderMapper;
import cn.codex.netdisk.model.entity.Order;
import cn.codex.netdisk.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author codex
 * @since 2021-01-28
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

}
