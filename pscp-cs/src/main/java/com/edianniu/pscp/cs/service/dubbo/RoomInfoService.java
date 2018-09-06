package com.edianniu.pscp.cs.service.dubbo;

import java.util.List;

import com.edianniu.pscp.cs.bean.room.ListReqData;
import com.edianniu.pscp.cs.bean.room.ListResult;
import com.edianniu.pscp.cs.bean.room.RoomInfo;
import com.edianniu.pscp.cs.bean.room.RoomListResult;
import com.edianniu.pscp.cs.bean.room.SaveResult;
import com.edianniu.pscp.cs.bean.room.vo.RoomVO;
import com.edianniu.pscp.sps.bean.project.DeleteResult;

/**
 * 
 * @author zhoujianjian
 * 2017年9月12日下午1:49:43
 */
public interface RoomInfoService {
	
	ListResult roomListResult(ListReqData listReqData);

	SaveResult save(Long uid,RoomInfo roomInfo);
	
	DeleteResult delete(Long id, Long uid);

	RoomVO getRoomById(Long roomId);
	
	/**
	 * 根据customerId获取客户所有的配电房，门户使用
	 * @param customerId  companyCustomer的主键
	 * @param companyId   companyId服务商的公司ID
	 * @return
	 */
	RoomListResult getRoomsByCustomerId(Long customerId, Long companyId);

	/**
	 * 根据配电房IDs查询配电房列表
	 * @param ids
	 * @return
	 */
	RoomListResult getRoomsByIds(List<Long> ids);

}
