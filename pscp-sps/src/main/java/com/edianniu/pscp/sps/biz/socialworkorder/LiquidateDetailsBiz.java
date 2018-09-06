package com.edianniu.pscp.sps.biz.socialworkorder;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.sps.bean.request.socialworkorder.LiquidateDetailsRequest;
import com.edianniu.pscp.sps.bean.response.socialworkorder.LiquidateDetailsResponse;
import com.edianniu.pscp.sps.bean.socialworkorder.liquidate.LiquidateDetailsReqData;
import com.edianniu.pscp.sps.bean.socialworkorder.liquidate.LiquidateDetailsResult;
import com.edianniu.pscp.sps.biz.BaseBiz;
import com.edianniu.pscp.sps.commons.ResultCode;
import com.edianniu.pscp.sps.service.dubbo.SocialWorkOrderInfoService;
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
 * 社会电工工单结算信息接口
 * ClassName: LiquidateDetailsBiz
 * Author: tandingbo
 * CreateTime: 2017-06-30 10:33
 */
public class LiquidateDetailsBiz extends BaseBiz<LiquidateDetailsRequest, LiquidateDetailsResponse> {
    private static final Logger logger = LoggerFactory.getLogger(LiquidateDetailsBiz.class);

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;
    @Autowired
    @Qualifier("socialWorkOrderInfoService")
    private SocialWorkOrderInfoService socialWorkOrderInfoService;

    @StateTemplate(init = true)
    class RecvReq {
        RecvReq() {
        }

        @OnAccept
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, LiquidateDetailsRequest req) {
            logger.debug("LiquidateDetailsRequest recv req : {}", req);
            LiquidateDetailsResponse resp = (LiquidateDetailsResponse) initResponse(ctx, req, new LiquidateDetailsResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken(),"/sps/socialworkorder/liquidate/details");
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            LiquidateDetailsReqData liquidateDetailsReqData = new LiquidateDetailsReqData();
            BeanUtils.copyProperties(req, liquidateDetailsReqData);
            LiquidateDetailsResult result = socialWorkOrderInfoService.liquidateDetailsForElectrician(liquidateDetailsReqData);
            BeanUtils.copyProperties(result, resp);

            return SendResp.class;
        }
    }

}
