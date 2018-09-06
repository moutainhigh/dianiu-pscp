package com.edianniu.pscp.sps.bean.request.needsorder;

import com.edianniu.pscp.sps.bean.request.TerminalRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: CancelRequest
 * Author: tandingbo
 * CreateTime: 2017-09-26 11:29
 */
@JSONMessage(messageCode = 1002175)
public final class CancelRequest extends TerminalRequest {

    /* 需求响应编号.*/
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
