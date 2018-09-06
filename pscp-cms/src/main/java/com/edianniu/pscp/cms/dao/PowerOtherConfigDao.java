package com.edianniu.pscp.cms.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edianniu.pscp.cms.entity.PowerOtherConfigEntity;

public interface PowerOtherConfigDao extends BaseDao<PowerOtherConfigEntity>{
	
	// 判断记录是否存在
	PowerOtherConfigEntity isExist(@Param("companyId") Long companyId, @Param("type") Integer type, @Param("key") Integer key);

	//获取配置详情
	List<PowerOtherConfigEntity> queryConfigs(HashMap<String, Object> map);

	
}
