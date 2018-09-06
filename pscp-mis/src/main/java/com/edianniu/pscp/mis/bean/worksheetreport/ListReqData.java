package com.edianniu.pscp.mis.bean.worksheetreport;

import java.io.Serializable;

/**
 * ClassName: ListReqData
 * Author: tandingbo
 * CreateTime: 2017-09-13 10:54
 */
public class ListReqData implements Serializable {
    private static final long serialVersionUID = -5148373919420759191L;

    private Long uid;
    private String orderId;
    private Long roomId;

    private int type;
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

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
