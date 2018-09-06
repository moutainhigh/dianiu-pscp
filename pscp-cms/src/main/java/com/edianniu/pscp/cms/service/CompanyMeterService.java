package com.edianniu.pscp.cms.service;

import java.util.HashMap;
import java.util.List;

import com.edianniu.pscp.cms.entity.CompanyMeterEntity;

/**
 * 企业采集点
 * @author zhoujianjian
 * @date 2017年12月19日 下午7:21:16
 */
public interface CompanyMeterService {

	void save(CompanyMeterEntity bean);

	List<CompanyMeterEntity> queryList(HashMap<String, Object> map);

	int queryTotal(HashMap<String, Object> map);

	CompanyMeterEntity queryObject(Long id);

	void update(CompanyMeterEntity bean);

	void deleteBatch(Long[] ids);
	// 仪表编号唯一性限制
	boolean isExist(String meterId);

	boolean isNameExist(String name, Long companyId);


}
