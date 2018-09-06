package com.edianniu.pscp.sps.bean.response.needs;

import com.edianniu.pscp.cs.bean.needs.NeedsOrderInfo;
import com.edianniu.pscp.cs.bean.needs.vo.NeedsVO;
import com.edianniu.pscp.sps.bean.response.BaseResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: DetailsResponse
 * Author: tandingbo
 * CreateTime: 2017-09-25 16:50
 */
@JSONMessage(messageCode = 2002169)
public final class DetailsResponse extends BaseResponse {

    /* 需求信息.*/
    private NeedsVO needsInfo;
    /* 响应订单信息.*/
    private NeedsOrderInfo needsOrderInfo;

    public NeedsVO getNeedsInfo() {
        return needsInfo;
    }

    public void setNeedsInfo(NeedsVO needsInfo) {
        this.needsInfo = needsInfo;
    }

    public NeedsOrderInfo getNeedsOrderInfo() {
        return needsOrderInfo;
    }

    public void setNeedsOrderInfo(NeedsOrderInfo needsOrderInfo) {
        this.needsOrderInfo = needsOrderInfo;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
