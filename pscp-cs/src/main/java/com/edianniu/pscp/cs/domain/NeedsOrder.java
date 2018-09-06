package com.edianniu.pscp.cs.domain;

import java.util.Date;

/**
 * @author zhoujianjian
 * 2017年9月15日上午10:27:24
 */
public class NeedsOrder extends BaseDo {
    private static final long serialVersionUID = 1L;
    private Long id;
    //需求Id
    private Long needsId;
    //服务商Id
    private Long companyId;
    //订单编号
    private String orderId;
    //状态
    //	-2：不合作    -1：不符合   0：已响应   1：待报价     2：已报价      3：已合作
    private Integer status;
    //支付类型
    //0:无    1:余额      2:支付宝    3:微信支付     4：银联支付
    private Integer payType;
    //支付状态
    //0:未支付     1:支付确认    2:支付成功    3:支付失败    4:取消支付
    private Integer payStatus;
    //支付金额  保留兩位小數
    private String payAmount;
    //发起支付时间
    private Date payTime;
    //成功时间(同步)
    private Date paySyncTime;
    //成功时间(异步)
    private Date payAsyncTime;
    //支付备注
    private String payMemo;
    //报价  保留兩位小數
    private String quotedPrice;
    //报价时间
    private Date quotedTime;
    //合作时间
    private Date cooperationTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNeedsId() {
        return needsId;
    }

    public void setNeedsId(Long needsId) {
        this.needsId = needsId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getPaySyncTime() {
        return paySyncTime;
    }

    public void setPaySyncTime(Date paySyncTime) {
        this.paySyncTime = paySyncTime;
    }

    public Date getPayAsyncTime() {
        return payAsyncTime;
    }

    public void setPayAsyncTime(Date payAsyncTime) {
        this.payAsyncTime = payAsyncTime;
    }

    public String getPayMemo() {
        return payMemo;
    }

    public void setPayMemo(String payMemo) {
        this.payMemo = payMemo;
    }

    public String getQuotedPrice() {
        return quotedPrice;
    }

    public void setQuotedPrice(String quotedPrice) {
        this.quotedPrice = quotedPrice;
    }

    public Date getQuotedTime() {
        return quotedTime;
    }

    public void setQuotedTime(Date quotedTime) {
        this.quotedTime = quotedTime;
    }

    public Date getCooperationTime() {
        return cooperationTime;
    }

    public void setCooperationTime(Date cooperationTime) {
        this.cooperationTime = cooperationTime;
    }

}
