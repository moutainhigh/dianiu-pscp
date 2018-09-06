package com.edianniu.pscp.cms.dao;

import java.util.HashMap;

import com.edianniu.pscp.cms.entity.CompanyBuildingEntity;

public interface CompanyBuildingDao extends BaseDao<CompanyBuildingEntity>{

	int getCountOfLines(Long id);

	CompanyBuildingEntity queryObjectByName(HashMap<String, Object> map);

}
