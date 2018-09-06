package com.edianniu.pscp.sps.biz.socialworkorder;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.sps.bean.certificate.CertificateVOResult;
import com.edianniu.pscp.sps.bean.certificate.vo.CertificateVO;
import com.edianniu.pscp.sps.bean.request.socialworkorder.CertificateRequest;
import com.edianniu.pscp.sps.bean.response.socialworkorder.CertificateResponse;
import com.edianniu.pscp.sps.biz.BaseBiz;
import com.edianniu.pscp.sps.commons.ResultCode;
import com.edianniu.pscp.sps.service.SpsCertificateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;

import java.util.List;

/**
 * 社会电工资质列表接口
 * ClassName: CertificateBiz
 * Author: tandingbo
 * CreateTime: 2017-07-11 11:14
 */
public class CertificateBiz extends BaseBiz<CertificateRequest, CertificateResponse> {
    private static final Logger logger = LoggerFactory.getLogger(CertificateBiz.class);

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;
    @Autowired
    @Qualifier("spsCertificateService")
    private SpsCertificateService spsCertificateService;

    @StateTemplate(init = true)
    class RecvReq {
        RecvReq() {
        }

        @OnAccept
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, CertificateRequest req) {
            logger.debug("CertificateRequest recv req : {}", req);
            CertificateResponse resp = (CertificateResponse) initResponse(ctx, req, new CertificateResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            List<CertificateVO> certificateVOList = spsCertificateService.selectAllCertificateVO();
            CertificateVOResult result = new CertificateVOResult();
            result.setCertificateList(certificateVOList);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
