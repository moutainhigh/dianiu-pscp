package com.edianniu.pscp.sps.bean.workorder.chieforder;

import java.io.Serializable;

/**
 * ClassName: ListQueryRequestInfo
 * Author: tandingbo
 * CreateTime: 2017-05-12 16:59
 */
public class ListQueryRequestInfo implements Serializable {
    private static final long serialVersionUID = 1L;

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
    private String startTime;
    /**
     * 发布时间_结束时间
     */
    private String endTime;
    /**
     * 状态
     */
    private String status;
    
    /**
     * 查询子状态 ，客户查询时使用 
     */
    private String subStatus;
    
    /**
     * 操作公司--->服务商
     */
    private String companyName;
    
    private String jumpPage;

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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	public String getJumpPage() {
		return jumpPage;
	}

	public void setJumpPage(String jumpPage) {
		this.jumpPage = jumpPage;
	}

	public String getSubStatus() {
		return subStatus;
	}

	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

    
}
