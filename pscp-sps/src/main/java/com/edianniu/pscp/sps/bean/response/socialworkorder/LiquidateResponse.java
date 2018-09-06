package com.edianniu.pscp.sps.bean.response.socialworkorder;

import com.edianniu.pscp.sps.bean.response.BaseResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: LiquidateResponse
 * Author: tandingbo
 * CreateTime: 2017-06-30 14:42
 */
@JSONMessage(messageCode = 2002107)
public final class LiquidateResponse extends BaseResponse {

    /**
     * 冻结金额支付状态：
     * success:支付成功
     * failure:支付失败
     */
    private String status;
    /**
     * 冻结金额
     */
    private String freezingAmount;
    /**
     * 支付金额
     */
    private String paymentAmount;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFreezingAmount() {
        return freezingAmount;
    }

    public void setFreezingAmount(String freezingAmount) {
        this.freezingAmount = freezingAmount;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
