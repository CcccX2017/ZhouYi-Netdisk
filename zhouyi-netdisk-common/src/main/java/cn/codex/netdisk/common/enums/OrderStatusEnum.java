package cn.codex.netdisk.common.enums;

/**
 * @author CodeX
 * @since 2020-11-03 22:47:48
 */
public enum OrderStatusEnum {
    /**
     * 已取消
     */
    CANCELED(0, "已取消"),

    /**
     * 未支付
     */
    NO_PAY(10, "未支付"),

    /**
     * 已付款
     */
    PAID(20, "已付款"),

    /**
     * 订单完成
     */
    ORDER_SUCCESS(40, "订单完成"),

    /**
     * 订单关闭
     */
    ORDER_CLOSE(50, "订单关闭");

    OrderStatusEnum(int code, String value) {
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

    public static OrderStatusEnum codeOf(int code) {
        for (OrderStatusEnum orderStatusEnum : values()) {
            if (orderStatusEnum.getCode() == code) {
                return orderStatusEnum;
            }
        }
        throw new RuntimeException("没有找到对应的枚举");
    }
}
