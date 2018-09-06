package com.edianniu.pscp.cms.service;

import com.edianniu.pscp.cms.entity.ElectricianCertificateImageEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 电工证书图片
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-04 18:33:42
 */
public interface ElectricianCertificateImageService {
	
	ElectricianCertificateImageEntity queryObject(Long id);
	
	List<ElectricianCertificateImageEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ElectricianCertificateImageEntity electricianCertificateImage);
	
	void update(ElectricianCertificateImageEntity electricianCertificateImage);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
	
	void deleteByElectricianId(Long electricianId);
	
	public List<ElectricianCertificateImageEntity> queryListByElectricianId(Long electricianId);
}
