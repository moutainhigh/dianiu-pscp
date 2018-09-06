package com.edianniu.pscp.mis.bean.electricianworkorder;

import java.io.Serializable;

/**
 * ClassName: ListQueryResultInfo
 * Author: tandingbo
 * CreateTime: 2017-04-13 09:44
 */
public class ListReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long uid;
    private String status;
    private String latitude;
    private String longitude;
    private Integer offset;
    private String type;
    private Long companyId;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
