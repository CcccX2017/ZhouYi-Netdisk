package cn.codex.netdisk.service;

import cn.codex.netdisk.model.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author codex
 * @since 2021-03-11
 */
public interface IMenuService extends IService<Menu> {
    
    /**
     * 获取菜单列表
     *
     * @return 菜单列表
     */
    List<Menu> getMenuList();
}
