package com.edianniu.pscp.renter.mis.service;

import java.util.List;
import java.util.Map;

import com.edianniu.pscp.renter.mis.bean.renter.RenterMeterInfo;
import com.edianniu.pscp.renter.mis.commons.PageResult;
import com.edianniu.pscp.renter.mis.domain.RenterMeter;

public interface RenterMeterService {

	/**
	 * 判断是否存在公摊仪表
	 * @param renterId
	 * @return
	 */
	boolean isExistPubilcMeter(Long renterId);
	
	/**
	 * 获取租客仪表列表
	 * @param renterId
	 * @return
	 */
	List<RenterMeter> getMeters(Long renterId);
	
	/**
	 * 判断是否是公摊仪表
	 * @param companyMeterId 公司仪表ID
	 * @return
	 */
	boolean isPublicMeter(Long companyMeterId);

	/**
	 * 分页获取租客仪表
	 * @param map
	 * @return
	 */
	PageResult<RenterMeterInfo> getPageMeters(Map<String, Object> map);
}
