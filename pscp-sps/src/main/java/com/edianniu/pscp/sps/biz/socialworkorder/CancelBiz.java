package com.edianniu.pscp.sps.biz.socialworkorder;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.sps.bean.request.socialworkorder.CancelRequest;
import com.edianniu.pscp.sps.bean.response.socialworkorder.CancelResponse;
import com.edianniu.pscp.sps.bean.socialworkorder.cancel.CancelReqData;
import com.edianniu.pscp.sps.bean.socialworkorder.cancel.CancelResult;
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
 * 社会工单取消
 * ClassName: CancelBiz
 * Author: tandingbo
 * CreateTime: 2017-07-11 10:33
 */
public class CancelBiz extends BaseBiz<CancelRequest, CancelResponse> {
    private static final Logger logger = LoggerFactory.getLogger(CancelBiz.class);

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
            BeanUtils.copyProperties(req, cancelReqData);
            CancelResult result = socialWorkOrderInfoService.cancel(cancelReqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
