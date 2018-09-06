package com.edianniu.pscp.cms.dao;

import com.edianniu.pscp.cms.entity.CompanyBankCardEntity;

/**
 * ${comments}
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-10 11:06:31
 */
public interface CompanyBankCardDao extends BaseDao<CompanyBankCardEntity> {
	
	public CompanyBankCardEntity getBankCardByCompany(Long companyId);
	
}
