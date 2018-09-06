package com.edianniu.pscp.mis.bean.query.electricianworkorder;

import com.edianniu.pscp.mis.commons.BaseQuery;

/**
 * ClassName: ListQuery
 * Author: tandingbo
 * CreateTime: 2017-04-13 17:54
 */
public class ListQuery extends BaseQuery {
    private static final long serialVersionUID = 1L;
    private double latitude;
    private double longitude;
    private Integer[] status;
    private Long uid;
    private String checkOptionId;
    private Integer workOrderLeader;
    private Long companyId;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Integer[] getStatus() {
        return status;
    }

    public void setStatus(Integer[] status) {
        this.status = status;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getCheckOptionId() {
        return checkOptionId;
    }

    public void setCheckOptionId(String checkOptionId) {
        this.checkOptionId = checkOptionId;
    }

    public Integer getWorkOrderLeader() {
        return workOrderLeader;
    }

    public void setWorkOrderLeader(Integer workOrderLeader) {
        this.workOrderLeader = workOrderLeader;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
