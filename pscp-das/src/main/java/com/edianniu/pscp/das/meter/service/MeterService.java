package com.edianniu.pscp.das.meter.service;

import com.edianniu.pscp.das.meter.domain.CompanyMeter;
import com.edianniu.pscp.das.meter.domain.CompanyPowerPriceConfig;
/**
 * 仪表服务
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月27日 下午4:38:47 
 * @version V1.0
 */
public interface MeterService {
	public CompanyMeter getCompanyMeterById(String meterId);
	public CompanyPowerPriceConfig getByCompanyId(Long companyId);
}
