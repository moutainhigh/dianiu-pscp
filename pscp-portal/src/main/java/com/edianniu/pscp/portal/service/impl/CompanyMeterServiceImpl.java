package com.edianniu.pscp.portal.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edianniu.pscp.portal.dao.CompanyMeterDao;
import com.edianniu.pscp.portal.entity.CompanyMeterEntity;
import com.edianniu.pscp.portal.service.CompanyMeterService;

@Transactional
@Service("companyMeterService")
public class CompanyMeterServiceImpl implements CompanyMeterService {
	
	@Autowired
	private CompanyMeterDao companyMeterDao;

	@Override
	public List<CompanyMeterEntity> queryList(HashMap<String, Object> map) {
		return companyMeterDao.queryList(map);
	}

}
