package com.edianniu.pscp.renter.mis.dao;

import org.apache.ibatis.annotations.Param;

import com.edianniu.pscp.renter.mis.domain.RenterMeter;

public interface RenterMeterDao extends BaseDao<RenterMeter>{

	Double rateSum(Long meterId);
	
	/**
	 * 判断是否存在公摊仪表
	 * @param companyMeterId
	 * @return
	 */
	int countByCompanyMeterId(@Param("companyMeterId")Long companyMeterId);

	/**
	 * 获取指定仪表的费用占比
	 * @param meterId
	 * @param renterId
	 * @return
	 */
	Double getRate(@Param("meterId")Long meterId, @Param("renterId")Long renterId);


}
