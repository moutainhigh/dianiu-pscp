package com.edianniu.pscp.mis.service.impl;

import com.edianniu.pscp.mis.bean.workorder.room.RoomInfo;
import com.edianniu.pscp.mis.dao.CustomerDistributionRoomDao;
import com.edianniu.pscp.mis.domain.CustomerDistributionRoom;
import com.edianniu.pscp.mis.service.CustomerDistributionRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
@Repository("customerDistributionRoomService")
public class CustomerDistributionRoomServiceImpl implements CustomerDistributionRoomService {

    @Autowired
    private CustomerDistributionRoomDao customerDistributionRoomDao;

    @Override
    public CustomerDistributionRoom getEntityById(Long roomId) {
        return customerDistributionRoomDao.getEntityById(roomId);
    }

    @Override
    public List<RoomInfo> getRoomInfoByIds(Long[] ids) {
        return customerDistributionRoomDao.getRoomInfoByIds(ids);
    }

    @Override
    public List<RoomInfo> getRoomInfoByMap(Map<String, Object> param) {
        return customerDistributionRoomDao.getRoomInfoByMap(param);
    }


}
