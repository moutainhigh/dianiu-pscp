package com.edianniu.pscp.cs.bean.request.company;

import com.edianniu.pscp.cs.bean.request.TerminalRequest;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: BindingFacilitatorRequest
 * Author: tandingbo
 * CreateTime: 2017-10-30 15:05
 */
@JSONMessage(messageCode = 1002177)
public final class BindingFacilitatorRequest extends TerminalRequest {

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
