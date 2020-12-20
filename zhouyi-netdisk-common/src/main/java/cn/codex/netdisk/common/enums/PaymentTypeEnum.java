package cn.codex.netdisk.common.enums;

/**
 * @author CodeX
 * @since 2020-11-03 22:51:11
 */
public enum PaymentTypeEnum {
    /**
     * 在线支付
     */
    ONLINE_PAY(1, "在线支付");

    PaymentTypeEnum(int code, String value) {
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

    public static PaymentTypeEnum codeOf(int code) {
        for (PaymentTypeEnum paymentTypeEnum : values()) {
            if (paymentTypeEnum.getCode() == code) {
                return paymentTypeEnum;
            }
        }
        throw new RuntimeException("没有找到对应的枚举");
    }
}
