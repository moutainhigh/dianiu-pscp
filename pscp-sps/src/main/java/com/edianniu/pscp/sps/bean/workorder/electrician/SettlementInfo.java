package com.edianniu.pscp.sps.bean.workorder.electrician;

import java.io.Serializable;

/**
 * ClassName: SettlementInfo
 * Author: tandingbo
 * CreateTime: 2017-05-12 10:12
 */
public class SettlementInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 工作时间
     */
    private String workTime;
    /**
     * 结算金额
     */
    private String amount;

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
