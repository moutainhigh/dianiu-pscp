package com.edianniu.pscp.sps.bean.request.workOrder;

import com.edianniu.pscp.sps.bean.request.TerminalRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: DetailsRequest
 * Author: tandingbo
 * CreateTime: 2017-06-27 14:30
 */
@JSONMessage(messageCode = 1002095)
public final class DetailsRequest extends TerminalRequest {

    /**
     * 工单编号
     */
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
