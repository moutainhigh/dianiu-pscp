package com.edianniu.pscp.sps.biz.workOrder;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.sps.bean.request.workOrder.WorkLogDetailRequest;
import com.edianniu.pscp.sps.bean.response.workOrder.WorkLogDetailResponse;
import com.edianniu.pscp.sps.bean.workorder.worklog.DetailReqData;
import com.edianniu.pscp.sps.bean.workorder.worklog.DetailResult;
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
 * 工单工作记录详情接口
 */
public class WorkLogDetailBiz extends BaseBiz<WorkLogDetailRequest, WorkLogDetailResponse> {
    private static final Logger logger = LoggerFactory.getLogger(WorkLogDetailBiz.class);

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
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, WorkLogDetailRequest req) {
            logger.debug("WorkLogResponse recv req : {}", req);
            WorkLogDetailResponse resp = (WorkLogDetailResponse) initResponse(ctx, req, new WorkLogDetailResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            DetailReqData detailReqData = new DetailReqData();
            BeanUtils.copyProperties(req, detailReqData);
            detailReqData.setSource("APP");
            DetailResult result = electricianWorkLogInfoService.detail(detailReqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
