package com.edianniu.pscp.cs.dao;

import java.util.List;
import java.util.Map;

import com.edianniu.pscp.cs.bean.power.PowerOtherConfigInfo;

/**
 * 企业用电其他配置管理
 * @author zhoujianjian
 * @date 2018年1月16日 上午10:01:30
 */
public interface PowerOtherConfigDao extends BaseDao<PowerOtherConfigInfo>{

	List<PowerOtherConfigInfo> queryConfigs(Map<String, Object> queryMap);
	
	
}
