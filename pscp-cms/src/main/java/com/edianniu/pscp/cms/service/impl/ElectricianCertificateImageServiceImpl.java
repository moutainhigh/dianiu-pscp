package com.edianniu.pscp.cms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.edianniu.pscp.cms.dao.ElectricianCertificateImageDao;
import com.edianniu.pscp.cms.entity.ElectricianCertificateImageEntity;
import com.edianniu.pscp.cms.service.ElectricianCertificateImageService;


/**
 * 电工证书图片
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月8日 上午11:55:19 
 * @version V1.0
 */
@Service("electricianCertificateImageService")
public class ElectricianCertificateImageServiceImpl implements ElectricianCertificateImageService {
	@Autowired
	private ElectricianCertificateImageDao electricianCertificateImageDao;
	
	@Override
	public ElectricianCertificateImageEntity queryObject(Long id){
		return electricianCertificateImageDao.queryObject(id);
	}
	
	@Override
	public List<ElectricianCertificateImageEntity> queryList(Map<String, Object> map){
		return electricianCertificateImageDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return electricianCertificateImageDao.queryTotal(map);
	}
	
	@Override
	public void save(ElectricianCertificateImageEntity electricianCertificateImage){
		electricianCertificateImageDao.save(electricianCertificateImage);
	}
	
	@Override
	public void update(ElectricianCertificateImageEntity electricianCertificateImage){
		electricianCertificateImageDao.update(electricianCertificateImage);
	}
	
	@Override
	public void delete(Long id){
		electricianCertificateImageDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		electricianCertificateImageDao.deleteBatch(ids);
	}

	@Override
	public List<ElectricianCertificateImageEntity> queryListByElectricianId(
			Long electricianId) {
		return electricianCertificateImageDao.queryListByElectricianId(electricianId);
	}

	@Override
	public void deleteByElectricianId(Long electricianId) {
		electricianCertificateImageDao.deleteByElectricianId(electricianId);
	}
	
}
