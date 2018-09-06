package com.edianniu.pscp.cs.bean.request.workorder;

import com.edianniu.pscp.cs.bean.request.TerminalRequest;
import com.edianniu.pscp.cs.bean.workorder.EvaluateInfo;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: EvaluateRequest
 * Author: tandingbo
 * CreateTime: 2017-08-08 12:11
 */
@JSONMessage(messageCode = 1002117)
public final class EvaluateRequest extends TerminalRequest {

    /* 工单编号.*/
    private String orderId;
    /* 评价信息.*/
    private EvaluateInfo evaluateInfo;

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

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
