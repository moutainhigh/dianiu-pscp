package com.edianniu.pscp.mis.bean.response.defectrecord;

import com.edianniu.pscp.mis.bean.response.BaseResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: CorrectionResponse
 * Author: tandingbo
 * CreateTime: 2017-09-12 11:14
 */
@JSONMessage(messageCode = 2002158)
public final class CorrectionResponse extends BaseResponse {

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
