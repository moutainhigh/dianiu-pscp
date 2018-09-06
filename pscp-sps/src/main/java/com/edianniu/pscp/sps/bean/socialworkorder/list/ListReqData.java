package com.edianniu.pscp.sps.bean.socialworkorder.list;

import com.edianniu.pscp.sps.commons.Constants;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: ListReqData
 * Author: tandingbo
 * CreateTime: 2017-05-18 09:53
 */
public class ListReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer pageSize = Constants.DEFAULT_PAGE_SIZE;
    private int offset;

    private Long memberId;
    private Long companyId;
    private Integer[] status;
    private Date startTime;
    private Date endTime;
    private Date finishStartTime;
    private Date finishEndTime;
    private String name;
    private String title;
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

    public Integer[] getStatus() {
        return status;
    }

    public void setStatus(Integer[] status) {
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

    public ListQueryRequestInfo getListQueryRequestInfo() {
        return listQueryRequestInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setListQueryRequestInfo(ListQueryRequestInfo listQueryRequestInfo) {
        this.listQueryRequestInfo = listQueryRequestInfo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
