package com.edianniu.pscp.mis.biz.history;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.history.FacilitatorHistoryReqData;
import com.edianniu.pscp.mis.bean.history.FacilitatorHistoryResult;
import com.edianniu.pscp.mis.bean.request.history.FacilitatorHistoryRequest;
import com.edianniu.pscp.mis.bean.response.history.FacilitatorHistoryResponse;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.CompanyElectricianInfoService;
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
 * ClassName: FacilitatorHistoryListBiz
 * Author: tandingbo
 * CreateTime: 2017-10-30 10:53
 */
public class FacilitatorHistoryListBiz extends BaseBiz<FacilitatorHistoryRequest, FacilitatorHistoryResponse> {
    private static final Logger logger = LoggerFactory.getLogger(FacilitatorHistoryListBiz.class);

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;
    @Autowired
    @Qualifier("companyElectricianInfoService")
    private CompanyElectricianInfoService companyElectricianInfoService;

    @StateTemplate(init = true)
    class RecvReq {
        RecvReq() {
        }

        @OnAccept
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, FacilitatorHistoryRequest req) {
            logger.debug("AddWorkLogRequest recv req : {}", req);
            FacilitatorHistoryResponse resp = (FacilitatorHistoryResponse) initResponse(ctx, req, new FacilitatorHistoryResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            FacilitatorHistoryReqData facilitatorHistoryReqData = new FacilitatorHistoryReqData();
            BeanUtils.copyProperties(req, facilitatorHistoryReqData);
            FacilitatorHistoryResult result = companyElectricianInfoService.queryFacilitatorHistory(facilitatorHistoryReqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
