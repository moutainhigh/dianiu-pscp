package com.edianniu.pscp.sps.bean.request.workOrder;

import com.edianniu.pscp.sps.bean.request.TerminalRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: ElectricianRequest
 * Author: tandingbo
 * CreateTime: 2017-06-29 10:38
 */
@JSONMessage(messageCode = 1002100)
public final class ElectricianRequest extends TerminalRequest {

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
