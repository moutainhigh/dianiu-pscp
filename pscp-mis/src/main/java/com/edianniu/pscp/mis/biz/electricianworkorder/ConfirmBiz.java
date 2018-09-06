package com.edianniu.pscp.mis.biz.electricianworkorder;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.electricianworkorder.ConfirmReqData;
import com.edianniu.pscp.mis.bean.electricianworkorder.ConfirmResult;
import com.edianniu.pscp.mis.bean.request.electricianworkorder.ConfirmRequest;
import com.edianniu.pscp.mis.bean.response.electricianworkorder.ConfirmResponse;
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
 * 电工工单确认接口
 * ClassName: ConfirmBiz
 * Author: tandingbo
 * CreateTime: 2017-04-13 18:03
 */
public class ConfirmBiz extends BaseBiz<ConfirmRequest, ConfirmResponse> {
    private static final Logger logger = LoggerFactory.getLogger(ConfirmBiz.class);

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
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, ConfirmRequest req) {
            logger.debug("ConfirmRequest recv req : {}", req);

            ConfirmResponse resp = (ConfirmResponse) initResponse(ctx, req, new ConfirmResponse());
            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            ConfirmReqData confirmReqData = new ConfirmReqData();
            confirmReqData.setUid(req.getUid());
            confirmReqData.setOrderId(req.getOrderId());
            ConfirmResult result = electricianWorkOrderInfoService.workOrdersConfirm(confirmReqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }

}
