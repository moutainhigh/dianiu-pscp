package com.edianniu.pscp.sps.service.dubbo;

import java.util.List;
import java.util.Map;

/**
 * ClassName: SpsElectricianCertificateInfoService
 * Author: tandingbo
 * CreateTime: 2017-05-17 14:02
 */
public interface SpsElectricianCertificateInfoService {
	
	public List<Map<String, Object>> getByElectricianId(Long electricianId);
}
