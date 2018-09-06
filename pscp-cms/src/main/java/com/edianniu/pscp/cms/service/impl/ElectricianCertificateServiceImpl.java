package com.edianniu.pscp.cms.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.edianniu.pscp.cms.dao.CertificateDao;
import com.edianniu.pscp.cms.dao.ElectricianCertificateDao;
import com.edianniu.pscp.cms.entity.CertificateEntity;
import com.edianniu.pscp.cms.entity.ElectricianCertificateEntity;
import com.edianniu.pscp.cms.service.ElectricianCertificateService;



@Service("electricianCertificateService")
public class ElectricianCertificateServiceImpl implements ElectricianCertificateService {
	@Autowired
	private ElectricianCertificateDao electricianCertificateDao;
	
	@Autowired
	private CertificateDao certificateDao;
	
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

	@Override
	public List<ElectricianCertificateEntity> getElectricianCertificates(Long electricianId) {
		List<ElectricianCertificateEntity> list=electricianCertificateDao.getElectricianCertificates(electricianId);
		List<CertificateEntity> allList=certificateDao.getAllCertificate();
		
		
		Map<Long,ElectricianCertificateEntity> map=new HashMap<Long,ElectricianCertificateEntity>();
		
		for(CertificateEntity certificate:allList){
			ElectricianCertificateEntity electricianCertificate=new ElectricianCertificateEntity();
			BeanUtils.copyProperties(certificate, electricianCertificate,new String[]{"id"});
			electricianCertificate.setCertificateId(certificate.getId());
			electricianCertificate.setElectricianId(electricianId);
			map.put(certificate.getId(), electricianCertificate);
		}
		
		for(ElectricianCertificateEntity certificate:list){
			ElectricianCertificateEntity bean=map.get(certificate.getCertificateId());
			if(bean!=null){
				BeanUtils.copyProperties(certificate,bean,new String[]{"name","type"});
				bean.setSelected(true);
				map.put(bean.getCertificateId(), bean);
			}
		}
		List<ElectricianCertificateEntity> certificates=new ArrayList<ElectricianCertificateEntity>();
		for(  ElectricianCertificateEntity electricianCertificateEntity:map.values()){
			certificates.add(electricianCertificateEntity);
		}
		
		
		return certificates;
	}
	
	
	
}
