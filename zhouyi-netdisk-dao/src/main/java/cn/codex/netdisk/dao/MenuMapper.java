package cn.codex.netdisk.dao;

import cn.codex.netdisk.model.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author codex
 * @since 2021-03-11
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    
    /**
     * 获取菜单列表
     *
     * @return 菜单列表
     */
    List<Menu> getMenuList();
}
