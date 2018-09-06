package com.edianniu.pscp.mis.biz.electricianworkorder;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.electricianworkorder.EndWorkReqData;
import com.edianniu.pscp.mis.bean.electricianworkorder.EndWorkResult;
import com.edianniu.pscp.mis.bean.request.electricianworkorder.EndWorkRequest;
import com.edianniu.pscp.mis.bean.response.electricianworkorder.EndWorkResponse;
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
 * 电工工单结束工作接口
 * ClassName: EndWorkBiz
 * Author: tandingbo
 * CreateTime: 2017-04-13 18:07
 */
public class EndWorkBiz extends BaseBiz<EndWorkRequest, EndWorkResponse> {
    private static final Logger logger = LoggerFactory.getLogger(EndWorkBiz.class);

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
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, EndWorkRequest req) {
            logger.debug("EndWorkRequest recv req : {}", req);

            EndWorkResponse resp = (EndWorkResponse) initResponse(ctx, req, new EndWorkResponse());
//            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
//            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
//                resp.setResultCode(isLogin.getResultCode());
//                resp.setResultMessage(isLogin.getResultMessage());
//                return SendResp.class;
//            }

            EndWorkReqData endWorkReqData = new EndWorkReqData();
            endWorkReqData.setUid(req.getUid());
            endWorkReqData.setOrderId(req.getOrderId());
            endWorkReqData.setEndTime(req.getEndTime());
            EndWorkResult result = electricianWorkOrderInfoService.endWork(endWorkReqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }

}
