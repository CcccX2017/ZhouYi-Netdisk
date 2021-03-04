package cn.codex.netdisk.service;

import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.model.entity.Friends;
import cn.codex.netdisk.model.vo.FriendsVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 好友表 服务类
 * </p>
 *
 * @author codex
 * @since 2021-01-28
 */
public interface IFriendsService extends IService<Friends> {
    /**
     * 获取好友列表
     *
     * @return 好友列表集合
     */
    List<FriendsVo> getFriends();

    /**
     * 搜索用户添加好友
     *
     * @param keyword 关键词
     * @return 用户信息
     */
    Map<String, Object> searchFriends(String keyword);

    /**
     * 添加好友备注
     *
     * @param id     好友表主键ID
     * @param remark 备注
     * @return 添加结果
     */
    ServerResponse setRemark(Long id, String remark);
}
