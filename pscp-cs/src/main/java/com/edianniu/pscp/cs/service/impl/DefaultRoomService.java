package com.edianniu.pscp.cs.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.edianniu.pscp.cs.bean.room.ListQuery;
import com.edianniu.pscp.cs.bean.room.RoomInfo;
import com.edianniu.pscp.cs.bean.room.vo.RoomVO;
import com.edianniu.pscp.cs.commons.PageResult;
import com.edianniu.pscp.cs.dao.RoomDao;
import com.edianniu.pscp.cs.domain.Room;
import com.edianniu.pscp.cs.service.RoomService;

/**
 * @author zhoujianjian
 * 2017年9月12日下午12:01:39
 */
@Transactional
@Service
@Repository("roomService")
public class DefaultRoomService implements RoomService {
	
	@Autowired
	private RoomDao roomDao;
	
	/**
	 * 配电房列表
	 */
	@Override
	public PageResult<RoomVO> selectRoomVOList (ListQuery listQuery) {
		if (listQuery.getCompanyId() == 0L) {
			listQuery.setCompanyId(-1L);
		}
		PageResult<RoomVO> result = new PageResult<RoomVO>();
		int total =roomDao.queryListRoomVOCount(listQuery);
		int nextOffset = 0;
		if (total > 0) {
			List<RoomVO> entityList = roomDao.queryListRoomVO(listQuery);
			
			result.setData(entityList);
			nextOffset=listQuery.getOffset() + entityList.size();
			result.setNextOffset(nextOffset);
		}
		
		if (nextOffset > 0 && nextOffset <total) {
			result.setHasNext(true);
		}
		
		result.setOffset(listQuery.getOffset());
		result.setNextOffset(nextOffset);		
		result.setTotal(total);		
		return result;
	}

	@Override
	public void saveRoom(RoomInfo roomInfo) {
		roomDao.save(roomInfo);
	}

	@Override
	public void update(RoomInfo roomInfo) {
		roomDao.update(roomInfo);
		
	}

	@Override
	public int delete(Long id, String modifyUser) {
		return roomDao.delete(id, modifyUser);
	}

	@Override
	public RoomVO queryRoomVO(Long id) {
		return roomDao.queryRoomVO(id);
	}

	/**
	 * 根据客户ID获取配电房列表
	 */
	@Override
	public List<RoomVO> getRoomListByCustomerId(Long customerId) {
		return roomDao.queryRoomListCustomerId(customerId);
	}

	@Override
	public List<RoomVO> getRoomsByIds(List<Long> ids) {
		Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("roomIds", ids);
        List<Room> roomList = roomDao.queryRoomList(queryMap);
        List<RoomVO> roomVOs = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(roomList)) {
        	for (Room room : roomList) {
    			RoomVO roomVO = new RoomVO();
    			roomVO.setId(room.getId());
    			roomVO.setName(room.getName());
    			roomVOs.add(roomVO);
    		}
		}
		return roomVOs;
	}

	


}
