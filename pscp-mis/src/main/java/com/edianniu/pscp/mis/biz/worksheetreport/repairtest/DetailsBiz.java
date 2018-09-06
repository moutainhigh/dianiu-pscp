package com.edianniu.pscp.mis.biz.worksheetreport.repairtest;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.request.worksheetreport.repairtest.DetailsRequest;
import com.edianniu.pscp.mis.bean.response.worksheetreport.repairtest.DetailsResponse;
import com.edianniu.pscp.mis.bean.worksheetreport.ReportType;
import com.edianniu.pscp.mis.bean.worksheetreport.DetailsReqData;
import com.edianniu.pscp.mis.bean.worksheetreport.DetailsResult;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.mis.service.dubbo.WorkOrderReportInfoService;
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
 * ClassName: DetailsBiz
 * Author: tandingbo
 * CreateTime: 2017-09-13 16:01
 */
public class DetailsBiz extends BaseBiz<DetailsRequest, DetailsResponse> {
    private static final Logger logger = LoggerFactory.getLogger(DetailsBiz.class);

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;
    @Autowired
    @Qualifier("workOrderReportInfoService")
    private WorkOrderReportInfoService workOrderReportInfoService;

    @StateTemplate(init = true)
    class RecvReq {
        RecvReq() {
        }

        @OnAccept
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, DetailsRequest req) {
            logger.debug("DetailsRequest recv req : {}", req);
            DetailsResponse resp = (DetailsResponse) initResponse(ctx, req, new DetailsResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            DetailsReqData reqData = new DetailsReqData();
            BeanUtils.copyProperties(req, reqData);
            reqData.setType(ReportType.REPAIR_TEST.getValue());
            DetailsResult result = workOrderReportInfoService.getWorkOrderReportById(reqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
