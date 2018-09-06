package com.edianniu.pscp.mis.bean.request.message;

import com.edianniu.pscp.mis.bean.request.TerminalRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: ClearAllMessageRequest
 * Author: tandingbo
 * CreateTime: 2017-04-17 16:27
 */
@JSONMessage(messageCode = 1002042)
public final class ClearAllMessageRequest extends TerminalRequest {

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
