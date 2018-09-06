package com.edianniu.pscp.cms.dao;

import java.util.HashMap;

import com.edianniu.pscp.cms.entity.CompanyMeterEntity;

public interface CompanyMeterDao extends BaseDao<CompanyMeterEntity>{

	int isExist(String meterId);

	Object queryObjectByName(HashMap<String, Object> map);

}
