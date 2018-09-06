package com.edianniu.pscp.sps.biz.needsorder;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.sps.bean.needsorder.InitDataReqData;
import com.edianniu.pscp.sps.bean.needsorder.InitDataResult;
import com.edianniu.pscp.sps.bean.request.needsorder.InitDataRequest;
import com.edianniu.pscp.sps.bean.response.needsorder.InitDataResponse;
import com.edianniu.pscp.sps.biz.BaseBiz;
import com.edianniu.pscp.sps.commons.ResultCode;
import com.edianniu.pscp.sps.service.dubbo.NeedsOrderInfoService;
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
 * ClassName: InitDataBiz
 * Author: tandingbo
 * CreateTime: 2017-09-26 15:12
 */
public class InitDataBiz extends BaseBiz<InitDataRequest, InitDataResponse> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;
    @Autowired
    @Qualifier("needsOrderInfoService")
    private NeedsOrderInfoService needsOrderInfoService;

    @StateTemplate(init = true)
    class RecvReq {
        RecvReq() {
        }

        @OnAccept
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, InitDataRequest req) {
            logger.debug("InitDataRequest recv req : {}", req);
            InitDataResponse resp = (InitDataResponse) initResponse(ctx, req, new InitDataResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken(),"/order/facilitator/initdata");
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            InitDataReqData initDataReqData = new InitDataReqData();
            BeanUtils.copyProperties(req, initDataReqData);
            InitDataResult result = needsOrderInfoService.initData(initDataReqData);
            BeanUtils.copyProperties(result, resp);
            return BaseBiz.SendResp.class;
        }
    }
}
