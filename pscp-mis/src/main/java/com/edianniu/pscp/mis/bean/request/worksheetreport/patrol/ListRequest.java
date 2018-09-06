package com.edianniu.pscp.mis.bean.request.worksheetreport.patrol;

import com.edianniu.pscp.mis.bean.request.TerminalRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: ListRequest
 * Author: tandingbo
 * CreateTime: 2017-09-13 10:57
 */
@JSONMessage(messageCode = 1002164)
public final class ListRequest extends TerminalRequest {
    /* 分页.*/
    private int offset;
    /* 工单编号.*/
    private String orderId;
    /* 配电房ID.*/
    private Long roomId;

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

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
