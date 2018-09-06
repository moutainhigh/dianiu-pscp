package com.edianniu.pscp.sps.bean.response.needsorder;

import com.edianniu.pscp.sps.bean.response.BaseResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: OfferResponse
 * Author: tandingbo
 * CreateTime: 2017-09-26 14:10
 */
@JSONMessage(messageCode = 2002172)
public final class OfferResponse extends BaseResponse {

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
