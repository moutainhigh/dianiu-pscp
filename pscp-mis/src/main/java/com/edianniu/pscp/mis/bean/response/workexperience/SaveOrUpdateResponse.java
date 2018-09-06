package com.edianniu.pscp.mis.bean.response.workexperience;

import com.edianniu.pscp.mis.bean.response.BaseResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: SaveOrUpdateResponse
 * Author: tandingbo
 * CreateTime: 2017-04-19 15:30
 */
@JSONMessage(messageCode = 2002054)
public final class SaveOrUpdateResponse extends BaseResponse {
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
