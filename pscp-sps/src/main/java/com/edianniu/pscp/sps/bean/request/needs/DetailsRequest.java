package com.edianniu.pscp.sps.bean.request.needs;

import com.edianniu.pscp.sps.bean.request.TerminalRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: DetailsRequest
 * Author: tandingbo
 * CreateTime: 2017-09-25 16:48
 */
@JSONMessage(messageCode = 1002169)
public final class DetailsRequest extends TerminalRequest {

    /* 需求编号.*/
    private String orderId;
    /* 服务商响应订单编号.*/
    private String responsedOrderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getResponsedOrderId() {
        return responsedOrderId;
    }

    public void setResponsedOrderId(String responsedOrderId) {
        this.responsedOrderId = responsedOrderId;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
