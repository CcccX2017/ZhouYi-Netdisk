package cn.codex.netdisk.common.enums;

/**
 * @author CodeX
 * @since 2020-11-03 22:50:10
 */
public enum PayPlatformEnum {
    /**
     * 支付宝
     */
    ALIPAY(1, "支付宝"),

    /**
     * 微信
     */
    WXPAY(2, "微信");

    PayPlatformEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    private final String value;
    private final int code;

    public String getValue() {
        return value;
    }

    public int getCode() {
        return code;
    }
}
