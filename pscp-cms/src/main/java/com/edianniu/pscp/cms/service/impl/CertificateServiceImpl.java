package com.edianniu.pscp.cms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.edianniu.pscp.cms.dao.CertificateDao;
import com.edianniu.pscp.cms.entity.CertificateEntity;
import com.edianniu.pscp.cms.service.CertificateService;



@Service("certificateService")
public class CertificateServiceImpl implements CertificateService {
	@Autowired
	private CertificateDao certificateDao;
	
	@Override
	public CertificateEntity queryObject(Long id){
		return certificateDao.queryObject(id);
	}
	
	@Override
	public List<CertificateEntity> queryList(Map<String, Object> map){
		return certificateDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return certificateDao.queryTotal(map);
	}
	
	@Override
	public void save(CertificateEntity certificate){
		certificateDao.save(certificate);
	}
	
	@Override
	public void update(CertificateEntity certificate){
		certificateDao.update(certificate);
	}
	
	@Override
	public void delete(Long id){
		certificateDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		certificateDao.deleteBatch(ids);
	}
	
}
