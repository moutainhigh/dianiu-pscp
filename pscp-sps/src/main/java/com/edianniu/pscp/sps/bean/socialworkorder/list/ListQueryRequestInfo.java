package com.edianniu.pscp.sps.bean.socialworkorder.list;

import java.io.Serializable;

/**
 * ClassName: ListQueryRequestInfo
 * Author: tandingbo
 * CreateTime: 2017-05-18 09:51
 */
public class ListQueryRequestInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 状态
     */
    private String status;
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
     * 完成时间_开始时间
     */
    private String finishStartTime;
    /**
     * 完成时间_结束时间
     */
    private String finishEndTime;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getFinishStartTime() {
        return finishStartTime;
    }

    public void setFinishStartTime(String finishStartTime) {
        this.finishStartTime = finishStartTime;
    }

    public String getFinishEndTime() {
        return finishEndTime;
    }

    public void setFinishEndTime(String finishEndTime) {
        this.finishEndTime = finishEndTime;
    }
}
