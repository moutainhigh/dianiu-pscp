package com.edianniu.pscp.mis.dao;

import java.util.List;

import com.edianniu.pscp.mis.domain.CompanyCertificateImage;

/**
 * ${comments}
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-05 14:50:40
 */
public interface CompanyCertificateImageDao {
	
	public List<CompanyCertificateImage> getCertificateImagesByCompanyId(Long companyId);
	
	public CompanyCertificateImage getCertificateImageByFileId(String fileId);
	
	public void save(CompanyCertificateImage bean);
	
	public void update(CompanyCertificateImage bean);
	
	public void delete(Long id);
}
