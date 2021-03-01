package cn.codex.netdisk.service;

import cn.codex.netdisk.common.dtos.ServerResponse;
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

    /**
     * 发送添加好友请求
     *
     * @param from    申请人
     * @param to      被申请人
     * @param message 验证消息
     * @return 发送添加好友请求结果
     */
    ServerResponse addFriend(String from, String to, String message);
}
