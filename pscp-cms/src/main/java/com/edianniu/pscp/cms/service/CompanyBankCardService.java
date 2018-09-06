package com.edianniu.pscp.cms.service;

import com.edianniu.pscp.cms.entity.CompanyBankCardEntity;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-10 11:06:31
 */
public interface CompanyBankCardService {
	
	CompanyBankCardEntity queryObject(Long id);
	
	List<CompanyBankCardEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(CompanyBankCardEntity companyBankCard);
	
	void update(CompanyBankCardEntity companyBankCard);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
	
	public CompanyBankCardEntity getBankCardByCompany(Long companyId);
}
