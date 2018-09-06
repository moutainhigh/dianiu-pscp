package com.edianniu.pscp.mis.bean.defectrecord;

import java.io.Serializable;

/**
 * ClassName: ListReqData
 * Author: tandingbo
 * CreateTime: 2017-09-12 11:18
 */
public class ListReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long uid;
    /* 工单编号.*/
    private String orderId;
    /* 项目ID（未整改的缺陷）.*/
    private Long projectId;
    /* 配电房ID.*/
    private Long roomId;
    /**
     * 状态(0:未整改,1:已整改)
     */
    private Integer status;

    private int offset;
    private int pageSize;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
