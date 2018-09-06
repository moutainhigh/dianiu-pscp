package com.edianniu.pscp.sps.service.impl;

import com.edianniu.pscp.sps.dao.SpsElectricianCertificateImageDao;
import com.edianniu.pscp.sps.service.SpsElectricianCertificateImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * ClassName: DefaultSpsElectricianCertificateImageService
 * Author: tandingbo
 * CreateTime: 2017-05-25 10:24
 */
@Service
@Repository("spsElectricianCertificateImageService")
public class DefaultSpsElectricianCertificateImageService implements SpsElectricianCertificateImageService {

    @Autowired
    private SpsElectricianCertificateImageDao spsElectricianCertificateImageDao;

    @Override
    public List<Map<String, Object>> queryListMap(Long electricianId) {
        return spsElectricianCertificateImageDao.queryListMap(electricianId);
    }
}
