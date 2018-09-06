package com.edianniu.pscp.mis.bean.request.electricianworkorder;

import com.edianniu.pscp.mis.bean.request.TerminalRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: StartWorkRequest
 * Author: tandingbo
 * CreateTime: 2017-04-13 17:20
 */
@JSONMessage(messageCode = 1002021)
public final class StartWorkRequest extends TerminalRequest {

    /**
     * 订单ID
     */
    private String orderId;
    /**
     * 开始时间
     * yyyy-MM-dd hh:MM:ss
     * 如果用户是工单负责人，需要填写
     */
    private String startTime;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
