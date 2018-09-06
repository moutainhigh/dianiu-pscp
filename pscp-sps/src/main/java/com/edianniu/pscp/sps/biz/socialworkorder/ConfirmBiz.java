package com.edianniu.pscp.sps.biz.socialworkorder;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.sps.bean.request.socialworkorder.ConfirmRequest;
import com.edianniu.pscp.sps.bean.response.socialworkorder.ConfirmResponse;
import com.edianniu.pscp.sps.bean.socialworkorder.confirm.ConfirmReqData;
import com.edianniu.pscp.sps.bean.socialworkorder.confirm.ConfirmResult;
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
 * 社会电工工单确认接口
 * ClassName: ConfirmBiz
 * Author: tandingbo
 * CreateTime: 2017-06-29 16:38
 */
public class ConfirmBiz extends BaseBiz<ConfirmRequest, ConfirmResponse> {
    private static final Logger logger = LoggerFactory.getLogger(ConfirmBiz.class);

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
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, ConfirmRequest req) {
            logger.debug("ConfirmRequest recv req : {}", req);
            ConfirmResponse resp = (ConfirmResponse) initResponse(ctx, req, new ConfirmResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken(),"/sps/socialworkorder/confirm");
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            ConfirmReqData confirmReqData = new ConfirmReqData();
            BeanUtils.copyProperties(req, confirmReqData);
            ConfirmResult result = socialWorkOrderInfoService.confirm(confirmReqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
