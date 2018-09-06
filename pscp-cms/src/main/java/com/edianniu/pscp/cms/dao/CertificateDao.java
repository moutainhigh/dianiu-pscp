package com.edianniu.pscp.cms.dao;

import java.util.List;

import com.edianniu.pscp.cms.entity.CertificateEntity;

/**
 * ${comments}
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-04 18:33:42
 */
public interface CertificateDao extends BaseDao<CertificateEntity> {
	
	List<CertificateEntity> getAllCertificate();
	
}
