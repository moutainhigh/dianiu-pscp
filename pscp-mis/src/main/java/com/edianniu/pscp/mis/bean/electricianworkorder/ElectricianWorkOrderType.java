package com.edianniu.pscp.mis.bean.electricianworkorder;

/**
 * package name: com.edianniu.pscp.mis.bean.workorder
 * project name: pscp-mis
 * user: tandingbo
 * create time: 2017-04-13 15:34
 */
public enum ElectricianWorkOrderType {
    COMPANY_ELECTRICIAN_WD(1, "企业电工工单"),
    SOCIAL_ELECTRICIAN_WD(2, "社会电工工单");
   

    private final Integer value;
    private final String name;

    ElectricianWorkOrderType(Integer value, String name) {
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
