package com.edianniu.pscp.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.edianniu.pscp.portal.dao.CompanyCertificateImageDao;
import com.edianniu.pscp.portal.entity.CompanyCertificateImageEntity;
import com.edianniu.pscp.portal.service.CompanyCertificateImageService;



@Service("companyCertificateImageService")
public class CompanyCertificateImageServiceImpl implements CompanyCertificateImageService {
	@Autowired
	private CompanyCertificateImageDao companyCertificateImageDao;
	
	@Override
	public CompanyCertificateImageEntity queryObject(Long id){
		return companyCertificateImageDao.queryObject(id);
	}
	
	@Override
	public List<CompanyCertificateImageEntity> queryList(Map<String, Object> map){
		return companyCertificateImageDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return companyCertificateImageDao.queryTotal(map);
	}
	
	@Override
	public void save(CompanyCertificateImageEntity companyCertificateImage){
		companyCertificateImageDao.save(companyCertificateImage);
	}
	
	@Override
	public void update(CompanyCertificateImageEntity companyCertificateImage){
		companyCertificateImageDao.update(companyCertificateImage);
	}
	
	@Override
	public void delete(Long id){
		companyCertificateImageDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		companyCertificateImageDao.deleteBatch(ids);
	}

	@Override
	public List<CompanyCertificateImageEntity> getCertificateImagesByCompany(Long companyId) {
		 List<CompanyCertificateImageEntity>list=companyCertificateImageDao.getCertificateImagesByCompany(companyId);
		return list;
	}

	@Override
	public CompanyCertificateImageEntity getCertificateImageByFileId(String fileId) {
		CompanyCertificateImageEntity bean=companyCertificateImageDao.getCertificateImageByFileId(fileId);
		return bean;
	}
	
}
