package com.edianniu.pscp.mis.bean.worksheetreport;

/**
 * package name: com.edianniu.pscp.mis.bean.worksheetreport
 * project name: pscp-mis
 * user: tandingbo
 * create time: 2017-09-13 11:18
 */
public enum ReportType {
    REPAIR_TEST(1, "修试报告"),
    PATROL(2, "巡视报告"),
    SURVEY(3, "勘察报告");

    ReportType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    private int value;
    private String desc;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
