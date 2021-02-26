package cn.codex.netdisk.portal.controller;

import cn.codex.netdisk.common.constants.ReturnMessage;
import cn.codex.netdisk.common.dtos.LoginUser;
import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.common.utils.JwtTokenUtil;
import cn.codex.netdisk.common.utils.RegexUtil;
import cn.codex.netdisk.model.entity.User;
import cn.codex.netdisk.model.vo.UserVo;
import cn.codex.netdisk.portal.dtos.UpdateUserInfoDto;
import cn.codex.netdisk.portal.utils.SecurityUtil;
import cn.codex.netdisk.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
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

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @ApiOperation(value = "获取登录用户信息")
    @GetMapping("/")
    public ServerResponse<UserVo> getLoginUserInfo(HttpServletRequest request) {
        return userService.getLoginUserInfo(request);
    }

    @ApiOperation("更新个人信息")
    @PutMapping("/")
    public ServerResponse<String> update(@RequestBody UpdateUserInfoDto updateUserInfoDto) {
        if (Strings.isNullOrEmpty(updateUserInfoDto.getNickname())) {
            return ServerResponse.createByErrorMessage(ReturnMessage.NICKNAME_EMPTY);
        }
        // 验证昵称是否合法
        if (!RegexUtil.isNicknameLegal(updateUserInfoDto.getNickname())) {
            return ServerResponse.createByErrorMessage(ReturnMessage.NICKNAME_ILLEGAL);
        }
        // 验证昵称是否已被使用
        int count =
                userService.count(new QueryWrapper<User>().eq(User.NICKNAME, updateUserInfoDto.getNickname()).ne(User.USER_ID, updateUserInfoDto.getUserId()));
        if (count > 0) {
            return ServerResponse.createByErrorMessage(ReturnMessage.NICKNAME_EXIST);
        }

        User user = new User();
        BeanUtils.copyProperties(updateUserInfoDto, user);

        boolean update = userService.updateById(user);
        if (update){
            // 更新security和redis中的用户信息
            LoginUser loginUser = SecurityUtil.getLoginUser();
            loginUser.setUser(userService.selectUserByUsername(loginUser.getUsername()));
            jwtTokenUtil.updateLoginUser(loginUser);
            System.out.println(loginUser);
            return ServerResponse.createBySuccess(ReturnMessage.UPDATE_SUCCESS);
        }
        return ServerResponse.createByErrorMessage(ReturnMessage.UPDATE_ERROR);
    }
}

