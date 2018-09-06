package com.edianniu.pscp.mis.service;

import com.edianniu.pscp.mis.bean.workorder.room.RoomInfo;
import com.edianniu.pscp.mis.domain.CustomerDistributionRoom;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 *
 * @author wangcheng.li
 * @email wangcheng.li@edianniu.com
 * @date 2017-09-18 10:33:12
 */
public interface CustomerDistributionRoomService {

    CustomerDistributionRoom getEntityById(Long roomId);

    List<RoomInfo> getRoomInfoByIds(Long[] ids);

    List<RoomInfo> getRoomInfoByMap(Map<String, Object> param);
}
