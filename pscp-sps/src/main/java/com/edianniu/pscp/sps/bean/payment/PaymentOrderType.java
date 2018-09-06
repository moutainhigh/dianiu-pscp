package com.edianniu.pscp.sps.bean.payment;

/**
 * ClassName: PaymentOrderType
 * Author: tandingbo
 * CreateTime: 2017-07-25 10:34
 */
public enum PaymentOrderType {
    RECHARGE(1, "充值"),
    SOCIAL_WORK_ORDER_PAY(2, "工单支付"),
    SOCIAL_ELECTRICIAN_WORK_ORDER_SETTLEMENT(3, "工单结算支付");

    private final Integer value;
    private final String name;

    PaymentOrderType(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static String getNameByValue(Integer value) {
        PaymentOrderType[] paymentOrderTypes = PaymentOrderType.values();
        for (PaymentOrderType paymentOrderType : paymentOrderTypes) {
            if (paymentOrderType.value.equals(value)) {
                return paymentOrderType.name;
            }
        }
        return "未知订单类型";
    }

}
