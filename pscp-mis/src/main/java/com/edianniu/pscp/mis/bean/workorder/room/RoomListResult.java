package com.edianniu.pscp.mis.bean.workorder.room;

import com.edianniu.pscp.mis.bean.Result;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName: RoomListResult
 * Author: tandingbo
 * CreateTime: 2017-09-15 16:49
 */
public class RoomListResult extends Result implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<RoomInfo> roomList;

    public List<RoomInfo> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<RoomInfo> roomList) {
        this.roomList = roomList;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
