package com.edianniu.pscp.portal.service;

import com.edianniu.pscp.mis.bean.company.CompanySaveOrAuthResult;
import com.edianniu.pscp.portal.entity.CompanyEntity;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-04 16:00:18
 */
public interface CompanyService {
	
	CompanyEntity getCompanyByCompanyId(Long companyId);
	
	List<CompanyEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	CompanySaveOrAuthResult save(CompanyEntity company);
	
	CompanySaveOrAuthResult update(CompanyEntity company);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
	
	public CompanyEntity getCompanyByUid(Long uid);
	
	public CompanyEntity getSimpleCompanyByUid(Long uid);
}
