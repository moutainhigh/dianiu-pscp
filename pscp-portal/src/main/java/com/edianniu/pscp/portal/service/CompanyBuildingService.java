package com.edianniu.pscp.portal.service;

import java.util.HashMap;
import java.util.List;

import com.edianniu.pscp.portal.entity.CompanyBuildingEntity;

public interface CompanyBuildingService {

	List<CompanyBuildingEntity> queryList(HashMap<String, Object> map);

}
