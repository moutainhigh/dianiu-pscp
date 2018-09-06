package com.edianniu.pscp.mis.dao;

import com.edianniu.pscp.mis.bean.workorder.room.RoomInfo;
import com.edianniu.pscp.mis.domain.CustomerDistributionRoom;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 *
 * @author wangcheng.li
 * @email wangcheng.li@edianniu.com
 * @date 2017-09-18 10:33:12
 */
public interface CustomerDistributionRoomDao extends BaseDao<CustomerDistributionRoom> {

    CustomerDistributionRoom getEntityById(Long roomId);

    List<RoomInfo> getRoomInfoByIds(@Param("ids") Long[] ids);

    List<RoomInfo> getRoomInfoByMap(Map<String, Object> param);
}
