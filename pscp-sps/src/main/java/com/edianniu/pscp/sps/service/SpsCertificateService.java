package com.edianniu.pscp.sps.service;

import com.edianniu.pscp.sps.bean.certificate.vo.CertificateVO;
import com.edianniu.pscp.sps.domain.Certificate;

import java.util.List;

/**
 * ClassName: SpsCertificateService
 * Author: tandingbo
 * CreateTime: 2017-05-17 14:20
 */
public interface SpsCertificateService {
    List<CertificateVO> selectAllCertificateVO();

    List<Certificate> selectAll();
}
