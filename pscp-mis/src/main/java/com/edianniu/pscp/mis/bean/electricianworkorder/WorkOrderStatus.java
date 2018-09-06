package com.edianniu.pscp.mis.bean.electricianworkorder;

/**
 * package name: com.edianniu.pscp.mis.bean.workorder
 * project name: pscp-mis
 * user: tandingbo
 * create time: 2017-04-13 15:34
 */
public enum WorkOrderStatus {
    CANCELED(-1, "已取消"),
    UNCONFIM(0, "未确认"),
    CONFIM(1, "已确认"),
    EXECUTING(2, "进行中"),
    PENDING_EVALUATION(3, "待评价"),
    EVALUATED(4, "已评价");

    private final Integer value;
    private final String name;

    WorkOrderStatus(Integer value, String name) {
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
