package com.edianniu.pscp.mis.biz.worksheetreport.patrol;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.request.worksheetreport.patrol.ListRequest;
import com.edianniu.pscp.mis.bean.response.worksheetreport.patrol.ListResponse;
import com.edianniu.pscp.mis.bean.worksheetreport.ListReqData;
import com.edianniu.pscp.mis.bean.worksheetreport.ListResult;
import com.edianniu.pscp.mis.bean.worksheetreport.ReportType;
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
 * ClassName: ListBiz
 * Author: tandingbo
 * CreateTime: 2017-09-13 17:00
 */
public class ListBiz extends BaseBiz<ListRequest, ListResponse> {
    private static final Logger logger = LoggerFactory.getLogger(ListBiz.class);

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
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, ListRequest req) {
            logger.debug("ListRequest recv req : {}", req);
            ListResponse resp = (ListResponse) initResponse(ctx, req, new ListResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            ListReqData reqData = new ListReqData();
            BeanUtils.copyProperties(req, reqData);
            reqData.setType(ReportType.PATROL.getValue());
            ListResult result = workOrderReportInfoService.pageListReport(reqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}