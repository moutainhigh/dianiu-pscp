package com.edianniu.pscp.mis.service;


import java.util.List;

import com.edianniu.pscp.mis.domain.CompanyCertificateImage;

/**
 * ${comments}
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-05 14:50:40
 */
public interface CompanyCertificateImageService {
	
	
	
	void save(CompanyCertificateImage companyCertificateImage);
	
	void update(CompanyCertificateImage companyCertificateImage);
	
	void delete(Long id);
	
	
	List<CompanyCertificateImage> getCertificateImagesByCompany(Long companyId);
	
	CompanyCertificateImage getCertificateImageByFileId(String fileId);
}
