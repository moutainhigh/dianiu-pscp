package com.edianniu.pscp.cs.service;

import java.util.List;

import com.edianniu.pscp.cs.bean.room.ListQuery;
import com.edianniu.pscp.cs.bean.room.RoomInfo;
import com.edianniu.pscp.cs.bean.room.vo.RoomVO;
import com.edianniu.pscp.cs.commons.PageResult;

public interface RoomService {
	
	PageResult<RoomVO> selectRoomVOList(ListQuery listQuery);
	
	void saveRoom(RoomInfo roomInfo);
	
	void update(RoomInfo roomInfo);
	
	int delete(Long id, String modifiedUser);
	
	RoomVO queryRoomVO(Long id);

	List<RoomVO> getRoomListByCustomerId(Long customerId);

	List<RoomVO> getRoomsByIds(List<Long> ids);


}
