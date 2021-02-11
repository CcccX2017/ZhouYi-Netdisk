package cn.codex.netdisk.common.exception;

/**
 * 自定义异常
 *
 * @author codex
 * @since 2021-02-11
 */
public class CustomException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    private Integer exceptionCode;
    
    private final String message;
    
    public CustomException(String message) {
        this.message = message;
    }
    
    public CustomException(Integer exceptionCode, String message) {
        this.exceptionCode = exceptionCode;
        this.message = message;
    }
    
    public CustomException(Throwable cause, String message) {
        super(message, cause);
        this.message = message;
    }
    
    public Integer getExceptionCode() {
        return exceptionCode;
    }
    
    @Override
    public String getMessage() {
        return message;
    }
}
