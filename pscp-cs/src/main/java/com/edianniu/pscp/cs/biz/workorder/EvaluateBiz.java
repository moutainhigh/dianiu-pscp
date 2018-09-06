package com.edianniu.pscp.cs.biz.workorder;

import com.edianniu.pscp.cs.bean.request.workorder.EvaluateRequest;
import com.edianniu.pscp.cs.bean.response.workorder.EvaluateResponse;
import com.edianniu.pscp.cs.bean.workorder.EvaluateReqData;
import com.edianniu.pscp.cs.bean.workorder.EvaluateResult;
import com.edianniu.pscp.cs.biz.BaseBiz;
import com.edianniu.pscp.cs.commons.ResultCode;
import com.edianniu.pscp.cs.service.dubbo.WorkOrderInfoService;
import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
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
 * ClassName: EvaluateBiz
 * Author: tandingbo
 * CreateTime: 2017-08-08 12:11
 */
public class EvaluateBiz extends BaseBiz<EvaluateRequest, EvaluateResponse> {
    private static final Logger logger = LoggerFactory.getLogger(EvaluateBiz.class);

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;

    @Autowired
    @Qualifier("workOrderInfoService")
    private WorkOrderInfoService workOrderInfoService;

    @StateTemplate(init = true)
    class RecvReq {
        RecvReq() {
        }

        @OnAccept
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, EvaluateRequest req) {
            logger.debug("EvaluateRequest recv req : {}", req);
            EvaluateResponse resp = (EvaluateResponse) initResponse(ctx, req, new EvaluateResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            EvaluateReqData evaluateReqData = new EvaluateReqData();
            BeanUtils.copyProperties(req, evaluateReqData);
            EvaluateResult result = workOrderInfoService.workOrderEvaluate(evaluateReqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
