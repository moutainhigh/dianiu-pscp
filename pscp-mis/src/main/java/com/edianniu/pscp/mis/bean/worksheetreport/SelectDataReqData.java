package com.edianniu.pscp.mis.bean.worksheetreport;

import java.io.Serializable;

/**
 * ClassName: SelectDataReqData
 * Author: tandingbo
 * CreateTime: 2017-10-18 14:47
 */
public class SelectDataReqData implements Serializable {
    private static final long serialVersionUID = 3898539839736343528L;

    private Long uid;
    private int type;
    private Long roomId;
    private String orderId;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
