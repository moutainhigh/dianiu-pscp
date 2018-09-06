package com.edianniu.pscp.mis.service.impl;

import com.edianniu.pscp.mis.dao.ElectricianCertificateImgDao;
import com.edianniu.pscp.mis.service.ElectricianCertificateImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * ClassName: DefaultElectricianCertificateImageService
 * Author: tandingbo
 * CreateTime: 2017-08-04 14:29
 */
@Service
@Repository("electricianCertificateImageService")
public class DefaultElectricianCertificateImageService implements ElectricianCertificateImageService {

    @Autowired
    private ElectricianCertificateImgDao electricianCertificateImgDao;

    @Override
    public List<Map<String, Object>> queryListMap(Long electricianId) {
        return electricianCertificateImgDao.queryListMap(electricianId);
    }
}
