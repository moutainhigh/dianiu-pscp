package com.edianniu.pscp.cs.bean.response.workorder;

import com.edianniu.pscp.cs.bean.response.BaseResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: EvaluateResponse
 * Author: tandingbo
 * CreateTime: 2017-08-08 12:18
 */
@JSONMessage(messageCode = 2002117)
public final class EvaluateResponse extends BaseResponse {

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
