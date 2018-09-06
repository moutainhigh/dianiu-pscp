package com.edianniu.pscp.cms.dao;

import java.util.List;

import com.edianniu.pscp.cms.entity.ElectricianCertificateEntity;

/**
 * ${comments}
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-04 18:33:41
 */
public interface ElectricianCertificateDao extends BaseDao<ElectricianCertificateEntity> {
	
	List<ElectricianCertificateEntity> getElectricianCertificates(Long electricianId);
	
	
	
	
}
