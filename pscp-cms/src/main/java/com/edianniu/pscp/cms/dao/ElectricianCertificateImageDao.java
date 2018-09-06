package com.edianniu.pscp.cms.dao;

import java.util.List;

import com.edianniu.pscp.cms.entity.ElectricianCertificateImageEntity;

/**
 * ${comments}
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-04 18:33:42
 */
public interface ElectricianCertificateImageDao extends BaseDao<ElectricianCertificateImageEntity> {
	public List<ElectricianCertificateImageEntity> queryListByElectricianId(Long electricianId);
	public void deleteByElectricianId(Long electricianId);
}
