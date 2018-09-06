package com.edianniu.pscp.sps.bean.request.socialworkorder;

import com.edianniu.pscp.sps.bean.request.TerminalRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: CertificateRequest
 * Author: tandingbo
 * CreateTime: 2017-07-11 11:15
 */
@JSONMessage(messageCode = 1002109)
public final class CertificateRequest extends TerminalRequest {

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
