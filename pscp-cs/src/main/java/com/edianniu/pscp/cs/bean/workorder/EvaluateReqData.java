package com.edianniu.pscp.cs.bean.workorder;

import java.io.Serializable;

/**
 * ClassName: EvaluateReqData
 * Author: tandingbo
 * CreateTime: 2017-08-08 12:19
 */
public class EvaluateReqData implements Serializable {
	private static final long serialVersionUID = 1L;
	/* uid.*/
    private Long uid;
    /* 工单编号.*/
    private String orderId;
    /* 评价信息.*/
    private EvaluateInfo evaluateInfo;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public EvaluateInfo getEvaluateInfo() {
        return evaluateInfo;
    }

    public void setEvaluateInfo(EvaluateInfo evaluateInfo) {
        this.evaluateInfo = evaluateInfo;
    }
}
