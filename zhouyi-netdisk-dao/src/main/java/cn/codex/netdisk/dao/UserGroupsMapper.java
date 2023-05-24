package cn.codex.netdisk.dao;

import cn.codex.netdisk.model.entity.UserGroups;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户组表 Mapper 接口
 * </p>
 *
 * @author codex
 * @since 2021-01-28
 */
@Mapper
public interface UserGroupsMapper extends BaseMapper<UserGroups> {

}
