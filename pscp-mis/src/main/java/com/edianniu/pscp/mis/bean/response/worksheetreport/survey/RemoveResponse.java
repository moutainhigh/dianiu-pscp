package com.edianniu.pscp.mis.bean.response.worksheetreport.survey;

import com.edianniu.pscp.mis.bean.response.BaseResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * @author tandingbo
 * @create 2017-11-08 17:19
 */
@JSONMessage(messageCode = 2002181)
public final class RemoveResponse extends BaseResponse {

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
