package cn.codex.netdisk.common.enums;

/**
 * @author CodeX
 * @since 2020-11-03 22:32:19
 */
public enum ResponseCode {
    /**
     * 成功
     */
    SUCCESS(200, "SUCCESS"),

    /**
     * 错误
     */
    ERROR(500, "ERROR"),
    
    /**
     * 404 资源，服务未找到
     */
    NOT_FOUND(404, "资源，服务未找到"),

    /**
     * 10 用户未登录，请登录
     */
    NEED_LOGIN(10, "尚未登录，请登录"),

    /**
     * 2 参数错误
     */
    ILLEGAL_ARGUMENT(2, "参数错误"),
    
    /**
     * 401 未授权
     */
    UNAUTHORIZED(401, "未授权"),
    
    /**
     * 403 权限不足，请联系管理员
     */
    FORBIDDEN(403, "权限不足，请联系管理员"),
    
    FOLDER_NAME_REPEAT(20, "此目录下已存在同名文件，是否要保存两个文件"),

    FILE_SIZE_OUT_OF_LIMIT(30, "上传的文件大小超限");

    private final int code;
    private final String desc;

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 获取代号
     *
     * @return 代号
     */
    public int getCode() {
        return code;
    }

    /**
     * 获取描述信息
     *
     * @return 描述信息
     */
    public String getDesc() {
        return desc;
    }
}
