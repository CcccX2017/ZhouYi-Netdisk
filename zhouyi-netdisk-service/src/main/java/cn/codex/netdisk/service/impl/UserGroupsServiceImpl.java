package cn.codex.netdisk.service.impl;

import cn.codex.netdisk.dao.UserGroupsMapper;
import cn.codex.netdisk.model.entity.UserGroups;
import cn.codex.netdisk.service.IUserGroupsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户组表 服务实现类
 * </p>
 *
 * @author codex
 * @since 2021-01-28
 */
@Service
public class UserGroupsServiceImpl extends ServiceImpl<UserGroupsMapper, UserGroups> implements IUserGroupsService {

}
