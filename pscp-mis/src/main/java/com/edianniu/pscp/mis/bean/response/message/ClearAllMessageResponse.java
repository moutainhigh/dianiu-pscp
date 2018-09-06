package com.edianniu.pscp.mis.bean.response.message;

import com.edianniu.pscp.mis.bean.response.BaseResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: ClearAllMessageResponse
 * Author: tandingbo
 * CreateTime: 2017-04-17 16:26
 */
@JSONMessage(messageCode = 2002042)
public final class ClearAllMessageResponse extends BaseResponse {

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
