package com.edianniu.pscp.mis.service;

import com.edianniu.pscp.mis.domain.ElectricianEvaluate;

/**
 * ClassName: ElectricianEvaluateService
 * Author: tandingbo
 * CreateTime: 2017-05-12 10:30
 */
public interface ElectricianEvaluateService {
    ElectricianEvaluate getEntityByElectricianId(Long uid, Long electricianWorkOrderId);
}
