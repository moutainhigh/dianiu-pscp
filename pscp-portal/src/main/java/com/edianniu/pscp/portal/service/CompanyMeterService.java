package com.edianniu.pscp.portal.service;

import java.util.HashMap;
import java.util.List;

import com.edianniu.pscp.portal.entity.CompanyMeterEntity;

public interface CompanyMeterService {

	List<CompanyMeterEntity> queryList(HashMap<String, Object> map);

}
