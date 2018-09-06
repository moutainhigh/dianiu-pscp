package com.edianniu.pscp.sps.bean.payment;

import java.io.Serializable;

/**
 * ClassName: PaymentReqData
 * Author: tandingbo
 * CreateTime: 2017-07-25 10:03
 */
public class PaymentReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 登录用户
     */
    private Long uid;
    /**
     * 支付类型
     * WALLET(0, "余额")
     * ALIPAY(1, "支付宝")
     * WEIXIN(2, "微信支付")
     * UNIONPAY(3,"银联支付")
     */
    private Integer payType;
    /**
     * 订单类型
     * RECHARGE(1, "充值")
     * SOCIAL_WORK_ORDER_PAY(2, "社会工单支付")
     * SOCIAL_ELECTRICAN_WORK_ORDER_SETTLEMENT(3, "社会电工工单结算");
     */
    private Integer orderType;
    /**
     * orderType=1 充值订单编号，可以留空，只能一个
     * orderType=2 社会工单编号，不能为空，支持多个，逗号隔开
     * orderType=3 社会电工工单编号，不能为空，支持多个，逗号隔开
     */
    private String orderIds;
    /**
     * 调用者ip app或者pc客户端ip需上传(微信支付要用到)
     */
    private String ip;
    /**
     * 网站支付返回的url 可以自定义同步返回url
     * （目前只有支付宝和银联支付，余额和微信支付暂时没有用）
     */
    private String returnUrl;
    /**
     * 扩展参数（最大256个字符，可以为空）
     */
    private String extendParams;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(String orderIds) {
        this.orderIds = orderIds;
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

    public String getExtendParams() {
        return extendParams;
    }

    public void setExtendParams(String extendParams) {
        this.extendParams = extendParams;
    }
}
