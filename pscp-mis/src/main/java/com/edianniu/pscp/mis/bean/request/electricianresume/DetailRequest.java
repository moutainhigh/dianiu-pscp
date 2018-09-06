package com.edianniu.pscp.mis.bean.request.electricianresume;

import com.edianniu.pscp.mis.bean.request.TerminalRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: DetailRequest
 * Author: tandingbo
 * CreateTime: 2017-04-19 16:20
 */
@JSONMessage(messageCode = 1002051)
public final class DetailRequest extends TerminalRequest {

    private Long electricianId;

    public Long getElectricianId() {
        return electricianId;
    }

    public void setElectricianId(Long electricianId) {
        this.electricianId = electricianId;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
