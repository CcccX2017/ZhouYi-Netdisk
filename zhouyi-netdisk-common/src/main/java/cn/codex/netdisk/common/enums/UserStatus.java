package cn.codex.netdisk.common.enums;

/**
 * 用户状态
 *
 * @author codex
 * @since 2021-02-11
 */
public enum UserStatus {
    /**
     * 启用
     */
    ENABLED(0, "启用"),
    
    /**
     * 停用
     */
    DISABLED(1, "停用");
    
    private Integer code;
    private String desc;
    
    UserStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    public Integer getCode() {
        return code;
    }
    
    public void setCode(Integer code) {
        this.code = code;
    }
    
    public String getDesc() {
        return desc;
    }
    
    public void setDesc(String desc) {
        this.desc = desc;
    }
}
