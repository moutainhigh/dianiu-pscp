package com.edianniu.pscp.mis.dao;

import com.edianniu.pscp.mis.domain.CompanyRenterConfig;

/**
 * CompanyRenterConfigDao
 *
 * @author yanlin.chen
 * @date 2018年03月30日 上午10:49:00
 */
public interface CompanyRenterConfigDao extends BaseDao<CompanyRenterConfig> {

	CompanyRenterConfig getById(Long id);

	CompanyRenterConfig getByRenterId(Long renterId);
	
	int updateSwitchStatus(CompanyRenterConfig companyRenterConfig);
}
