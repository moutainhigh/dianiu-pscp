package com.edianniu.pscp.renter.mis.dao;

import java.util.List;
import java.util.Map;

import com.edianniu.pscp.renter.mis.domain.Renter;

public interface RenterDao extends BaseDao<Renter>{

	int queryCount(Map<String, Object> map);

	Renter getInfo(Map<String, Object> map);

	List<Renter> getListByMemberId(Long uid);
	
	List<Long> getRenterIds(Map<String, Object> map);
	
}
