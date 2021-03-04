package cn.codex.netdisk.service.impl;

import cn.codex.netdisk.common.utils.SecurityUtil;
import cn.codex.netdisk.dao.OrderMapper;
import cn.codex.netdisk.model.entity.Order;
import cn.codex.netdisk.model.vo.OrderVo;
import cn.codex.netdisk.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    
    @Autowired
    private OrderMapper orderMapper;
    
    /**
     * 获取订单信息
     *
     * @return 订单列表
     */
    @Override
    public List<OrderVo> getOrder() {
        return orderMapper.selectOrderByUsername(SecurityUtil.getUsername());
    }
}
