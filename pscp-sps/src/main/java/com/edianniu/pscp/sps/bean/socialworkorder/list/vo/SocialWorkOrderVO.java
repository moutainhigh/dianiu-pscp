package com.edianniu.pscp.sps.bean.socialworkorder.list.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * ClassName: DetailsVO
 * Author: tandingbo
 * CreateTime: 2017-05-18 09:49
 */
public class SocialWorkOrderVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 社会电工招募ID
     */
    private Long id;
    /**
     * 社会工单编号
     */
    private String orderId;
    /**
     * 工单ID
     */
    private Long workOrderId;
    /**
     * 工单名称
     */
    private String name;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 客户(业主)单位名称
     */
    private String customerName;
    /**
     * 费用
     */
    private Double fee;
    /**
     * 人数
     */
    private Integer quantity;
    /**
     * 响应人数
     */
    private String responseNumber;
    /**
     * 招募人数
     */
    private String recruitNumber;
    /**
     * 结算人数
     */
    private String liquidateNumber;
    /**
     * 状态
     */
    private String status;
    /**
     * 发布日期(yyyy-mm-dd)
     */
    private String publishTime;
    /**
     * 发布日期
     */
    private Date createTime;
    /**
     * 截止日期
     */
    private Date closingDate;
    /**
     * 支付时间
     */
    private Date payTime;
    /**
     * 电工资质
     */
    private String qualifications;
    /**
     * 电工资质_名称
     */
    private String certificate;
    /**
     * 标题
     */
    private String title;
    /**
     * 经度
     */
    private String latitude;
    /**
     * 纬度
     */
    private String longitude;
    /**
     * 距离(小数位2位，单位：m, km)
     */
    private String distance;
    /**
     * 工单地点
     */
    private String address;
    /**
     * 招募电工信息
     */
    private Map<String, Object> social;

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

    public Long getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getResponseNumber() {
        return responseNumber;
    }

    public void setResponseNumber(String responseNumber) {
    	this.responseNumber = responseNumber;
    }

    public String getRecruitNumber() {
        return recruitNumber;
    }

    public void setRecruitNumber(String recruitNumber) {
    	this.recruitNumber = recruitNumber;
    }

    public String getLiquidateNumber() {
        return liquidateNumber;
    }

    public void setLiquidateNumber(String liquidateNumber) {
        this.liquidateNumber = liquidateNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Map<String, Object> getSocial() {
        return social;
    }

    public void setSocial(Map<String, Object> social) {
        this.social = social;
    }
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
