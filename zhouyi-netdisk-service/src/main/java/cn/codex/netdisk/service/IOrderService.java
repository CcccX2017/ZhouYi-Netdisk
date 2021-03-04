package cn.codex.netdisk.service;

import cn.codex.netdisk.model.entity.Order;
import cn.codex.netdisk.model.vo.OrderVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author codex
 * @since 2021-01-28
 */
public interface IOrderService extends IService<Order> {
    
    /**
     * 获取订单信息
     *
     * @return 订单列表
     */
    List<OrderVo> getOrder();
}
