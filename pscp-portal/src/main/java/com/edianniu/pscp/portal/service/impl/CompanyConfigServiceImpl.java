package com.edianniu.pscp.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edianniu.pscp.portal.dao.PowerPriceConfigDao;
import com.edianniu.pscp.portal.entity.PowerPriceConfig;
import com.edianniu.pscp.portal.service.CompanyConfigService;

@Transactional
@Service("companyConfigService")
public class CompanyConfigServiceImpl implements CompanyConfigService {
	
	@Autowired
	private PowerPriceConfigDao powerPriceConfigDao;
	
	@Override
	public PowerPriceConfig getPowerPriceConfig(Long companyId) {
		PowerPriceConfig powerPriceConfig = powerPriceConfigDao.queryObject(companyId);
		return powerPriceConfig;
	}
}
