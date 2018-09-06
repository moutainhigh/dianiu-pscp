package com.edianniu.pscp.sps.bean.response.socialworkorder;

import com.edianniu.pscp.sps.bean.response.BaseResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

import java.util.List;

/**
 * ClassName: SaveResponse
 * Author: tandingbo
 * CreateTime: 2017-06-29 14:16
 */
@JSONMessage(messageCode = 2002103)
public final class SaveResponse extends BaseResponse {
    /**
     * 社会工单ID
     */
    private List<Long> socialWorkOrderIds;
    /**
     * 社会工单编号（多个以,隔开）
     */
    private String socialOrderIdStr;

    public List<Long> getSocialWorkOrderIds() {
        return socialWorkOrderIds;
    }

    public void setSocialWorkOrderIds(List<Long> socialWorkOrderIds) {
        this.socialWorkOrderIds = socialWorkOrderIds;
    }

    public String getSocialOrderIdStr() {
        return socialOrderIdStr;
    }

    public void setSocialOrderIdStr(String socialOrderIdStr) {
        this.socialOrderIdStr = socialOrderIdStr;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
