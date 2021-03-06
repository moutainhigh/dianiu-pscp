package com.edianniu.pscp.sps.bean.response.needsorder;

import com.edianniu.pscp.sps.bean.response.BaseResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: CancelResponse
 * Author: tandingbo
 * CreateTime: 2017-09-26 11:30
 */
@JSONMessage(messageCode = 2002175)
public final class CancelResponse extends BaseResponse {

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
