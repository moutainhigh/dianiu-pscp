package com.edianniu.pscp.cms.service;

import java.util.List;
import java.util.Map;

import com.edianniu.pscp.cms.entity.ElectricianEntity;

/**
 * 电工服务
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-04-14 18:17:07
 */
public interface ElectricianService {
	
	ElectricianEntity getById(Long id);
	
	ElectricianEntity getByUid(Long uid);
	
	List<ElectricianEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void deleteBatch(Long[] ids);
}
