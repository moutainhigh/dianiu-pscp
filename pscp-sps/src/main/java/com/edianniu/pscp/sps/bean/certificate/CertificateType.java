package com.edianniu.pscp.sps.bean.certificate;

/**
 * ClassName: CertificateType
 * Author: tandingbo
 * CreateTime: 2017-07-21 11:29
 */
public enum CertificateType {
    SPECIAL_OPERATION(1, "特种作业操作证"),
    POWER_GRID_OPERATION(2, "电工进网作业许可证"),
    VOCATIONAL_QUALIFICATION(3, "职业资格证书");

    private final Integer value;
    private final String name;

    CertificateType(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static String getNameByKey(Integer value) {
        if (value == null) {
            return "";
        }
        CertificateType[] values = CertificateType.values();
        for (CertificateType certificateType : values) {
            if (value.equals(certificateType.getValue())) {
                return certificateType.getName();
            }
        }
        return "";
    }
}
