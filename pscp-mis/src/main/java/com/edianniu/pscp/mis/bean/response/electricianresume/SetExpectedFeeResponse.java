package com.edianniu.pscp.mis.bean.response.electricianresume;

import com.edianniu.pscp.mis.bean.response.BaseResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: SetExpectedFeeResponse
 * Author: tandingbo
 * CreateTime: 2017-04-19 17:21
 */
@JSONMessage(messageCode = 2002053)
public final class SetExpectedFeeResponse extends BaseResponse {

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
