package cn.codex.netdisk.service.impl;

import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.common.utils.SecurityUtil;
import cn.codex.netdisk.dao.FriendsMapper;
import cn.codex.netdisk.model.entity.Friends;
import cn.codex.netdisk.model.vo.FriendsVo;
import cn.codex.netdisk.service.IFriendsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    
    @Autowired
    private FriendsMapper friendsMapper;
    
    /**
     * 获取好友列表
     *
     * @return 好友列表集合
     */
    @Override
    public List<FriendsVo> getFriends() {
        String username = SecurityUtil.getUsername();
        return friendsMapper.selectFriends(username);
    }
}
