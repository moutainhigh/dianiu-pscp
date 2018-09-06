package com.edianniu.pscp.mis.bean.request.electricianworkorder;

import com.edianniu.pscp.mis.bean.request.TerminalRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: DelWorkLogAttachmentRequest
 * Author: tandingbo
 * CreateTime: 2017-04-13 17:27
 */
@JSONMessage(messageCode = 1002025)
public final class DelWorkLogAttachmentRequest extends TerminalRequest {
    /**
     * 订单ID
     */
    private String orderId;
    /**
     * 附件ID
     */
    private Long workLogAttachmentId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getWorkLogAttachmentId() {
        return workLogAttachmentId;
    }

    public void setWorkLogAttachmentId(Long workLogAttachmentId) {
        this.workLogAttachmentId = workLogAttachmentId;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
