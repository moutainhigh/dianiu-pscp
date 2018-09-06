package com.edianniu.pscp.mis.bean.query;

import com.edianniu.pscp.mis.commons.BaseQuery;

import java.util.List;

/**
 * ClassName: DefectRecordQuery
 * Author: tandingbo
 * CreateTime: 2017-09-12 11:41
 */
public class DefectRecordQuery extends BaseQuery {
    private static final long serialVersionUID = 1L;

    private Long uid;
    /**
     * 工单ID
     */
    private Long workOrderId;
    /**
     * 项目ID（未整改的缺陷）
     */
    private Long projectId;
    /**
     * 配电房ID
     */
    private Long roomId;
    /**
     * 状态(0:未整改,1:已整改)
     */
    private Integer status;
    /**
     * 配电房IDs
     */
    private List<Long> roomIds;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
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

    public List<Long> getRoomIds() {
        return roomIds;
    }

    public void setRoomIds(List<Long> roomIds) {
        this.roomIds = roomIds;
    }
}
