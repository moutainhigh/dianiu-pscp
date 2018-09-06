package com.edianniu.pscp.portal.service;

import java.util.List;
import java.util.Map;

import com.edianniu.pscp.portal.commons.Result;
import com.edianniu.pscp.portal.entity.ElectricianEntity;

/**
 * 电工服务--后续迁移到mis服务中去 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-04-14 18:17:07
 */
public interface ElectricianService {
	
	ElectricianEntity queryObject(Long id);
	
	ElectricianEntity getByUid(Long uid);
	
	List<ElectricianEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	Result save(ElectricianEntity electrician);
	
	Result update(ElectricianEntity electrician);
	
	void deleteBatch(Long[] ids);
}
