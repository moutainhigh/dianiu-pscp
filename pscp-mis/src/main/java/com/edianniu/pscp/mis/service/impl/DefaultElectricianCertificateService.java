package com.edianniu.pscp.mis.service.impl;

import com.edianniu.pscp.mis.dao.ElectricianCertificateDao;
import com.edianniu.pscp.mis.service.ElectricianCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * ClassName: DefaultElectricianCertificateService
 * Author: tandingbo
 * CreateTime: 2017-08-04 14:20
 */
@Service
@Repository("electricianCertificateService")
public class DefaultElectricianCertificateService implements ElectricianCertificateService {

    @Autowired
    private ElectricianCertificateDao electricianCertificateDao;

    @Override
    public List<Map<String, Object>> queryListMap(Long electricianId) {
        return electricianCertificateDao.queryListMap(electricianId);
    }
}
