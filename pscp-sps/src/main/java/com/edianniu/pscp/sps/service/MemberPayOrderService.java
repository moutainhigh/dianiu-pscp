package com.edianniu.pscp.sps.service;

import com.edianniu.pscp.sps.domain.MemberPayOrder;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-23 17:22:14
 */
public interface MemberPayOrderService {
	
	MemberPayOrder queryObject(Long id);
	
	List<MemberPayOrder> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(MemberPayOrder memberPayOrder);
	
	void update(MemberPayOrder memberPayOrder);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

    MemberPayOrder queryEntityByOrderId(String orderId);
}
