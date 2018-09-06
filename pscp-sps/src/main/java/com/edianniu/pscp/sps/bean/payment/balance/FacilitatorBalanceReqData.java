package com.edianniu.pscp.sps.bean.payment.balance;

import java.io.Serializable;

/**
 * ClassName: FacilitatorBalanceReqData
 * Author: tandingbo
 * CreateTime: 2017-06-01 09:54
 */
public class FacilitatorBalanceReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 登录用户ID
     */
    private Long uid;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }
}
