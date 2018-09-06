package com.edianniu.pscp.sps.service.impl;

import com.edianniu.pscp.sps.dao.SpsElectricianCertificateDao;
import com.edianniu.pscp.sps.service.SpsElectricianCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * ClassName: DefaultSpsElectricianCertificateService
 * Author: tandingbo
 * CreateTime: 2017-05-17 14:01
 */
@Service
@Repository("spsElectricianCertificateService")
public class DefaultSpsElectricianCertificateService implements SpsElectricianCertificateService {

    @Autowired
    private SpsElectricianCertificateDao spsElectricianCertificateDao;

    @Override
    public List<Map<String, Object>> queryListMap(Long electricianId) {
        return spsElectricianCertificateDao.queryListMap(electricianId);
    }
}
