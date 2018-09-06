package com.edianniu.pscp.mis.bean.response.worksheetreport.survey;

import com.edianniu.pscp.mis.bean.response.BaseResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * @author tandingbo
 * @create 2017-11-09 09:44
 */
@JSONMessage(messageCode = 2002180)
public final class SaveResponse extends BaseResponse {

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
