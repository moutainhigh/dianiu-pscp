package com.edianniu.pscp.sps.bean.request.customer;

import com.edianniu.pscp.sps.bean.request.TerminalRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: ProjectsRequest
 * Author: tandingbo
 * CreateTime: 2017-07-12 09:47
 */
@JSONMessage(messageCode = 1002092)
public final class ProjectsRequest extends TerminalRequest {

    /* 客户ID. */
    private Long customerId;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
