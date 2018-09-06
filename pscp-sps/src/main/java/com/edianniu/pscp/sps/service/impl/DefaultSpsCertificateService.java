package com.edianniu.pscp.sps.service.impl;

import com.edianniu.pscp.sps.bean.certificate.vo.CertificateVO;
import com.edianniu.pscp.sps.dao.SpsCertificateDao;
import com.edianniu.pscp.sps.domain.Certificate;
import com.edianniu.pscp.sps.service.SpsCertificateService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: DefaultSpsCertificateService
 * Author: tandingbo
 * CreateTime: 2017-05-17 14:20
 */
@Service
@Repository("spsCertificateService")
public class DefaultSpsCertificateService implements SpsCertificateService {

    @Autowired
    private SpsCertificateDao spsCertificateDao;

    @Override
    public List<CertificateVO> selectAllCertificateVO() {
        List<CertificateVO> certificateVOList = new ArrayList<>();
        List<Certificate> certificateList = spsCertificateDao.queryAllList();
        if (CollectionUtils.isNotEmpty(certificateList)) {
            for (Certificate certificate : certificateList) {
                CertificateVO certificateVO = new CertificateVO();
                certificateVO.setId(certificate.getId());
                certificateVO.setName(certificate.getName());
                certificateVOList.add(certificateVO);
            }
        }
        return certificateVOList;
    }

    @Override
    public List<Certificate> selectAll() {
        return spsCertificateDao.queryAllList();
    }
}
