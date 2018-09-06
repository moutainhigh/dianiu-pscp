package com.edianniu.pscp.das.meter.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.edianniu.pscp.das.meter.dao.CompanyMeterDao;
import com.edianniu.pscp.das.meter.dao.CompanyPowerPriceConfigDao;
import com.edianniu.pscp.das.meter.domain.CompanyMeter;
import com.edianniu.pscp.das.meter.domain.CompanyPowerPriceConfig;
import com.edianniu.pscp.das.meter.domain.MeterConfig;
import com.edianniu.pscp.das.meter.service.MeterService;

/**
 * MeterServiceImpl
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月27日 下午4:39:59 
 * @version V1.0
 */
@Service
@Repository("meterService")
public class MeterServiceImpl implements MeterService {
	private static final Logger logger = LoggerFactory
			.getLogger(MeterServiceImpl.class);
	private final static int LOAD_TYPE = 1;
	private final static int FACTOR_TYPE = 2;
	private final static int VOLTAGE_TYPE = 3;
	@Autowired
    private CompanyMeterDao companyMeterDao;
	@Autowired
    private CompanyPowerPriceConfigDao companyPowerPriceConfigDao;
	
	@Override
	public CompanyMeter getCompanyMeterById(String meterId) {
		CompanyMeter companyMeter=companyMeterDao.getByMeterId(meterId);
		if(companyMeter!=null){
			List<MeterConfig> configList = companyMeterDao.getConfigsByCompanyId(companyMeter.getCompanyId(), null);
			List<MeterConfig> voltageList = new ArrayList<>();
			List<MeterConfig> factorList = new ArrayList<>();
			List<MeterConfig> loadList = new ArrayList<>();
			for (MeterConfig meterConfig : configList) {
				if (meterConfig.getType().equals(VOLTAGE_TYPE)) {
					voltageList.add(meterConfig);
				}
				if (meterConfig.getType().equals(FACTOR_TYPE)) {
					factorList.add(meterConfig);
				}
				if (meterConfig.getType().equals(LOAD_TYPE)) {
					loadList.add(meterConfig);
				}
			}
			
			if(!voltageList.isEmpty()&&voltageList.size()==2){
				for(MeterConfig config:voltageList){
					if(config.getKey()==null){
						logger.error("仪表[{}]电压配置异常",meterId);
						continue;
					}
					if(!StringUtils.isNumeric(config.getValue())){
						logger.error("仪表[{}]电压配置异常",meterId);
						continue;
					}
					if(config.getKey()==1){//上限
						companyMeter.setuHigh(Double.parseDouble(config.getValue()));
					}
					else if(config.getKey()==2){//下限
						companyMeter.setuLower(Double.parseDouble(config.getValue()));
					}
				}
			} else{
				logger.error("仪表[{}]电压配置异常",meterId);
			}
			if(!factorList.isEmpty()&&factorList.size()==4){
				for(MeterConfig config:factorList){
					if(config.getKey()==null){
						logger.error("仪表[{}]功率因数配置异常",meterId);
						continue;
					}
					if(config.getKey()==3){ //正常
						companyMeter.setNormalFactor(config.getValue());
					}
				}
			} else {
				logger.error("仪表[{}]功率因数配置异常",meterId);
			}
			if(!loadList.isEmpty()&&loadList.size()==4){
				for(MeterConfig config:loadList){
					if(config.getKey()==null){
						logger.error("仪表[{}]负荷配置异常",meterId);
						continue;
					}
					if(config.getKey()==2){ //经济
						companyMeter.setEconomicLoad(config.getValue());
					}
				}
			} else {
				logger.error("仪表[{}]负荷配置异常",meterId);
			}
		}
		return companyMeter;
	}
	@Override
	public CompanyPowerPriceConfig getByCompanyId(Long companyId) {
		return companyPowerPriceConfigDao.getByCompanyId(companyId);
	}


}
