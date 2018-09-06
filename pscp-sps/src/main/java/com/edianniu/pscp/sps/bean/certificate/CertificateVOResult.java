package com.edianniu.pscp.sps.bean.certificate;

import com.edianniu.pscp.sps.bean.Result;
import com.edianniu.pscp.sps.bean.certificate.vo.CertificateVO;

import java.util.List;

/**
 * ClassName: CertificateVOResult
 * Author: tandingbo
 * CreateTime: 2017-05-17 14:06
 */
public class CertificateVOResult extends Result {
    private static final long serialVersionUID = 1L;

    private List<CertificateVO> certificateList;

    public List<CertificateVO> getCertificateList() {
        return certificateList;
    }

    public void setCertificateList(List<CertificateVO> certificateList) {
        this.certificateList = certificateList;
    }
}
