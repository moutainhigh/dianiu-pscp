package com.edianniu.pscp.sps.bean.socialworkorder.payment;

import com.edianniu.pscp.sps.bean.Result;

/**
 * ClassName: PaymentResult
 * Author: tandingbo
 * CreateTime: 2017-05-25 18:51
 */
public class PaymentResult extends Result {
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
    /**
     * 第三方支付页面地址
     */
    private String redirectUrl;
    /**
     * 支付成功后跳转地址
     */
    private String originUrl;
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

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
    }

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
}
