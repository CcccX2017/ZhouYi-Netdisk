package cn.codex.netdisk.service.impl;

import cn.codex.netdisk.common.constants.Const;
import cn.codex.netdisk.common.dtos.LoginDto;
import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.common.enums.ResponseCode;
import cn.codex.netdisk.common.utils.RedisUtil;
import cn.codex.netdisk.dao.UserMapper;
import cn.codex.netdisk.model.entity.User;
import cn.codex.netdisk.service.IUserService;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author codex
 * @since 2021-01-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserMapper userMapper;

    /**
     * 用户登录
     *
     * @param loginDto 用户登录对象
     * @return 登录结果
     */
    @Override
    public ServerResponse login(LoginDto loginDto) {
        if (loginDto == null) {
            return ServerResponse.createByErrorMessage(ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        String code = redisUtil.getObject(Const.CAPTCHA_KEY + loginDto.getUuid());
        if (!loginDto.getCode().equalsIgnoreCase(code)) {
            return ServerResponse.createByErrorMessage(Const.CAPTCHA_ERROR);
        }

        User user = userMapper.selectByUsername(loginDto.getUsername());

        if (user == null || !BCrypt.checkpw(loginDto.getPassword(), user.getPassword())) {
            return ServerResponse.createByErrorMessage(Const.USERNAME_PASSWORD_ERROR);
        }

        user.setPassword("");
        user.setSalt("");
        redisUtil.setObject(Const.LOGINUSER_KEY + user.getUsername(), user);

        return ServerResponse.createBySuccess(user);
    }
}
