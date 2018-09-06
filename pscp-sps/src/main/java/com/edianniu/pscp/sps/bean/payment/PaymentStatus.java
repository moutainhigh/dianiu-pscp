package com.edianniu.pscp.sps.bean.payment;

/**
 * ClassName: PaymentStatus
 * Author: tandingbo
 * CreateTime: 2017-06-05 09:59
 */
public enum PaymentStatus {
    UNPAID(0, "UNPAID"),
    CONFIRMING(1, "CONFIRM"),
    SUCCESS(2, "SUCCESS"),
    FAILURE(3, "FAIL"),
    CANCEL(4, "CANCLE"),
    REPEAT(5, "REPEAT");

    private final Integer value;
    private final String name;

    PaymentStatus(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
