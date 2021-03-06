package com.edianniu.pscp.sps.biz.needsorder;

import com.edianniu.pscp.cs.bean.needsorder.OfferReqData;
import com.edianniu.pscp.cs.bean.needsorder.OfferResult;
import com.edianniu.pscp.cs.service.dubbo.CustomerNeedsOrderInfoService;
import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.sps.bean.request.needsorder.OfferRequest;
import com.edianniu.pscp.sps.bean.response.needsorder.OfferResponse;
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
 * ClassName: OfferBiz
 * Author: tandingbo
 * CreateTime: 2017-09-26 14:08
 */
public class OfferBiz extends BaseBiz<OfferRequest, OfferResponse> {
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
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, OfferRequest req) {
            logger.debug("OfferRequest recv req : {}", req);
            OfferResponse resp = (OfferResponse) initResponse(ctx, req, new OfferResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken(),"/needs/facilitator/offer");
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return BaseBiz.SendResp.class;
            }

            OfferReqData offerReqData = new OfferReqData();
            BeanUtils.copyProperties(req, offerReqData);
            OfferResult result = customerNeedsOrderInfoService.offer(offerReqData);
            BeanUtils.copyProperties(result, resp);
            return BaseBiz.SendResp.class;
        }
    }
}