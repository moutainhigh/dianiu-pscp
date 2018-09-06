package com.edianniu.pscp.mis.bean.response.electricianresume;

import com.edianniu.pscp.mis.bean.response.BaseResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: UpdateResponse
 * Author: tandingbo
 * CreateTime: 2017-04-19 17:12
 */
@JSONMessage(messageCode = 2002052)
public final class UpdateResponse extends BaseResponse {

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
