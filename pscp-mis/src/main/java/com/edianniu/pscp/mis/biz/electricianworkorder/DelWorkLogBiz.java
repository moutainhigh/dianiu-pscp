package com.edianniu.pscp.mis.biz.electricianworkorder;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.electricianworkorder.DelWorkLogReqData;
import com.edianniu.pscp.mis.bean.electricianworkorder.DelWorkLogResult;
import com.edianniu.pscp.mis.bean.request.electricianworkorder.DelWorkLogRequest;
import com.edianniu.pscp.mis.bean.response.electricianworkorder.DelWorkLogResponse;
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
 * 电工工单删除工作记录接口
 * ClassName: DelWorkLogBiz
 * Author: tandingbo
 * CreateTime: 2017-04-13 18:10
 */
public class DelWorkLogBiz extends BaseBiz<DelWorkLogRequest, DelWorkLogResponse> {
    private static final Logger logger = LoggerFactory.getLogger(DelWorkLogBiz.class);

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
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, DelWorkLogRequest req) {
            logger.debug("DelWorkLogRequest recv req : {}", req);

            DelWorkLogResponse resp = (DelWorkLogResponse) initResponse(ctx, req, new DelWorkLogResponse());
            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            DelWorkLogReqData delWorkLogReqData = new DelWorkLogReqData();
            delWorkLogReqData.setUid(req.getUid());
            delWorkLogReqData.setOrderId(req.getOrderId());
            delWorkLogReqData.setWorkLogId(req.getWorkLogId());
            DelWorkLogResult result = electricianWorkOrderInfoService.delWorkLog(delWorkLogReqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }

}
