package com.edianniu.pscp.sps.bean.electrician;

/**
 * 电工类型
 * ClassName: ElectricianType
 * Author: tandingbo
 * CreateTime: 2017-04-14 15:48
 */
public enum ElectricianType {
    COMPANY(1),
    SOCIETY(2);

    private final Integer value;

    ElectricianType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
