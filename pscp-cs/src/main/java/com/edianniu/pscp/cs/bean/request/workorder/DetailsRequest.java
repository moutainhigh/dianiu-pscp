package com.edianniu.pscp.cs.bean.request.workorder;

import com.edianniu.pscp.cs.bean.request.TerminalRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: DetailsRequest
 * Author: tandingbo
 * CreateTime: 2017-08-08 11:42
 */
@JSONMessage(messageCode = 1002116)
public final class DetailsRequest extends TerminalRequest {

    /* 工单编号.*/
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
