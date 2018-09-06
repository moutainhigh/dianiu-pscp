package com.edianniu.pscp.cs.dao;

import com.edianniu.pscp.cs.bean.needs.NeedsOrderInfo;
import com.edianniu.pscp.cs.bean.needs.ResponsedCompany;
import com.edianniu.pscp.cs.domain.CustomerNeedsOrder;
import com.edianniu.pscp.cs.domain.NeedsOrder;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface NeedsOrderDao extends BaseDao<NeedsOrder>{
	
	//根据需求ID和服务商响应订单号查询响应的服务商信息
    ResponsedCompany query(@Param("needsId")Long needsId, @Param("responsedOrderId")String responsedOrderId);
	
	//需求询价，确认合作
	void operateNeedsOrder(HashMap<String, Object> map);
	
	//根据需求ID和服务商响应状态查询服务商信息
	List<ResponsedCompany> queryList(HashMap<String, Object>queryMap);

	//根据响应编号获取响应详情
	NeedsOrderInfo queryRespondDetails(String orderId);

	List<CustomerNeedsOrder> queryEntityList(Map<String, Object> queryMap);

}
