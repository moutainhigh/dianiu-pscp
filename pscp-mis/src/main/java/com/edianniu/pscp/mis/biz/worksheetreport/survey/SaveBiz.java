package com.edianniu.pscp.mis.biz.worksheetreport.survey;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.request.worksheetreport.survey.SaveRequest;
import com.edianniu.pscp.mis.bean.response.worksheetreport.survey.SaveResponse;
import com.edianniu.pscp.mis.bean.worksheetreport.ReportType;
import com.edianniu.pscp.mis.bean.worksheetreport.SaveReqData;
import com.edianniu.pscp.mis.bean.worksheetreport.SaveResult;
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
 * 勘察报告保存接口
 *
 * @author tandingbo
 * @create 2017-11-09 09:40
 */
public class SaveBiz extends BaseBiz<SaveRequest, SaveResponse> {
    private static final Logger logger = LoggerFactory.getLogger(SaveBiz.class);

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
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, SaveRequest req) {
            logger.debug("ListRequest recv req : {}", req);
            SaveResponse resp = (SaveResponse) initResponse(ctx, req, new SaveResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            SaveReqData reqData = new SaveReqData();
            BeanUtils.copyProperties(req, reqData);
            reqData.setType(ReportType.SURVEY.getValue());
            SaveResult result = workOrderReportInfoService.saveSurveyReport(reqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
