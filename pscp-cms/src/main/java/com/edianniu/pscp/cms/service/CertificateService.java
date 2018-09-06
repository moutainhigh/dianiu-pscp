package com.edianniu.pscp.cms.service;

import com.edianniu.pscp.cms.entity.CertificateEntity;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-04 18:33:42
 */
public interface CertificateService {
	
	CertificateEntity queryObject(Long id);
	
	List<CertificateEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(CertificateEntity certificate);
	
	void update(CertificateEntity certificate);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
