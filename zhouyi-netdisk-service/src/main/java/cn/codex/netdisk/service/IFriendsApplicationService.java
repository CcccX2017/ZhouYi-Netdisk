package cn.codex.netdisk.service;

import cn.codex.netdisk.model.entity.FriendsApplication;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 好友申请表 服务类
 * </p>
 *
 * @author codex
 * @since 2021-01-28
 */
public interface IFriendsApplicationService extends IService<FriendsApplication> {

    /**
     * 获取好友申请列表
     *
     * @return 申请列表
     */
    List<FriendsApplication> getFriendsApplicationList();
}
