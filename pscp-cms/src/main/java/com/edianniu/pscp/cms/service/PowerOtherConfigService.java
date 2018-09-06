package com.edianniu.pscp.cms.service;

import java.util.HashMap;
import java.util.List;

import com.edianniu.pscp.cms.entity.PowerOtherConfigEntity;

/**
 * 企业用电其他配置
 * @author zhoujianjian
 * @date 2018年1月10日 下午3:32:49
 */
public interface PowerOtherConfigService {
	
	// 保存用户配置
	void save(PowerOtherConfigEntity[] entities);

	// 获取客户配置信息
	List<PowerOtherConfigEntity> queryConfigs(HashMap<String, Object> map);

	// 删除客户配置信息
	void deleteConfigs(Long companyId, Integer type);

}
