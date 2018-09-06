package com.edianniu.pscp.sps.bean.workorder.chieforder;

import com.edianniu.pscp.sps.commons.BaseQuery;

import java.util.Date;

/**
 * ClassName: ListQuery
 * Author: tandingbo
 * CreateTime: 2017-05-12 16:42
 */
public class ListQuery extends BaseQuery {
    private static final long serialVersionUID = 1L;

    private Long memberId;//服务商，操作人uid
    private Long companyId;//服务商
    private Integer[] status;
    private Integer needSocialElectrician;
    private Integer subStatus;//子状态，客户查询时使用
    private String companyName;//服务商
    private Long customerUid;//客户uid
    
    public Long getCustomerUid() {
		return customerUid;
	}

	public void setCustomerUid(Long customerUid) {
		this.customerUid = customerUid;
	}

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
     * 发布时间_开始时间
     */
    private Date startTime;
    /**
     * 发布时间_结束时间
     */
    private Date endTime;

    /**
     * 经度
     */
    private String latitude;
    /**
     * 纬度
     */
    private String longitude;

    public Long getMemberId() {
        return memberId;
    }

    public Integer getSubStatus() {
		return subStatus;
	}

	public void setSubStatus(Integer subStatus) {
		this.subStatus = subStatus;
	}

	public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Integer[] getStatus() {
        return status;
    }

    public void setStatus(Integer[] status) {
        this.status = status;
    }

    public Integer getNeedSocialElectrician() {
        return needSocialElectrician;
    }

    public void setNeedSocialElectrician(Integer needSocialElectrician) {
        this.needSocialElectrician = needSocialElectrician;
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
    
}
