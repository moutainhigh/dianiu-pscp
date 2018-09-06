package com.edianniu.pscp.sps.bean.payment.balance;

import com.edianniu.pscp.sps.bean.Result;

/**
 * ClassName: FacilitatorBalanceResult
 * Author: tandingbo
 * CreateTime: 2017-06-01 09:50
 */
public class FacilitatorBalanceResult extends Result {
    private static final long serialVersionUID = 1L;

    /**
     * 余额
     */
    private String amount;
    /**
     * 冻结金额
     */
    private String freezingAmount;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFreezingAmount() {
        return freezingAmount;
    }

    public void setFreezingAmount(String freezingAmount) {
        this.freezingAmount = freezingAmount;
    }
}
