package cn.codex.netdisk.common.exception;

import cn.codex.netdisk.common.enums.ResponseCode;

/**
 * 验证码验证异常
 *
 * @author codex
 * @since 2021-02-12
 */
public class CaptchaException extends CustomException {
    private static final long serialVersionUID = 1L;
    
    public CaptchaException(String message) {
        super(ResponseCode.ERROR.getCode(), message);
    }
}
