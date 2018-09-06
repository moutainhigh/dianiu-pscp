package com.edianniu.pscp.sps.bean.workorder.chieforder;

import com.edianniu.pscp.sps.commons.Constants;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: ListReqData
 * Author: tandingbo
 * CreateTime: 2017-05-12 16:43
 */
public class ListReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer pageSize = Constants.DEFAULT_PAGE_SIZE;
    private int offset;

    private Long memberId; //操作者memberId--->指服务商
    private Long companyId;//操作者companyId--->指服务商
    private String status; //主查询状态
    private Date startTime;
    private Date endTime;
    private Long uid;
    private String name;
    
    private String subStatus;//子查询状态
    private String companyName;//操作者companyName--->指服务商

    /**
     * 经度
     */
    private String latitude;
    /**
     * 纬度
     */
    private String longitude;

    private ListQueryRequestInfo listQueryRequestInfo;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public ListQueryRequestInfo getListQueryRequestInfo() {
        return listQueryRequestInfo;
    }

    public void setListQueryRequestInfo(ListQueryRequestInfo listQueryRequestInfo) {
        this.listQueryRequestInfo = listQueryRequestInfo;
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

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
