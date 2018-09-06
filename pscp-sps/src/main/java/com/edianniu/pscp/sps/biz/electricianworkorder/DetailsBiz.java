package com.edianniu.pscp.sps.biz.electricianworkorder;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.sps.bean.request.electricianworkorder.DetailsRequest;
import com.edianniu.pscp.sps.bean.response.electricianworkorder.DetailsResponse;
import com.edianniu.pscp.sps.bean.workorder.electrician.DetailReqData;
import com.edianniu.pscp.sps.bean.workorder.electrician.DetailResult;
import com.edianniu.pscp.sps.biz.BaseBiz;
import com.edianniu.pscp.sps.commons.ResultCode;
import com.edianniu.pscp.sps.service.dubbo.ElectricianWorkOrderInfoService;
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
 * 电工工单详情接口
 * ClassName: DetailsBiz
 * Author: tandingbo
 * CreateTime: 2017-07-11 15:11
 */
public class DetailsBiz extends BaseBiz<DetailsRequest, DetailsResponse> {
    private static final Logger logger = LoggerFactory.getLogger(DetailsBiz.class);

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
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, DetailsRequest req) {
            logger.debug("DetailsRequest recv req : {}", req);
            DetailsResponse resp = (DetailsResponse) initResponse(ctx, req, new DetailsResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken(),"/sps/electricianworkorder/details");
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            DetailReqData detailReqData = new DetailReqData();
            BeanUtils.copyProperties(req, detailReqData);
            DetailResult result = electricianWorkOrderInfoService.detail(detailReqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
