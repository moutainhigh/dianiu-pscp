package com.edianniu.pscp.sps.bean.response.socialworkorder;

import com.edianniu.pscp.sps.bean.response.BaseResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: ConfirmResponse
 * Author: tandingbo
 * CreateTime: 2017-06-29 16:39
 */
@JSONMessage(messageCode = 2002105)
public final class ConfirmResponse extends BaseResponse {

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
