package com.edianniu.pscp.sps.bean.payment;

import com.edianniu.pscp.sps.bean.Result;

/**
 * ClassName: PaymentResult
 * Author: tandingbo
 * CreateTime: 2017-07-25 10:02
 */
public class PaymentResult extends Result {
    private static final long serialVersionUID = 1L;

    /**
     * 支付金额
     */
    private String amount;
    /**
     * 支付类型
     * WALLET(0, "余额")
     * ALIPAY(1, "支付宝")
     * WEIXIN(2, "微信支付")
     * UNIONPAY(3,"银联支付")
     */
    private Integer payType;
    /**
     * 支付成功后跳转地址
     */
    private String originUrl;
    /**
     * 支付订单ID
     */
    private String orderId;
    /**
     * 订单类型
     */
    private String orderType;
    /**
     * 第三方支付信息
     */
    private String thirdPartyPaymentInfo;
    /**
     * 第三方支付页面地址
     */
    private String redirectUrl;
    /**
     * 冻结金额支付状态：
     * success:支付成功
     * failure:支付失败
     */
    private String status;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
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

    public String getThirdPartyPaymentInfo() {
        return thirdPartyPaymentInfo;
    }

    public void setThirdPartyPaymentInfo(String thirdPartyPaymentInfo) {
        this.thirdPartyPaymentInfo = thirdPartyPaymentInfo;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
