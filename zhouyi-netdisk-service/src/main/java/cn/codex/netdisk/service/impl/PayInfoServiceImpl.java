package cn.codex.netdisk.service.impl;

import cn.codex.netdisk.model.entity.PayInfo;
import cn.codex.netdisk.dao.PayInfoMapper;
import cn.codex.netdisk.service.IPayInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付信息表 服务实现类
 * </p>
 *
 * @author codex
 * @since 2021-01-28
 */
@Service
public class PayInfoServiceImpl extends ServiceImpl<PayInfoMapper, PayInfo> implements IPayInfoService {

}
