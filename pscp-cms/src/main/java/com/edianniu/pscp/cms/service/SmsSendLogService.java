package com.edianniu.pscp.cms.service;

import com.edianniu.pscp.cms.entity.SmsSendLogEntity;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 * 
 * @author wangcheng.li
 * @email wangcheng.li@edianniu.com
 * @date 2017-07-03 17:20:37
 */
public interface SmsSendLogService {
	
	SmsSendLogEntity queryObject(Long id);
	
	List<SmsSendLogEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	

	
	void update(SmsSendLogEntity smsSendLog);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
