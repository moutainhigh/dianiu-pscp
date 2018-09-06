package com.edianniu.pscp.sps.bean.socialworkorder.details.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: DetailsVO Author: tandingbo CreateTime: 2017-05-21 17:37
 */
public class DetailsVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 社会工单ID
     */
    private Long id;
    /**
     * 社会工单编号
     */
    private String orderId;
    /**
     * 社会工单标题
     */
    private String title;
    /**
     * 费用
     */
    private String fee;
    /**
     * 招募总费用
     */
    private String totalFee;
    /**
     * 电工资质
     */
    private String qualifications;
    /**
     * 人数
     */
    private Integer quantity;
    /**
     * 电工资质_名称
     */
    private String certificate;
    /**
     * 招募人数
     */
    private String recruitNumber;
    /**
     * 响应人数
     */
    private String responseNumber;
    /**
     * 未确认人数
     */
    private String unconfirmed;
    /**
     * 招募开始时间
     */
    private Date startTime;
    /**
     * 招募结束时间
     */
    private Date endTime;
    /**
     * 发布时间
     */
    private String publishTime;
    /**
     * 招募截止时间
     */
    private String expiryTime;
    /**
     * 工作时间
     */
    private String workTime;
    /**
     * 需求描述
     */
    private String content;
    /**
     * 状态：
     * -1：取消
     * 0：未发布
     * 1：招募中
     * 2：招募结束
     * 3：已完成
     * 4：已结算
     */
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getRecruitNumber() {
        return recruitNumber;
    }

    public void setRecruitNumber(String recruitNumber) {
        this.recruitNumber = recruitNumber;
    }

    public String getResponseNumber() {
        return responseNumber;
    }

    public void setResponseNumber(String responseNumber) {
        this.responseNumber = responseNumber;
    }

    public String getUnconfirmed() {
        return unconfirmed;
    }

    public void setUnconfirmed(String unconfirmed) {
        this.unconfirmed = unconfirmed;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(String expiryTime) {
        this.expiryTime = expiryTime;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.DEFAULT_STYLE);
    }
}
