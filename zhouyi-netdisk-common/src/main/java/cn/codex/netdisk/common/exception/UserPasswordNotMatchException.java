package cn.codex.netdisk.common.exception;

import cn.codex.netdisk.common.constants.ReturnMessage;
import cn.codex.netdisk.common.enums.ResponseCode;

/**
 * 用户密码不正确或不符合规范异常类
 *
 * @author codex
 * @since 2021-02-12
 */
public class UserPasswordNotMatchException extends CustomException{
    
    public UserPasswordNotMatchException() {
        super(ResponseCode.ERROR.getCode(), ReturnMessage.USERNAME_PASSWORD_ERROR);
    }
}
