package com.edianniu.pscp.cms.dao;

import com.edianniu.pscp.cms.entity.CompanyEntity;

/**
 * ${comments}
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-04 16:00:18
 */
public interface CompanyDao extends BaseDao<CompanyEntity> {
	
	public CompanyEntity getCompanyByUid(Long uid);
	
	public CompanyEntity getCompanyById(Long id);
	
	public CompanyEntity getCompanyByName(String name);
	
	
}
