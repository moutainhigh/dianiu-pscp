package com.edianniu.pscp.renter.mis.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.edianniu.pscp.renter.mis.domain.RenterConfig;


public interface RenterConfigDao extends BaseDao<RenterConfig>{

	int queryCount(Map<String, Object> map);

	RenterConfig queryObject(Map<String, Object> map);
	int updateBillTaskStatus(@Param("id")Long id,@Param("billTaskStatus")Integer billTaskStatus,@Param("modifiedUser")String modifiedUser);
	int updateBillTaskInfo(RenterConfig renterConfig);
}
