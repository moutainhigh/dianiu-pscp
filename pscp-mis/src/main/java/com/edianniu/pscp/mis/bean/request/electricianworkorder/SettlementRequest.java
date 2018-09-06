package com.edianniu.pscp.mis.bean.request.electricianworkorder;

import com.edianniu.pscp.mis.bean.request.TerminalRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: SettlementRequest
 * Author: tandingbo
 * CreateTime: 2017-05-10 15:53
 */
@JSONMessage(messageCode = 1002056)
public final class SettlementRequest extends TerminalRequest {
    /**
     * 支付金额
     */
    private String amount;
    /**
     * 订单ID
     */
    private String orderId;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

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
