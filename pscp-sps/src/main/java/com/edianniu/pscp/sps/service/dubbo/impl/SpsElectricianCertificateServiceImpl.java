package com.edianniu.pscp.sps.service.dubbo.impl;

import java.util.List;
import java.util.Map;

import com.edianniu.pscp.sps.service.SpsElectricianCertificateService;
import com.edianniu.pscp.sps.service.dubbo.SpsElectricianCertificateInfoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * ClassName: SpsElectricianCertificateServiceImpl
 * Author: tandingbo
 * CreateTime: 2017-05-17 14:02
 */
@Service
@Repository("spsElectricianCertificateInfoService")
public class SpsElectricianCertificateServiceImpl implements SpsElectricianCertificateInfoService {
    private static final Logger logger = LoggerFactory.getLogger(SpsElectricianCertificateServiceImpl.class);

    @Autowired
    @Qualifier("spsElectricianCertificateService")
    private SpsElectricianCertificateService spsElectricianCertificateService;

	@Override
	public List<Map<String, Object>> getByElectricianId(Long electricianId) {
		List<Map<String, Object>> list=spsElectricianCertificateService.queryListMap(electricianId);
		return list;
	}

}
