package com.edianniu.pscp.mis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edianniu.pscp.mis.domain.CompanyRenterMeter;

/**
 * CompanyRenterConfigDao
 *
 * @author yanlin.chen
 * @date 2018年03月30日 上午10:49:00
 */
public interface CompanyRenterMeterDao extends BaseDao<CompanyRenterMeter> {

	CompanyRenterMeter getById(Long id);

	List<CompanyRenterMeter> getByRenterId(Long renterId);
	
	CompanyRenterMeter getByRenterIdAndMeterNo(@Param("renterId")Long renterId,@Param("meterNo")String meterNo);
	
	int updateSwitchStatus(CompanyRenterMeter companyRenterMeter);
}
