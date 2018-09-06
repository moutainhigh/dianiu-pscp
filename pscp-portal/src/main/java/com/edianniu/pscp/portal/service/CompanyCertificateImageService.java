package com.edianniu.pscp.portal.service;

import com.edianniu.pscp.portal.entity.CompanyCertificateImageEntity;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-05 14:50:40
 */
public interface CompanyCertificateImageService {
	
	CompanyCertificateImageEntity queryObject(Long id);
	
	List<CompanyCertificateImageEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(CompanyCertificateImageEntity companyCertificateImage);
	
	void update(CompanyCertificateImageEntity companyCertificateImage);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
	
	List<CompanyCertificateImageEntity> getCertificateImagesByCompany(Long companyId);
	
	CompanyCertificateImageEntity getCertificateImageByFileId(String fileId);
}
