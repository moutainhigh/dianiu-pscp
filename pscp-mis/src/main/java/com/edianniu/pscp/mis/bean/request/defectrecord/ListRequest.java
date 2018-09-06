package com.edianniu.pscp.mis.bean.request.defectrecord;

import com.edianniu.pscp.mis.bean.request.TerminalRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: ListRequest
 * Author: tandingbo
 * CreateTime: 2017-09-12 11:07
 */
@JSONMessage(messageCode = 1002155)
public final class ListRequest extends TerminalRequest {
    /* 分页.*/
    private int offset;

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

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
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

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
