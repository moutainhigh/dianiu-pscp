package com.edianniu.pscp.sps.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-23 17:22:14
 */
public class MemberPayOrder extends BaseDo implements Serializable {
    private static final long serialVersionUID = 1L;

    //$column.comments
    private Long id;
    //$column.comments
    private Long uid;
    //$column.comments
    private Integer orderType;
    //$column.comments
    private String orderId;
    //$column.comments
    private String associatedOrderIds;
    //$column.comments
    private String title;
    //$column.comments
    private String body;
    //$column.comments
    private Double amount;
    //$column.comments
    private Integer payType;
    //$column.comments
    private String payMethod;
    //$column.comments
    private Date payTime;
    //$column.comments
    private Date paySyncTime;
    //$column.comments
    private Date payAsyncTime;
    //$column.comments
    private Integer status;
    //$column.comments
    private String memo;
    //$column.comments
    private Date createTime;
    //$column.comments
    private String createUser;
    //$column.comments
    private Date modifiedTime;
    //$column.comments
    private String modifiedUser;
    private String extendParams;

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
    public void setUid(Long uid) {
        this.uid = uid;
    }

    /**
     * 获取：${column.comments}
     */
    public Long getUid() {
        return uid;
    }

    /**
     * 设置：${column.comments}
     */
    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    /**
     * 获取：${column.comments}
     */
    public Integer getOrderType() {
        return orderType;
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

    public String getAssociatedOrderIds() {
        return associatedOrderIds;
    }

    public void setAssociatedOrderIds(String associatedOrderIds) {
        this.associatedOrderIds = associatedOrderIds;
    }

    /**
     * 设置：${column.comments}
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取：${column.comments}
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置：${column.comments}
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * 获取：${column.comments}
     */
    public String getBody() {
        return body;
    }

    /**
     * 设置：${column.comments}
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * 获取：${column.comments}
     */
    public Double getAmount() {
        return amount;
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
    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    /**
     * 获取：${column.comments}
     */
    public String getPayMethod() {
        return payMethod;
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
    public void setMemo(String memo) {
        this.memo = memo;
    }

    /**
     * 获取：${column.comments}
     */
    public String getMemo() {
        return memo;
    }

    /**
     * 设置：${column.comments}
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取：${column.comments}
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置：${column.comments}
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 获取：${column.comments}
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 设置：${column.comments}
     */
    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    /**
     * 获取：${column.comments}
     */
    public Date getModifiedTime() {
        return modifiedTime;
    }

    /**
     * 设置：${column.comments}
     */
    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    /**
     * 获取：${column.comments}
     */
    public String getModifiedUser() {
        return modifiedUser;
    }

    public String getExtendParams() {
        return extendParams;
    }

    public void setExtendParams(String extendParams) {
        this.extendParams = extendParams;
    }
}
