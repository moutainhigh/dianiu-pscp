package com.edianniu.pscp.sps.bean.workorder.electrician;

import org.apache.commons.lang3.StringUtils;

/**
 * package name: com.edianniu.pscp.portal.bean.workorder
 * project name: pscp-portal
 * user: tandingbo
 * create time: 2017-05-10 16:48
 */
public enum ElectricianWorkOrderStatus {
    CANCELED(-1, "已取消"),
    FAIL(-2, "不符合"),
    UNCONFIRMED(0, "未确认"),
    CONFIRMED(1, "已确认"),
    EXECUTING(2, "进行中"),
    FINISHED(3, "已结束"),
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

    public static String getNameByKey(String valueStr) {
        if (StringUtils.isBlank(valueStr)) {
            return "";
        }
        Integer value = Integer.valueOf(valueStr);
        ElectricianWorkOrderStatus[] values = ElectricianWorkOrderStatus.values();
        for (ElectricianWorkOrderStatus electricianWorkOrderStatus : values) {
            if (value.equals(electricianWorkOrderStatus.getValue())) {
                return electricianWorkOrderStatus.getName();
            }
        }
        return "";
    }
}
