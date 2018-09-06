package com.edianniu.pscp.cms.service;

import com.edianniu.pscp.cms.entity.SmsTemplateEntity;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 * 
 * @author wangcheng.li
 * @email wangcheng.li@edianniu.com
 * @date 2017-07-03 15:22:52
 */
public interface SmsTemplateService {
	
	SmsTemplateEntity queryObject(Long id);
	
	List<SmsTemplateEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SmsTemplateEntity smsTemplate);
	
	void update(SmsTemplateEntity smsTemplate);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
