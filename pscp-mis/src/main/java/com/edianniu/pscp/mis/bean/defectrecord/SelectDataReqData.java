package com.edianniu.pscp.mis.bean.defectrecord;

import java.io.Serializable;

/**
 * ClassName: SelectDataReqData
 * Author: tandingbo
 * CreateTime: 2017-10-18 15:11
 */
public class SelectDataReqData implements Serializable {
    private static final long serialVersionUID = 111254015710424450L;

    private Long uid;
    /**
     * 工单编号
     */
    private String orderId;
    /**
     * 项目ID（未整改的缺陷）
     */
    private Long projectId;
    /**
     * 配电房ID
     */
    private Long roomId;

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
}
