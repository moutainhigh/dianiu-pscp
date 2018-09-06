package com.edianniu.pscp.mis.bean.request.worksheetreport.repairtest;

import com.edianniu.pscp.mis.bean.request.TerminalRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: DetailsRequest
 * Author: tandingbo
 * CreateTime: 2017-09-13 10:57
 */
@JSONMessage(messageCode = 1002161)
public final class DetailsRequest extends TerminalRequest {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
