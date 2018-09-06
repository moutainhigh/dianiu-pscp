package com.edianniu.pscp.portal.bean.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: WorkOrderListVO
 * Author: tandingbo
 * CreateTime: 2017-10-31 16:23
 */
public class WorkOrderListVO implements Serializable {
    private static final long serialVersionUID = -5386034829625821708L;

    /**
     * 工单ID
     */
    private Long id;
    /**
     * 工单编号
     */
    private String orderId;
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
     * 操作人(服务商)单位
     */
    private String companyName;
    /**
     * 专职电工
     */
    private String enterpriseElectrician;
    /**
     * 社会电工
     */
    private String socialElectrician;
    /**
     * 工单状态
     */
    private Integer status;
    /**
     * 发布日期(yyyy-mm-dd)
     */
    private String publishTime;
    /**
     * 发布日期
     */
    private Date createTime;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 工单地点
     */
    private String address;
    /**
     * 经度
     */
    private String latitude;
    /**
     * 纬度
     */
    private String longitude;
    /**
     * 距离
     */
    private String distance;
    /**
     * 专职电工信息
     */
    private String company;
    /**
     * 招募电工信息
     */
    private String social;
    /**
     * 工单类型
     */
    private Integer type;
    /**
     * 工单类型名称
     */
    private String typeName;

    /**
     * 工单负责人
     */
    private String leaderName;
    /**
     * 派单人
     */
    private String dispatchPersonnel;
    /**
     * 最后修改人
     */
    private String lastAmendment;
    /**
     * 最后修改时间(yyyy-MM-dd HH:mm:ss)
     */
    private String lastModifiedTime;

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

    public String getEnterpriseElectrician() {
        return enterpriseElectrician;
    }

    public void setEnterpriseElectrician(String enterpriseElectrician) {
        this.enterpriseElectrician = enterpriseElectrician;
    }

    public String getSocialElectrician() {
        return socialElectrician;
    }

    public void setSocialElectrician(String socialElectrician) {
        this.socialElectrician = socialElectrician;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSocial() {
        return social;
    }

    public void setSocial(String social) {
        this.social = social;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getDispatchPersonnel() {
        return dispatchPersonnel;
    }

    public void setDispatchPersonnel(String dispatchPersonnel) {
        this.dispatchPersonnel = dispatchPersonnel;
    }

    public String getLastAmendment() {
        return lastAmendment;
    }

    public void setLastAmendment(String lastAmendment) {
        this.lastAmendment = lastAmendment;
    }

    public String getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(String lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
    
}
