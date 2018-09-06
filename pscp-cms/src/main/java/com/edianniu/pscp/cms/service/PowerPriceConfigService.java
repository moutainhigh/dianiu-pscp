package com.edianniu.pscp.cms.service;

import java.util.HashMap;

import com.edianniu.pscp.cms.entity.PowerPriceConfigEntity;

/**
 * 企业电价配置
 * @author zhoujianjian
 * @date 2018年1月3日 下午8:26:31
 */
public interface PowerPriceConfigService {

	/**
	 * 保存、编辑客户电价配置
	 */
	void save(PowerPriceConfigEntity entity);

	/**
	 * 电价配置详情
	 */
	PowerPriceConfigEntity queryObject(HashMap<String, Object> map);

	/**
	 * 电价配置删除
	 */
	void delete(Long id);

}
