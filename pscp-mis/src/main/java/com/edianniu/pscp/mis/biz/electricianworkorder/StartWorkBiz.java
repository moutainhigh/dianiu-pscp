package com.edianniu.pscp.mis.biz.electricianworkorder;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.electricianworkorder.StartWorkReqData;
import com.edianniu.pscp.mis.bean.electricianworkorder.StartWorkResult;
import com.edianniu.pscp.mis.bean.request.electricianworkorder.StartWorkRequest;
import com.edianniu.pscp.mis.bean.response.electricianworkorder.StartWorkResponse;
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
 * 电工工单开始工作接口
 * ClassName: StartWorkBiz
 * Author: tandingbo
 * CreateTime: 2017-04-13 18:06
 */
public class StartWorkBiz extends BaseBiz<StartWorkRequest, StartWorkResponse> {
    private static final Logger logger = LoggerFactory.getLogger(StartWorkBiz.class);

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
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, StartWorkRequest req) {
            logger.debug("StartWorkRequest recv req : {}", req);

            StartWorkResponse resp = (StartWorkResponse) initResponse(ctx, req, new StartWorkResponse());
            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            StartWorkReqData startWorkReqData = new StartWorkReqData();
            startWorkReqData.setUid(req.getUid());
            startWorkReqData.setOrderId(req.getOrderId());
            startWorkReqData.setStartTime(req.getStartTime());
            StartWorkResult result = electricianWorkOrderInfoService.startWork(startWorkReqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
