package com.edianniu.pscp.mis.biz.electricianworkorder;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.electricianworkorder.ListWorkLogReqData;
import com.edianniu.pscp.mis.bean.electricianworkorder.ListWorkLogResult;
import com.edianniu.pscp.mis.bean.request.electricianworkorder.ListWorkLogRequest;
import com.edianniu.pscp.mis.bean.response.electricianworkorder.ListWorkLogResponse;
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
 * 电工工单工作记录列表接口
 * ClassName: ListWorkLogBiz
 * Author: tandingbo
 * CreateTime: 2017-04-13 18:12
 */
public class ListWorkLogBiz extends BaseBiz<ListWorkLogRequest, ListWorkLogResponse> {
    private static final Logger logger = LoggerFactory.getLogger(ListWorkLogBiz.class);

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
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, ListWorkLogRequest req) {
            logger.debug("ListWorkLogRequest recv req : {}", req);

            ListWorkLogResponse resp = (ListWorkLogResponse) initResponse(ctx, req, new ListWorkLogResponse());
            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            ListWorkLogReqData listWorkLogReqData = new ListWorkLogReqData();
            listWorkLogReqData.setUid(req.getUid());
            listWorkLogReqData.setOrderId(req.getOrderId());
            ListWorkLogResult result = electricianWorkOrderInfoService.listWorkLog(listWorkLogReqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
