package com.edianniu.pscp.sps.bean.request.socialworkorder;

import com.edianniu.pscp.sps.bean.request.TerminalRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: ElectricianRequest
 * Author: tandingbo
 * CreateTime: 2017-06-29 15:41
 */
@JSONMessage(messageCode = 1002104)
public final class ElectricianRequest extends TerminalRequest {

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
