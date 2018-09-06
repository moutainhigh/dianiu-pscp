package com.edianniu.pscp.sps.bean.response.socialworkorder;

import com.edianniu.pscp.sps.bean.certificate.vo.CertificateVO;
import com.edianniu.pscp.sps.bean.response.BaseResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

import java.util.List;

/**
 * ClassName: CertificateResponse
 * Author: tandingbo
 * CreateTime: 2017-07-11 11:16
 */
@JSONMessage(messageCode = 2002109)
public final class CertificateResponse extends BaseResponse {

    private List<CertificateVO> certificateList;

    public List<CertificateVO> getCertificateList() {
        return certificateList;
    }

    public void setCertificateList(List<CertificateVO> certificateList) {
        this.certificateList = certificateList;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
