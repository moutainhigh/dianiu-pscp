package com.edianniu.pscp.mis.bean.response.worksheetreport.patrol;

import com.edianniu.pscp.mis.bean.response.BaseResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: RemoveResponse
 * Author: tandingbo
 * CreateTime: 2017-09-13 11:04
 */
@JSONMessage(messageCode = 2002167)
public final class RemoveResponse extends BaseResponse {

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
