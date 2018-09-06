package com.edianniu.pscp.cms.service;

import com.edianniu.pscp.cms.entity.AppCheckUpdateEntity;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 * 
 * @author wangcheng.li
 * @email wangcheng.li@edianniu.com
 * @date 2017-07-03 10:08:40
 */
public interface AppCheckUpdateService {
	
	AppCheckUpdateEntity queryObject(Long id);
	
	List<AppCheckUpdateEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(AppCheckUpdateEntity appCheckUpdate);
	
	void update(AppCheckUpdateEntity appCheckUpdate);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

	Integer getMaxAppLatestVer(String appPkg);
}
