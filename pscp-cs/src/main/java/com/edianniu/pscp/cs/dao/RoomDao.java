package com.edianniu.pscp.cs.dao;

import com.edianniu.pscp.cs.bean.room.ListQuery;
import com.edianniu.pscp.cs.bean.room.RoomInfo;
import com.edianniu.pscp.cs.bean.room.vo.RoomVO;
import com.edianniu.pscp.cs.domain.Room;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author zhoujianjian
 * 2017年9月11日下午10:31:19
 */
public interface RoomDao extends BaseDao<RoomInfo>{
	
	int queryListRoomVOCount(ListQuery listQuery);
	
	List<RoomVO> queryListRoomVO(ListQuery listQuery);
	
	RoomVO queryRoomVO(Long id);
	
	void save(RoomInfo roomInfo);
	
	int update(RoomInfo roomInfo);
	
	int delete(@Param("id") Long id, @Param("modifiedUser") String modifiedUser);
	
	List<Room> queryRoomList(Map<String, Object> queryMap);

	List<RoomVO> queryRoomListCustomerId(Long customerId);
}
