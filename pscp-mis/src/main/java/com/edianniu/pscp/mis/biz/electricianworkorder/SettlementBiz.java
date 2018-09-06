package com.edianniu.pscp.mis.biz.electricianworkorder;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.electricianworkorder.SettlementReqData;
import com.edianniu.pscp.mis.bean.electricianworkorder.SettlementResult;
import com.edianniu.pscp.mis.bean.request.electricianworkorder.SettlementRequest;
import com.edianniu.pscp.mis.bean.response.electricianworkorder.SettlementResponse;
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
 * 社会电工结算
 * ClassName: SettlementBiz
 * Author: tandingbo
 * CreateTime: 2017-05-10 15:50
 */
public class SettlementBiz extends BaseBiz<SettlementRequest, SettlementResponse> {
    private static final Logger logger = LoggerFactory.getLogger(SettlementBiz.class);

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
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, SettlementRequest req) {
            logger.debug("SettlementRequest recv req : {}", req);
            SettlementResponse resp = (SettlementResponse) initResponse(ctx, req, new SettlementResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            SettlementReqData settlementReqData = new SettlementReqData();
            settlementReqData.setUid(req.getUid());
            settlementReqData.setAmount(req.getAmount());
            settlementReqData.setOrderId(req.getOrderId());
            SettlementResult result = electricianWorkOrderInfoService.settlementWorkOrder(settlementReqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
