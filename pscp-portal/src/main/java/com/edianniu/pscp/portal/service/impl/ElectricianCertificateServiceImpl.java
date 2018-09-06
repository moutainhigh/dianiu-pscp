package com.edianniu.pscp.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.edianniu.pscp.portal.dao.ElectricianCertificateDao;
import com.edianniu.pscp.portal.entity.ElectricianCertificateEntity;
import com.edianniu.pscp.portal.service.ElectricianCertificateService;



@Service("electricianCertificateService")
public class ElectricianCertificateServiceImpl implements ElectricianCertificateService {
	@Autowired
	private ElectricianCertificateDao electricianCertificateDao;
	
	@Override
	public ElectricianCertificateEntity queryObject(Long id){
		return electricianCertificateDao.queryObject(id);
	}
	
	@Override
	public List<ElectricianCertificateEntity> queryList(Map<String, Object> map){
		return electricianCertificateDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return electricianCertificateDao.queryTotal(map);
	}
	
	@Override
	public void save(ElectricianCertificateEntity electricianCertificate){
		electricianCertificateDao.save(electricianCertificate);
	}
	
	@Override
	public void update(ElectricianCertificateEntity electricianCertificate){
		electricianCertificateDao.update(electricianCertificate);
	}
	
	@Override
	public void delete(Long id){
		electricianCertificateDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		electricianCertificateDao.deleteBatch(ids);
	}
	
}
