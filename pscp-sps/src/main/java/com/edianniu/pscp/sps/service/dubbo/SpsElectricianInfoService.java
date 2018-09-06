package com.edianniu.pscp.sps.service.dubbo;

import com.edianniu.pscp.sps.bean.electrician.ElectricianVOResult;

/**
 * ClassName: SpsElectricianInfoService
 * Author: tandingbo
 * CreateTime: 2017-05-17 10:42
 */
public interface SpsElectricianInfoService {
    ElectricianVOResult selectElectricianVOByCompanyId(Long uid);
}
