package com.edianniu.pscp.renter.mis.dao;

import org.apache.ibatis.annotations.Param;
import com.edianniu.pscp.renter.mis.domain.MeterInfo;

public interface MeterInfoDao extends BaseDao<MeterInfo>{

	MeterInfo queryInfo(@Param("meterNo") String meterNo);

	
	
}
