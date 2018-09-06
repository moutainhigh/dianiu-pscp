package com.edianniu.pscp.sps.bean.response.needsorder;

import com.edianniu.pscp.sps.bean.response.BaseResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: SaveResponse
 * Author: tandingbo
 * CreateTime: 2017-09-26 10:57
 */
@JSONMessage(messageCode = 2002171)
public final class SaveResponse extends BaseResponse {

    /* 服务商响应订单编号.*/
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
