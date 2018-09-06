package com.edianniu.pscp.mis.biz.defectrecord;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.defectrecord.CorrectionReqData;
import com.edianniu.pscp.mis.bean.defectrecord.CorrectionResult;
import com.edianniu.pscp.mis.bean.request.defectrecord.CorrectionRequest;
import com.edianniu.pscp.mis.bean.response.defectrecord.CorrectionResponse;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.mis.service.dubbo.WorkOrderDefectRecordInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;

/**
 * ClassName: CorrectionBiz
 * Author: tandingbo
 * CreateTime: 2017-09-12 17:44
 */
public class CorrectionBiz extends BaseBiz<CorrectionRequest, CorrectionResponse> {
    private static final Logger logger = LoggerFactory.getLogger(CorrectionBiz.class);

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;
    @Autowired
    @Qualifier("workOrderDefectRecordInfoService")
    private WorkOrderDefectRecordInfoService workOrderDefectRecordInfoService;

    @StateTemplate(init = true)
    class RecvReq {
        RecvReq() {
        }

        @OnAccept
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, CorrectionRequest req) {
            logger.debug("CorrectionRequest recv req : {}", req);
            CorrectionResponse resp = (CorrectionResponse) initResponse(ctx, req, new CorrectionResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            CorrectionReqData reqData = new CorrectionReqData();
            BeanUtils.copyProperties(req, reqData);
            CorrectionResult result = workOrderDefectRecordInfoService.correction(reqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
