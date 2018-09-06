package com.edianniu.pscp.cs.bean.needsorder.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: NeedsOrderVO
 * Author: tandingbo
 * CreateTime: 2017-09-21 17:06
 */
public class NeedsOrderVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long needsId;
    private Long companyId;
    private String orderId;
    private Double amount;
    private Integer status;
    private Integer payType;
    private Integer payStatus;
    private Double payAmount;
    private Date payTime;
    private Date paySyncTime;
    private Date payAsyncTime;
    private String payMemo;
    private Date accordTime;
    private Double quotedPrice;
    private Date quotedTime;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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

    public Double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Double payAmount) {
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

    public Date getAccordTime() {
        return accordTime;
    }

    public void setAccordTime(Date accordTime) {
        this.accordTime = accordTime;
    }

    public Double getQuotedPrice() {
        return quotedPrice;
    }

    public void setQuotedPrice(Double quotedPrice) {
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
