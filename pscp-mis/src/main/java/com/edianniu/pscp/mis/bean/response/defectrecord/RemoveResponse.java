package com.edianniu.pscp.mis.bean.response.defectrecord;

import com.edianniu.pscp.mis.bean.response.BaseResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: RemoveResponse
 * Author: tandingbo
 * CreateTime: 2017-09-13 09:57
 */
@JSONMessage(messageCode = 2002159)
public final class RemoveResponse extends BaseResponse {

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}