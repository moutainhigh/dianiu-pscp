package com.edianniu.pscp.mis.bean.electricianworkorder;

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
    /**
     * 工单结算支付状态(0:结算未支付,1:结算已支付)
     */
    private Integer settlementPayStatus;

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

    public Integer getSettlementPayStatus() {
        return settlementPayStatus;
    }

    public void setSettlementPayStatus(Integer settlementPayStatus) {
        this.settlementPayStatus = settlementPayStatus;
    }
}
