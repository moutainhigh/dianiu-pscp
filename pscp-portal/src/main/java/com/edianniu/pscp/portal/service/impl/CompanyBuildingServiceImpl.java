package com.edianniu.pscp.portal.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edianniu.pscp.portal.dao.CompanyBuildingDao;
import com.edianniu.pscp.portal.entity.CompanyBuildingEntity;
import com.edianniu.pscp.portal.service.CompanyBuildingService;


@Transactional
@Service("companyBuildingService")
public class CompanyBuildingServiceImpl implements CompanyBuildingService {

	@Autowired
	private CompanyBuildingDao companyBuildingDao;

	@Override
	public List<CompanyBuildingEntity> queryList(HashMap<String, Object> map) {
		return companyBuildingDao.queryList(map);
	}
	
}
