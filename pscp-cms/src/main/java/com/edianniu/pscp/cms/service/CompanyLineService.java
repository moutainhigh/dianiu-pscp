package com.edianniu.pscp.cms.service;

import java.util.HashMap;
import java.util.List;

import com.edianniu.pscp.cms.entity.CompanyLineEntity;

/**
 * 企业线路
 * @author zhoujianjian
 * @date 2017年12月20日 上午9:48:22
 */
public interface CompanyLineService {

	CompanyLineEntity queryObject(Long id);

	void saveOrUpdate(CompanyLineEntity bean);

	List<CompanyLineEntity> queryList(HashMap<String, Object> map);

	int queryTotal(HashMap<String, Object> map);

	void deleteBatch(Long[] ids);

	// 获取子线路数量
	int getCountChildLines(Long id);


}
