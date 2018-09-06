package com.edianniu.pscp.cms.service;

import java.util.HashMap;
import java.util.List;

import com.edianniu.pscp.cms.entity.CompanyEquipmentEntity;

/**
 * 客户设备
 * @author zhoujianjian
 * @date 2017年12月19日 下午3:52:46
 */
public interface CompanyEquipmentService {

	void save(CompanyEquipmentEntity bean);

	CompanyEquipmentEntity queryObject(Long id);

	List<CompanyEquipmentEntity> queryList(HashMap<String, Object> map);

	int queryTotal(HashMap<String, Object> map);

	void update(CompanyEquipmentEntity bean);

	void deleteBatch(Long[] ids);

}
