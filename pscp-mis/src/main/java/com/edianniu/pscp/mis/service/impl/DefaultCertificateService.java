package com.edianniu.pscp.mis.service.impl;

import com.edianniu.pscp.mis.dao.CertificateDao;
import com.edianniu.pscp.mis.domain.Certificate;
import com.edianniu.pscp.mis.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: DefaultCertificateService
 * Author: tandingbo
 * CreateTime: 2017-08-04 14:34
 */
@Service
@Repository("certificateService")
public class DefaultCertificateService implements CertificateService {

    @Autowired
    private CertificateDao certificateDao;

    @Override
    public List<Certificate> selectAll() {
        return certificateDao.queryAllList();
    }
}
