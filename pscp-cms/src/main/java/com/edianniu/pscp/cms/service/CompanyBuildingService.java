package com.edianniu.pscp.cms.service;

import java.util.List;
import java.util.Map;

import com.edianniu.pscp.cms.entity.CompanyBuildingEntity;

/**
 * 客户楼宇
 * @author zhoujianjian
 * @date 2017年12月19日 上午10:42:50
 */
public interface CompanyBuildingService {

	void save(CompanyBuildingEntity bean);

	List<CompanyBuildingEntity> queryList(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);

	CompanyBuildingEntity queryObject(Long id);

	void update(CompanyBuildingEntity bean);

	void deleteBatch(Long[] ids);

	/**
	 * 获取绑定的线路条数
	 */
	int getCountOfLines(Long id);

	/**
	 * 判断客户楼宇名称是否重复
	 * @param name
	 * @param companyId
	 * @return
	 */
	boolean isNameExist(String name, Long companyId);

}
