package com.edianniu.pscp.cms.service;



import java.util.List;
import java.util.Map;

import com.edianniu.pscp.cms.entity.CompanyEntity;

/**
 * 公司服务
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-04 16:00:18
 */
public interface CompanyService {
	
	CompanyEntity getCompanyByCompanyId(Long companyId);
	
	List<CompanyEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
	
	
}
