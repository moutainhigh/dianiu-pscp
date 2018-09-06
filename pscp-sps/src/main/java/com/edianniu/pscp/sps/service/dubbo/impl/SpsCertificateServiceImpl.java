package com.edianniu.pscp.sps.service.dubbo.impl;

import com.edianniu.pscp.sps.bean.certificate.CertificateVOResult;
import com.edianniu.pscp.sps.bean.certificate.vo.CertificateVO;
import com.edianniu.pscp.sps.commons.ResultCode;
import com.edianniu.pscp.sps.service.SpsCertificateService;
import com.edianniu.pscp.sps.service.dubbo.SpsCertificateInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: SpsCertificateServiceImpl
 * Author: tandingbo
 * CreateTime: 2017-05-17 14:18
 */
@Service
@Repository("spsCertificateInfoService")
public class SpsCertificateServiceImpl implements SpsCertificateInfoService {
    private static final Logger logger = LoggerFactory.getLogger(SpsCertificateServiceImpl.class);

    @Autowired
    @Qualifier("spsCertificateService")
    private SpsCertificateService spsCertificateService;

    @Override
    public CertificateVOResult selectAllCertificateVO() {
        CertificateVOResult result = new CertificateVOResult();
        try {
            List<CertificateVO> CertificateVOList = spsCertificateService.selectAllCertificateVO();
            result.setCertificateList(CertificateVOList);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("Electrician WorkOrder list:{}", e);
        }
        return result;
    }
}
