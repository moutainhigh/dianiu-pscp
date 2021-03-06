package com.edianniu.pscp.mis.bean.response.defectrecord;

import com.edianniu.pscp.mis.bean.response.BaseResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: SaveResponse
 * Author: tandingbo
 * CreateTime: 2017-09-12 11:11
 */
@JSONMessage(messageCode = 2002157)
public final class SaveResponse extends BaseResponse {

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
