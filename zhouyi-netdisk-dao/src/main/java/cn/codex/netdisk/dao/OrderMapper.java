package cn.codex.netdisk.dao;

import cn.codex.netdisk.model.entity.Order;
import cn.codex.netdisk.model.vo.OrderVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author codex
 * @since 2021-01-28
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    
    /**
     * 根据用户名查询用户订单信息
     *
     * @param username 用户名
     * @return 订单列表信息
     */
    List<OrderVo> selectOrderByUsername(String username);
}
