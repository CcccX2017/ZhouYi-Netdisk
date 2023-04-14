package cn.codex.netdisk.common.exception;

import cn.codex.netdisk.common.enums.ResponseCode;

/**
 * 错误异常
 *
 * @author CodeX
 * @since 2021-03-04 09:43:16
 */
public class ErrorException extends CustomException {
    
    private static final long serialVersionUID = 7641692213832210385L;
    
    public ErrorException(String message) {
        super(ResponseCode.ERROR.getCode(), message);
    }
}