package com.edianniu.pscp.sps.bean.request.workOrder;

import com.edianniu.pscp.sps.bean.request.TerminalRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: TooleQuipmentsRequest
 * Author: tandingbo
 * CreateTime: 2017-08-04 09:41
 */
@JSONMessage(messageCode = 1002113)
public final class TooleQuipmentsRequest extends TerminalRequest {

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
