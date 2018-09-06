package com.edianniu.pscp.mis.bean.defectrecord;

/**
 * 工单缺陷解决状态
 * user: tandingbo
 * create time: 2017-09-12 15:41
 */
public enum CorrectionStatus {
    UNRESOLVED(0, "未解决"), RESOLVED(1, "已解决");

    CorrectionStatus(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    private int value;
    private String desc;

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
