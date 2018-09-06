package com.edianniu.pscp.cms.service;

import com.edianniu.pscp.cms.entity.ElectricianCertificateEntity;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-04 18:33:41
 */
public interface ElectricianCertificateService {
	
	ElectricianCertificateEntity queryObject(Long id);
	
	List<ElectricianCertificateEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ElectricianCertificateEntity electricianCertificate);
	
	void update(ElectricianCertificateEntity electricianCertificate);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
	
	public List<ElectricianCertificateEntity> getElectricianCertificates(Long electricianId);
	
	
}
