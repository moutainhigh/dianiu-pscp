package com.edianniu.pscp.mis.bean.response.electricianworkorder;

import com.edianniu.pscp.mis.bean.response.BaseResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: EndWorkResponse
 * Author: tandingbo
 * CreateTime: 2017-04-13 17:23
 */
@JSONMessage(messageCode = 2002022)
public final class EndWorkResponse extends BaseResponse {

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
