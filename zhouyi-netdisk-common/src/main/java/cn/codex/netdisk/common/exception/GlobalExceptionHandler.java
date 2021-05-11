package cn.codex.netdisk.common.exception;

import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.common.enums.ResponseCode;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 全局异常处理
 *
 * @author codex
 * @since 2021-02-12
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    public static final Log log = LogFactory.get();
    
    /**
     * 自定义基础异常
     */
    @ExceptionHandler(CustomException.class)
    public ServerResponse<String> baseException(CustomException e) {
        log.error(e.getMessage());
        return ServerResponse.createByErrorCodeMessage(e.getExceptionCode(), e.getMessage());
    }
    
    @ExceptionHandler(NoHandlerFoundException.class)
    public ServerResponse<String> handlerNoFoundException(Exception e) {
        log.error(e.getMessage(), e);
        return ServerResponse.createByErrorCodeMessage(ResponseCode.NOT_FOUND.getCode(), "路径不存在，请检查路径是否正确");
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    public ServerResponse<String> handleAuthorizationException(AccessDeniedException e) {
        log.error(e.getMessage());
        return ServerResponse.createByErrorCodeMessage(ResponseCode.FORBIDDEN.getCode(), ResponseCode.FORBIDDEN.getDesc());
    }
    
    @ExceptionHandler(AccountExpiredException.class)
    public ServerResponse<String> handleAccountExpiredException(AccountExpiredException e) {
        log.error(e.getMessage(), e);
        return ServerResponse.createByErrorMessage(e.getMessage());
    }
    
    @ExceptionHandler(UsernameNotFoundException.class)
    public ServerResponse<String> handleUsernameNotFoundException(UsernameNotFoundException e) {
        log.error(e.getMessage(), e);
        return ServerResponse.createByErrorMessage(e.getMessage());
    }
    
    @ExceptionHandler(Exception.class)
    public ServerResponse<String> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ServerResponse.createByErrorMessage(e.getMessage());
    }
    
}
