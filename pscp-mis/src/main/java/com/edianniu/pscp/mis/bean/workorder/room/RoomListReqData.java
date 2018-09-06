package com.edianniu.pscp.mis.bean.workorder.room;

import java.io.Serializable;

/**
 * ClassName: RoomListReqData
 * Author: tandingbo
 * CreateTime: 2017-09-15 16:48
 */
public class RoomListReqData implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long uid;
    private String orderId;

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
}
