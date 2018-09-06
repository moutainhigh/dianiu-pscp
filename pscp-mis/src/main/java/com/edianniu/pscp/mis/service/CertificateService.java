package com.edianniu.pscp.mis.service;

import com.edianniu.pscp.mis.domain.Certificate;

import java.util.List;

/**
 * ClassName: CertificateService
 * Author: tandingbo
 * CreateTime: 2017-08-04 14:33
 */
public interface CertificateService {
    List<Certificate> selectAll();
}
