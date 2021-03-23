package cn.codex.netdisk.portal.controller;

import cn.codex.netdisk.common.constants.Const;
import cn.codex.netdisk.common.constants.ReturnMessage;
import cn.codex.netdisk.common.dtos.LoginUser;
import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.common.utils.FileUtil;
import cn.codex.netdisk.common.utils.JwtTokenUtil;
import cn.codex.netdisk.common.utils.RegexUtil;
import cn.codex.netdisk.common.utils.SecurityUtil;
import cn.codex.netdisk.model.entity.User;
import cn.codex.netdisk.model.vo.OrderVo;
import cn.codex.netdisk.model.vo.UserVo;
import cn.codex.netdisk.portal.component.CaptchaMail;
import cn.codex.netdisk.portal.dtos.UpdateUserInfoDto;
import cn.codex.netdisk.service.IOrderService;
import cn.codex.netdisk.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private CaptchaMail captchaMail;
    
    @Autowired
    private IOrderService orderService;

    @ApiOperation(value = "获取登录用户信息")
    @GetMapping("/")
    public ServerResponse<UserVo> getLoginUserInfo(HttpServletRequest request) {
        return userService.getLoginUserInfo(request);
    }

    @ApiOperation("更新个人信息")
    @PutMapping("/")
    public ServerResponse update(@RequestBody UpdateUserInfoDto updateUserInfoDto) {
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
        if (update) {
            // redis中的用户信息
            LoginUser loginUser = SecurityUtil.getLoginUser();
            loginUser.setUser(userService.selectUserByUsername(loginUser.getUsername()));
            jwtTokenUtil.updateLoginUser(loginUser);
            return ServerResponse.createBySuccess(ReturnMessage.UPDATE_SUCCESS);
        }
        return ServerResponse.createByErrorMessage(ReturnMessage.UPDATE_ERROR);
    }

    @ApiOperation("修改密码")
    @PostMapping("/")
    public ServerResponse updatePwd(String oldPassword, String newPassword, String code, String uuid) {
        return userService.updatePwd(oldPassword, newPassword, code, uuid);
    }

    @ApiOperation("发送修改密码的邮件验证码")
    @GetMapping("/sendCode")
    public ServerResponse sendCode() {
        LoginUser loginUser = SecurityUtil.getLoginUser();
        String email = loginUser.getUser().getEmail();
        if (Strings.isNullOrEmpty(email)) {
            return ServerResponse.createByErrorMessage("账号未绑定邮箱，请先绑定");
        }

        return captchaMail.sendCaptchaMail(email, "修改密码", Const.UPDATE_KEY);
    }
    
    @ApiOperation("获取订单信息")
    @GetMapping("/order")
    public ServerResponse getOrder(){
        List<OrderVo> orderVos = orderService.getOrder();
        return ServerResponse.createBySuccess(orderVos);
    }
    
    @ApiOperation("获取空间使用情况")
    @GetMapping("/storage")
    public ServerResponse getStorageInfo(){
        User user = userService.selectUserByUsername(SecurityUtil.getUsername());
        Long usedStorageSpace = user.getUsedStorageSpace();
        Long maxStorageSpace = user.getUserGroups().getMaxStorageSpace();
        Map<String, Object> map = Maps.newHashMap();
        if (usedStorageSpace.equals(0L)){
            map.put("usedStorageSpace", "0");
        }else{
            map.put("usedStorageSpaceStr", FileUtil.unreservedDecimalPoint(usedStorageSpace));
        }
        map.put("maxStorageSpace", FileUtil.unreservedDecimalPoint(maxStorageSpace));
        map.put("percent", usedStorageSpace / maxStorageSpace * 100);
        
        return ServerResponse.createBySuccess(map);
    }
}

