package com.edianniu.pscp.cs.domain;

import java.io.Serializable;
import java.util.Date;


/**
 * ${comments}
 *
 * @author wangcheng.li
 * @email wangcheng.li@edianniu.com
 * @date 2017-09-21 15:52:07
 */
public class CustomerNeedsOrder extends BaseDo implements Serializable {
    private static final long serialVersionUID = 1L;

    //$column.comments
    private Long id;
    //$column.comments
    private Long needsId;
    //$column.comments
    private Long companyId;
    //$column.comments
    private String orderId;
    //$column.comments
    private Integer status;
    //$column.comments
    private Integer payType;
    //$column.comments
    private Integer payStatus;
    //$column.comments
    private Double payAmount;
    //$column.comments
    private Date payTime;
    //$column.comments
    private Date paySyncTime;
    //$column.comments
    private Date payAsyncTime;
    //$column.comments
    private String payMemo;
    //$column.comments
    private Date accordTime;
    //$column.comments
    private Double quotedPrice;
    //$column.comments
    private Date quotedTime;
    //$column.comments
    private Date cooperationTime;
    /* 保证金金额.*/
    private Double amount;

    /**
     * 设置：${column.comments}
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：${column.comments}
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：${column.comments}
     */
    public void setNeedsId(Long needsId) {
        this.needsId = needsId;
    }

    /**
     * 获取：${column.comments}
     */
    public Long getNeedsId() {
        return needsId;
    }

    /**
     * 设置：${column.comments}
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    /**
     * 获取：${column.comments}
     */
    public Long getCompanyId() {
        return companyId;
    }

    /**
     * 设置：${column.comments}
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取：${column.comments}
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 设置：${column.comments}
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取：${column.comments}
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置：${column.comments}
     */
    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    /**
     * 获取：${column.comments}
     */
    public Integer getPayType() {
        return payType;
    }

    /**
     * 设置：${column.comments}
     */
    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    /**
     * 获取：${column.comments}
     */
    public Integer getPayStatus() {
        return payStatus;
    }

    /**
     * 设置：${column.comments}
     */
    public void setPayAmount(Double payAmount) {
        this.payAmount = payAmount;
    }

    /**
     * 获取：${column.comments}
     */
    public Double getPayAmount() {
        return payAmount;
    }

    /**
     * 设置：${column.comments}
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    /**
     * 获取：${column.comments}
     */
    public Date getPayTime() {
        return payTime;
    }

    /**
     * 设置：${column.comments}
     */
    public void setPaySyncTime(Date paySyncTime) {
        this.paySyncTime = paySyncTime;
    }

    /**
     * 获取：${column.comments}
     */
    public Date getPaySyncTime() {
        return paySyncTime;
    }

    /**
     * 设置：${column.comments}
     */
    public void setPayAsyncTime(Date payAsyncTime) {
        this.payAsyncTime = payAsyncTime;
    }

    /**
     * 获取：${column.comments}
     */
    public Date getPayAsyncTime() {
        return payAsyncTime;
    }

    /**
     * 设置：${column.comments}
     */
    public void setPayMemo(String payMemo) {
        this.payMemo = payMemo;
    }

    /**
     * 获取：${column.comments}
     */
    public String getPayMemo() {
        return payMemo;
    }

    /**
     * 设置：${column.comments}
     */
    public void setAccordTime(Date accordTime) {
        this.accordTime = accordTime;
    }

    /**
     * 获取：${column.comments}
     */
    public Date getAccordTime() {
        return accordTime;
    }

    /**
     * 设置：${column.comments}
     */
    public void setQuotedPrice(Double quotedPrice) {
        this.quotedPrice = quotedPrice;
    }

    /**
     * 获取：${column.comments}
     */
    public Double getQuotedPrice() {
        return quotedPrice;
    }

    /**
     * 设置：${column.comments}
     */
    public void setQuotedTime(Date quotedTime) {
        this.quotedTime = quotedTime;
    }

    /**
     * 获取：${column.comments}
     */
    public Date getQuotedTime() {
        return quotedTime;
    }

    /**
     * 设置：${column.comments}
     */
    public void setCooperationTime(Date cooperationTime) {
        this.cooperationTime = cooperationTime;
    }

    /**
     * 获取：${column.comments}
     */
    public Date getCooperationTime() {
        return cooperationTime;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
