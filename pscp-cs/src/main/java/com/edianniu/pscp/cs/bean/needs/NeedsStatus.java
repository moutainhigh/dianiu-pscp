package com.edianniu.pscp.cs.bean.needs;

/**
 * 需求状态
 *
 * @author zhoujianjian
 * 2017年9月25日下午11:03:20
 */
public enum NeedsStatus {

    OVERTIME(-3, "已超时"),
    CANCELED(-2, "已取消"),
    FAIL_AUDIT(-1, "审核不通过"),
    AUDITING(0, "审核中"),
    RESPONDING(1, "响应中（审核通过）"),
    QUOTING(2, "报价中"),
    QUOTED(3, "已报价"),
    COOPETATED(4, "已合作");

    private Integer value;
    private String desc;

    NeedsStatus(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


}
