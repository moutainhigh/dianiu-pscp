package com.edianniu.pscp.mis.bean.request.electricianworkorder;

import com.edianniu.pscp.mis.bean.request.TerminalRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: DelWorkLogRequest
 * Author: tandingbo
 * CreateTime: 2017-04-13 17:25
 */
@JSONMessage(messageCode = 1002024)
public final class DelWorkLogRequest extends TerminalRequest {
    /**
     * 订单ID
     */
    private String orderId;
    /**
     * 日志ID
     */
    private Long workLogId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getWorkLogId() {
        return workLogId;
    }

    public void setWorkLogId(Long workLogId) {
        this.workLogId = workLogId;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
