package com.edianniu.pscp.mis.bean.request.electricianworkorder;

import com.edianniu.pscp.mis.bean.request.TerminalRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: EndWorkRequest
 * Author: tandingbo
 * CreateTime: 2017-04-13 17:22
 */
@JSONMessage(messageCode = 1002022)
public final class EndWorkRequest extends TerminalRequest {

    /**
     * 订单ID
     */
    private String orderId;
    /**
     * 结束时间
     * yyyy-MM-dd hh:MM:ss
     * 如果用户是工单负责人，需要填写
     */
    private String endTime;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
