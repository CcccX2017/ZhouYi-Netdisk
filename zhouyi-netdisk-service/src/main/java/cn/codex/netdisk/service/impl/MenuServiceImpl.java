package cn.codex.netdisk.service.impl;

import cn.codex.netdisk.model.entity.Menu;
import cn.codex.netdisk.dao.MenuMapper;
import cn.codex.netdisk.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author codex
 * @since 2021-03-11
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
    
    @Autowired
    private MenuMapper menuMapper;
    
    /**
     * 获取菜单列表
     *
     * @return 菜单列表
     */
    @Override
    public List<Menu> getMenuList() {
        List<Menu> menuList = menuMapper.getMenuList();
        List<Menu> menus = Lists.newArrayList();
        menuList.forEach(menu -> {
        
        });
        return menuList;
    }
}
