package cn.codex.netdisk.portal.controller;


import cn.codex.netdisk.common.constants.Const;
import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.common.utils.RedisUtil;
import cn.codex.netdisk.model.entity.Menu;
import cn.codex.netdisk.service.IMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  菜单
 * </p>
 *
 * @author codex
 * @since 2021-03-11
 */
@RestController
@RequestMapping("/portal/menu")
@Api(tags = "菜单")
public class MenuController {
    
    @Autowired
    private IMenuService menuService;
    
    @Autowired
    private RedisUtil redisUtil;
    
    @ApiOperation("获取菜单列表")
    @GetMapping("/")
    public ServerResponse<List<Menu>> getMenuList(){
        List<Menu> menus = redisUtil.getObject(Const.MENU_PREFIX);
        if (menus == null || menus.size() <= 0) {
            menus = menuService.getMenuList();
            redisUtil.setObject(Const.MENU_PREFIX, menus);
            return ServerResponse.createBySuccess(menus);
        }
        
        return ServerResponse.createBySuccess(menus);
    }
}

