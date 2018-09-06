package com.edianniu.pscp.mis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edianniu.pscp.mis.dao.CompanyCertificateImageDao;
import com.edianniu.pscp.mis.domain.CompanyCertificateImage;
import com.edianniu.pscp.mis.service.CompanyCertificateImageService;

import java.util.List;



@Service("companyCertificateImageService")
public class DefaultCompanyCertificateImageService implements CompanyCertificateImageService {
	@Autowired
	private CompanyCertificateImageDao companyCertificateImageDao;
	
	
	
	@Override
	public void save(CompanyCertificateImage companyCertificateImage){
		companyCertificateImageDao.save(companyCertificateImage);
	}
	
	@Override
	public void update(CompanyCertificateImage companyCertificateImage){
		companyCertificateImageDao.update(companyCertificateImage);
	}
	
	@Override
	public void delete(Long id){
		companyCertificateImageDao.delete(id);
	}
	
	

	@Override
	public List<CompanyCertificateImage> getCertificateImagesByCompany(Long companyId) {
		 List<CompanyCertificateImage>list=companyCertificateImageDao.getCertificateImagesByCompanyId(companyId);
		return list;
	}

	@Override
	public CompanyCertificateImage getCertificateImageByFileId(String fileId) {
		CompanyCertificateImage bean=companyCertificateImageDao.getCertificateImageByFileId(fileId);
		return bean;
	}
	
}
