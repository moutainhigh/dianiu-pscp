package com.edianniu.pscp.sps.biz.workOrder;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.sps.bean.request.workOrder.WorkLogRequest;
import com.edianniu.pscp.sps.bean.response.workOrder.WorkLogResponse;
import com.edianniu.pscp.sps.bean.workorder.worklog.ListReqData;
import com.edianniu.pscp.sps.bean.workorder.worklog.ListResult;
import com.edianniu.pscp.sps.biz.BaseBiz;
import com.edianniu.pscp.sps.commons.ResultCode;
import com.edianniu.pscp.sps.service.dubbo.ElectricianWorkLogInfoService;
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
 * 工单工作记录接口
 * ClassName: WorkLogBiz
 * Author: tandingbo
 * CreateTime: 2017-06-28 17:36
 */
public class WorkLogBiz extends BaseBiz<WorkLogRequest, WorkLogResponse> {
    private static final Logger logger = LoggerFactory.getLogger(WorkLogBiz.class);

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;
    @Autowired
    @Qualifier("electricianWorkLogInfoService")
    private ElectricianWorkLogInfoService electricianWorkLogInfoService;

    @StateTemplate(init = true)
    class RecvReq {
        RecvReq() {
        }

        @OnAccept
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, WorkLogRequest req) {
            logger.debug("WorkLogResponse recv req : {}", req);
            WorkLogResponse resp = (WorkLogResponse) initResponse(ctx, req, new WorkLogResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            ListReqData listReqData = new ListReqData();
            BeanUtils.copyProperties(req, listReqData);
            listReqData.setSource("APP");
            ListResult result = electricianWorkLogInfoService.list(listReqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
