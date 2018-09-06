package com.edianniu.pscp.mis.bean.request.workorder;

import com.edianniu.pscp.mis.bean.request.TerminalRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: RoomListRequest
 * Author: tandingbo
 * CreateTime: 2017-09-15 16:51
 */
@JSONMessage(messageCode = 1002174)
public final class RoomListRequest extends TerminalRequest {

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
