package com.edianniu.pscp.mis.bean.response.workorder;

import com.edianniu.pscp.mis.bean.response.BaseResponse;
import com.edianniu.pscp.mis.bean.workorder.room.RoomInfo;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

import java.util.List;

/**
 * ClassName: RoomListResponse
 * Author: tandingbo
 * CreateTime: 2017-09-15 16:52
 */
@JSONMessage(messageCode = 2002174)
public final class RoomListResponse extends BaseResponse {

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
