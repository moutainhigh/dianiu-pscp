package com.edianniu.pscp.sps.biz.socialworkorder;
import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.sps.bean.request.socialworkorder.LiquidateRequest;
import com.edianniu.pscp.sps.bean.response.socialworkorder.LiquidateResponse;
import com.edianniu.pscp.sps.bean.socialworkorder.liquidate.LiquidateInfo;
import com.edianniu.pscp.sps.bean.socialworkorder.liquidate.LiquidateReqData;
import com.edianniu.pscp.sps.bean.socialworkorder.liquidate.LiquidateResult;
import com.edianniu.pscp.sps.biz.BaseBiz;
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

import java.util.ArrayList;
import java.util.List;

/**
 * 社会电工工单结算
 * ClassName: LiquidateBiz
 * Author: tandingbo
 * CreateTime: 2017-06-30 14:40
 */
public class LiquidateBiz extends BaseBiz<LiquidateRequest, LiquidateResponse> {
    private static final Logger logger = LoggerFactory.getLogger(LiquidateBiz.class);

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
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, LiquidateRequest req) {
            logger.debug("LiquidateRequest recv req : {}", req);
            LiquidateResponse resp = (LiquidateResponse) initResponse(ctx, req, new LiquidateResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken(),"/sps/socialworkorder/liquidate");
            if (!isLogin.isSuccess()) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            List<LiquidateInfo> liquidateInfoList = new ArrayList<>();
            LiquidateInfo liquidateInfo = new LiquidateInfo();
            BeanUtils.copyProperties(req, liquidateInfo);
            liquidateInfoList.add(liquidateInfo);

            LiquidateReqData liquidateReqData = new LiquidateReqData();
            liquidateReqData.setUid(req.getUid());
            liquidateReqData.setSocialWorkOrderId(req.getSocialWorkOrderId());
            liquidateReqData.setLiquidateInfoList(liquidateInfoList);

            LiquidateResult result = socialWorkOrderInfoService.liquidate(liquidateReqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
