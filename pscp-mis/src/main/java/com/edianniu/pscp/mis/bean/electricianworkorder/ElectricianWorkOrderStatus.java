package com.edianniu.pscp.mis.bean.electricianworkorder;

/**
 * package name: com.edianniu.pscp.mis.bean.workorder
 * project name: pscp-mis
 * user: tandingbo
 * create time: 2017-04-13 15:34
 */
public enum ElectricianWorkOrderStatus {
    CANCELED(-1, "已取消"),
    FAIL(-2, "不符合"),
    UNCONFIRMED(0, "未确认"),
    CONFIRMED(1, "已确认"),
    EXECUTING(2, "进行中"),
    FINISHED(3, "已完成"),
    FEE_CONFIRM(4, "费用确认"),
    LIQUIDATED(5, "已结算");

    private final Integer value;
    private final String name;

    ElectricianWorkOrderStatus(Integer value, String name) {
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
