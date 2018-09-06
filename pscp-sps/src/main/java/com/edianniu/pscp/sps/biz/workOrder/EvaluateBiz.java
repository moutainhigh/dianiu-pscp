package com.edianniu.pscp.sps.biz.workOrder;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.sps.bean.request.workOrder.EvaluateRequest;
import com.edianniu.pscp.sps.bean.response.workOrder.EvaluateResponse;
import com.edianniu.pscp.sps.bean.workorder.evaluate.ListReqData;
import com.edianniu.pscp.sps.bean.workorder.evaluate.ListResult;
import com.edianniu.pscp.sps.biz.BaseBiz;
import com.edianniu.pscp.sps.commons.ResultCode;
import com.edianniu.pscp.sps.service.dubbo.WorkOrderEvaluateInfoService;
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
 * 工单评价信息接口
 * ClassName: EvaluateBiz
 * Author: tandingbo
 * CreateTime: 2017-06-29 10:05
 */
public class EvaluateBiz extends BaseBiz<EvaluateRequest, EvaluateResponse> {
    private static final Logger logger = LoggerFactory.getLogger(EvaluateBiz.class);

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;
    @Autowired
    @Qualifier("workOrderEvaluateInfoService")
    private WorkOrderEvaluateInfoService workOrderEvaluateInfoService;

    @StateTemplate(init = true)
    class RecvReq {
        RecvReq() {
        }

        @OnAccept
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, EvaluateRequest req) {
            logger.debug("EvaluateResponse recv req : {}", req);
            EvaluateResponse resp = (EvaluateResponse) initResponse(ctx, req, new EvaluateResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            ListReqData listReqData = new ListReqData();
            BeanUtils.copyProperties(req, listReqData);
            ListResult result = workOrderEvaluateInfoService.list(listReqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
