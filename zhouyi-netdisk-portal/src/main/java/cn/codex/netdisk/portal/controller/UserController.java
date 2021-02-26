package cn.codex.netdisk.portal.controller;

import cn.codex.netdisk.common.constants.Const;
import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.model.entity.User;
import cn.codex.netdisk.model.vo.UserVo;
import cn.codex.netdisk.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author codex
 * @since 2021-01-28
 */
@RestController
@RequestMapping("/portal/user")
@Api(tags = "个人信息管理")
public class UserController {
    
    @Autowired
    private IUserService userService;

    @ApiOperation(value = "获取登录用户信息")
    @GetMapping("/")
    public ServerResponse<UserVo> getLoginUserInfo(HttpServletRequest request){
        return userService.getLoginUserInfo(request);
    }

    @ApiOperation("更新个人信息")
    @PutMapping("/")
    public ServerResponse<String> update(@RequestBody User user){
        user.setUsername(null);
        user.setPassword(null);
        user.setSalt(null);
        return userService.updateById(user)
                ? ServerResponse.createBySuccessMessage(Const.UPDATE_SUCCESS)
                : ServerResponse.createByErrorMessage(Const.UPDATE_ERROR);
    }
}

