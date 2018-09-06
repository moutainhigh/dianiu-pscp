package com.edianniu.pscp.mis.service.impl;

import com.edianniu.pscp.mis.dao.ElectricianEvaluateDao;
import com.edianniu.pscp.mis.domain.ElectricianEvaluate;
import com.edianniu.pscp.mis.service.ElectricianEvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * ClassName: DefaultElectricianEvaluateService
 * Author: tandingbo
 * CreateTime: 2017-05-12 10:31
 */
@Service
@Repository("electricianEvaluateService")
public class DefaultElectricianEvaluateService implements ElectricianEvaluateService {

    @Autowired
    private ElectricianEvaluateDao electricianEvaluateDao;

    @Override
    public ElectricianEvaluate getEntityByElectricianId(Long uid, Long electricianWorkOrderId) {
        return electricianEvaluateDao.getEntityByElectricianId(uid, electricianWorkOrderId);
    }
}
