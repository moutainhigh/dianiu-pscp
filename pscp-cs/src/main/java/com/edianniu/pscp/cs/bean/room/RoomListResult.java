package com.edianniu.pscp.cs.bean.room;

import java.util.List;

import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.room.vo.RoomVO;

/**
 * 根据客户ID获取所有配电房
 * @author zhoujianjian
 * @date 2017年12月5日 下午5:27:53
 */
public class RoomListResult extends Result{
	private static final long serialVersionUID = 1L;
	
	private List<RoomVO> roomList;

	public List<RoomVO> getRoomList() {
		return roomList;
	}

	public void setRoomList(List<RoomVO> roomList) {
		this.roomList = roomList;
	}
	

}
