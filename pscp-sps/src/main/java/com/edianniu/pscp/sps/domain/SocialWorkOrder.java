package com.edianniu.pscp.sps.domain;

import java.io.Serializable;
import java.util.Date;


/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-18 11:03:23
 */
public class SocialWorkOrder extends BaseDo implements Serializable {
    private static final long serialVersionUID = 1L;

    //$column.comments
    private Long id;
    //$column.comments
    private Long workOrderId;
    //$column.comments
    private String orderId;
    //$column.comments
    private Date expiryTime;
    //$column.comments
    private Date beginWorkTime;
    //$column.comments
    private Date endWorkTime;
    //$column.comments
    private String qualifications;
    private String title;
    //$column.comments
    private String content;
    //$column.comments
    private Double totalFee;
    //$column.comments
    private Integer quantity;
    //$column.comments
    private Integer unit;
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
    private Date paySynctime;
    //$column.comments
    private Date payAsynctime;
    //$column.comments
    private String payMemo;
    //$column.comments
    private Long companyId;
    //$column.comments
    private Date createTime;
    //$column.comments
    private String createUser;
    //$column.comments
    private Date modifiedTime;
    //$column.comments
    private String modifiedUser;
    //$column.comments
    private Double fee;

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
    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
    }

    /**
     * 获取：${column.comments}
     */
    public Long getWorkOrderId() {
        return workOrderId;
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
    public void setExpiryTime(Date expiryTime) {
        this.expiryTime = expiryTime;
    }

    /**
     * 获取：${column.comments}
     */
    public Date getExpiryTime() {
        return expiryTime;
    }

    /**
     * 设置：${column.comments}
     */
    public void setBeginWorkTime(Date beginWorkTime) {
        this.beginWorkTime = beginWorkTime;
    }

    /**
     * 获取：${column.comments}
     */
    public Date getBeginWorkTime() {
        return beginWorkTime;
    }

    /**
     * 设置：${column.comments}
     */
    public void setEndWorkTime(Date endWorkTime) {
        this.endWorkTime = endWorkTime;
    }

    /**
     * 获取：${column.comments}
     */
    public Date getEndWorkTime() {
        return endWorkTime;
    }

    /**
     * 设置：${column.comments}
     */
    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    /**
     * 获取：${column.comments}
     */
    public String getQualifications() {
        return qualifications;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 设置：${column.comments}
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取：${column.comments}
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置：${column.comments}
     */
    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
    }

    /**
     * 获取：${column.comments}
     */
    public Double getTotalFee() {
        return totalFee;
    }

    /**
     * 设置：${column.comments}
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * 获取：${column.comments}
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * 设置：${column.comments}
     */
    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    /**
     * 获取：${column.comments}
     */
    public Integer getUnit() {
        return unit;
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
    public void setPaySynctime(Date paySynctime) {
        this.paySynctime = paySynctime;
    }

    /**
     * 获取：${column.comments}
     */
    public Date getPaySynctime() {
        return paySynctime;
    }

    /**
     * 设置：${column.comments}
     */
    public void setPayAsynctime(Date payAsynctime) {
        this.payAsynctime = payAsynctime;
    }

    /**
     * 获取：${column.comments}
     */
    public Date getPayAsynctime() {
        return payAsynctime;
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

    /**
     * 设置：${column.comments}
     */
    public void setFee(Double fee) {
        this.fee = fee;
    }

    /**
     * 获取：${column.comments}
     */
    public Double getFee() {
        return fee;
    }
}
