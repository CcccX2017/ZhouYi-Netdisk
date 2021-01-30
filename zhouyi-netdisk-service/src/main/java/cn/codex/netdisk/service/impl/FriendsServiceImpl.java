package cn.codex.netdisk.service.impl;

import cn.codex.netdisk.model.entity.Friends;
import cn.codex.netdisk.dao.FriendsMapper;
import cn.codex.netdisk.service.IFriendsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 好友表 服务实现类
 * </p>
 *
 * @author codex
 * @since 2021-01-28
 */
@Service
public class FriendsServiceImpl extends ServiceImpl<FriendsMapper, Friends> implements IFriendsService {

}
