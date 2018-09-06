package com.edianniu.pscp.mis.biz.electricianworkorder;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.electricianworkorder.CancelReqData;
import com.edianniu.pscp.mis.bean.electricianworkorder.CancelResult;
import com.edianniu.pscp.mis.bean.request.electricianworkorder.CancelRequest;
import com.edianniu.pscp.mis.bean.response.electricianworkorder.CancelResponse;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.ElectricianWorkOrderInfoService;
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
 * 电工工单取消接口
 * ClassName: CancelBiz
 * Author: tandingbo
 * CreateTime: 2017-04-13 18:05
 */
public class CancelBiz extends BaseBiz<CancelRequest, CancelResponse> {
    private static final Logger logger = LoggerFactory.getLogger(CancelBiz.class);

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;

    @Autowired
    @Qualifier("electricianWorkOrderInfoService")
    private ElectricianWorkOrderInfoService electricianWorkOrderInfoService;

    @StateTemplate(init = true)
    class RecvReq {
        RecvReq() {
        }

        @OnAccept
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, CancelRequest req) {
            logger.debug("CancelRequest recv req : {}", req);
            CancelResponse resp = (CancelResponse) initResponse(ctx, req, new CancelResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            CancelReqData cancelReqData = new CancelReqData();
            cancelReqData.setUid(req.getUid());
            cancelReqData.setOrderId(req.getOrderId());
            CancelResult result = electricianWorkOrderInfoService.cancelWorkOrder(cancelReqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }

}
