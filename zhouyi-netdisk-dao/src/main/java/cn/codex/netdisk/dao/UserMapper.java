package cn.codex.netdisk.dao;

import cn.codex.netdisk.model.entity.User;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author codex
 * @since 2021-01-28
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    
    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    User selectByUsername(String username);
    
    /**
     * 根据用户名或邮箱查询用户信息
     *
     * @param keyword 关键词
     * @return 用户信息
     */
    User selectByUsernameOrEmail(String keyword);
    
    /**
     * 根据邮箱修改用户密码
     *
     * @param password 密码
     * @param email    邮箱
     * @return 影响行数
     */
    int updatePasswordByEmail(@Param("password") String password, @Param("email") String email);
}
