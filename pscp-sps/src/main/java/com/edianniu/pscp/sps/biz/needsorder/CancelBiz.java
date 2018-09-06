package com.edianniu.pscp.sps.biz.needsorder;

import com.edianniu.pscp.cs.bean.needsorder.CancelReqData;
import com.edianniu.pscp.cs.bean.needsorder.CancelResult;
import com.edianniu.pscp.cs.service.dubbo.CustomerNeedsOrderInfoService;
import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.sps.bean.request.needsorder.CancelRequest;
import com.edianniu.pscp.sps.bean.response.needsorder.CancelResponse;
import com.edianniu.pscp.sps.biz.BaseBiz;
import com.edianniu.pscp.sps.commons.ResultCode;
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
 * ClassName: CancelBiz
 * Author: tandingbo
 * CreateTime: 2017-09-26 11:29
 */
public class CancelBiz extends BaseBiz<CancelRequest, CancelResponse> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;
    @Autowired
    @Qualifier("customerNeedsOrderInfoService")
    private CustomerNeedsOrderInfoService customerNeedsOrderInfoService;

    @StateTemplate(init = true)
    class RecvReq {
        RecvReq() {
        }

        @OnAccept
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, CancelRequest req) {
            logger.debug("CancelRequest recv req : {}", req);
            CancelResponse resp = (CancelResponse) initResponse(ctx, req, new CancelResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken(),"/needs/facilitator/cancel");
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            CancelReqData cancelReqData = new CancelReqData();
            BeanUtils.copyProperties(req, cancelReqData);
            CancelResult result = customerNeedsOrderInfoService.cancel(cancelReqData);
            BeanUtils.copyProperties(result, resp);
            return BaseBiz.SendResp.class;
        }
    }
}
