package cn.codex.netdisk.service.impl;

import cn.codex.netdisk.common.dtos.LoginUser;
import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.common.utils.SecurityUtil;
import cn.codex.netdisk.dao.FriendsApplicationMapper;
import cn.codex.netdisk.model.entity.FriendsApplication;
import cn.codex.netdisk.service.IFriendsApplicationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 好友申请表 服务实现类
 * </p>
 *
 * @author codex
 * @since 2021-01-28
 */
@Service
public class FriendsApplicationServiceImpl extends ServiceImpl<FriendsApplicationMapper, FriendsApplication> implements IFriendsApplicationService {

    @Autowired
    private FriendsApplicationMapper friendsApplicationMapper;

    /**
     * 获取好友申请列表
     *
     * @return 申请列表
     */
    @Override
    public List<FriendsApplication> getFriendsApplicationList() {
        LoginUser loginUser = SecurityUtil.getLoginUser();
        return friendsApplicationMapper.selectList(new QueryWrapper<FriendsApplication>().eq(FriendsApplication.RESPONDENT,
                loginUser.getUser().getUsername()).or().eq(FriendsApplication.RESPONDENT,
                loginUser.getUser().getEmail()));
    }
}
