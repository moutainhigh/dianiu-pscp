package com.edianniu.pscp.mis.bean.query;

import com.edianniu.pscp.mis.commons.BaseQuery;

import java.util.List;

/**
 * ClassName: WorkOrderReportQuery
 * Author: tandingbo
 * CreateTime: 2017-09-13 10:56
 */
public class WorkOrderReportQuery extends BaseQuery {
    private static final long serialVersionUID = 1L;

    private int type;
    private Long workOrderId;
    private Long roomId;
    private List<Long> roomIds;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public List<Long> getRoomIds() {
        return roomIds;
    }

    public void setRoomIds(List<Long> roomIds) {
        this.roomIds = roomIds;
    }
}
