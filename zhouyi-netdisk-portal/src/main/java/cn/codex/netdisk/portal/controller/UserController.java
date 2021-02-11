package cn.codex.netdisk.portal.controller;

import cn.codex.netdisk.portal.config.security.component.JwtTokenUtil;
import cn.codex.netdisk.service.IUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

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
@Api(tags = "个人信息管理(UserController)")
public class UserController {

}

