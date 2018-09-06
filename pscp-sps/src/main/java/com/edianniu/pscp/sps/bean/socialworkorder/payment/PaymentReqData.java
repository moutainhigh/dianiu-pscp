package com.edianniu.pscp.sps.bean.socialworkorder.payment;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName: PayPageReqData
 * Author: tandingbo
 * CreateTime: 2017-05-25 18:51
 */
public class PaymentReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 支付类型 WALLET(0, "余额"), ALIPAY(1, "支付宝"), WEIXIN(2, "微信支付"),UNIONPAY(3,"银联支付")
     */
    private Integer payType;
    /**
     * 社会工单ID
     */
    private Long socialWorkOrderId;
    /**
     * 结算数据
     */
    private List<PaymentInfo> paymentInfoList;
    /**
     * 当前登录用户信息
     */
    private Long uid;

    /**
     * ip
     */
    private String ip;
    /**
     * 网站支付返回的url
     */
    private String returnUrl;
    /**
     * 社会工单ID数组
     */
    private List<String> socialWorkOrderIds;

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Long getSocialWorkOrderId() {
        return socialWorkOrderId;
    }

    public void setSocialWorkOrderId(Long socialWorkOrderId) {
        this.socialWorkOrderId = socialWorkOrderId;
    }

    public List<PaymentInfo> getPaymentInfoList() {
        return paymentInfoList;
    }

    public void setPaymentInfoList(List<PaymentInfo> paymentInfoList) {
        this.paymentInfoList = paymentInfoList;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public List<String> getSocialWorkOrderIds() {
        return socialWorkOrderIds;
    }

    public void setSocialWorkOrderIds(List<String> socialWorkOrderIds) {
        this.socialWorkOrderIds = socialWorkOrderIds;
    }
}
