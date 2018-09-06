package com.edianniu.pscp.sps.bean.socialworkorder.list;

import com.edianniu.pscp.sps.commons.BaseQuery;

import java.util.Date;

/**
 * ClassName: ListQuery
 * Author: tandingbo
 * CreateTime: 2017-05-18 10:05
 */
public class ListQuery extends BaseQuery {
    private static final long serialVersionUID = 1L;

    private Long memberId;
    private Long companyId;
    private Integer[] status;

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
     * 完成时间_开始时间
     */
    private Date finishStartTime;
    /**
     * 完成时间_结束时间
     */
    private Date finishEndTime;

    /**
     * 经度
     */
    private String latitude;
    /**
     * 纬度
     */
    private String longitude;
    /**
     * 标题
     */
    private String title;

    public Long getMemberId() {
        return memberId;
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

    public Date getFinishStartTime() {
        return finishStartTime;
    }

    public void setFinishStartTime(Date finishStartTime) {
        this.finishStartTime = finishStartTime;
    }

    public Date getFinishEndTime() {
        return finishEndTime;
    }

    public void setFinishEndTime(Date finishEndTime) {
        this.finishEndTime = finishEndTime;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
