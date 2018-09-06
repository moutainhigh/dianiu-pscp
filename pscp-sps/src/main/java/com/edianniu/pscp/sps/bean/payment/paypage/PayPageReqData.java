package com.edianniu.pscp.sps.bean.payment.paypage;

import java.io.Serializable;

/**
 * ClassName: PayPageReqData
 * Author: tandingbo
 * CreateTime: 2017-06-01 15:56
 */
public class PayPageReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 支付类型 WALLET(0, "余额"), ALIPAY(1, "支付宝"), WEIXIN(2, "微信支付"),UNIONPAY(3,"银联支付")
     */
    private Integer payType;
    /**
     * 第三方支付信息
     */
    private String thirdPartyPaymentInfo;
    /**
     * 支付订单ID
     */
    private String orderId;
    /**
     * 订单类型
     */
    private String orderType;
    /**
     * 支付金额
     */
    private String amount;

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getThirdPartyPaymentInfo() {
        return thirdPartyPaymentInfo;
    }

    public void setThirdPartyPaymentInfo(String thirdPartyPaymentInfo) {
        this.thirdPartyPaymentInfo = thirdPartyPaymentInfo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
