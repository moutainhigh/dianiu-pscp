package com.edianniu.pscp.cs.biz.workorder;

import com.edianniu.pscp.cs.bean.request.workorder.DetailsRequest;
import com.edianniu.pscp.cs.bean.response.workorder.DetailsResponse;
import com.edianniu.pscp.cs.bean.workorder.DetailsReqData;
import com.edianniu.pscp.cs.bean.workorder.DetailsResult;
import com.edianniu.pscp.cs.biz.BaseBiz;
import com.edianniu.pscp.cs.commons.ResultCode;
import com.edianniu.pscp.cs.service.dubbo.WorkOrderInfoService;
import com.edianniu.pscp.mis.bean.Result;
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
 * ClassName: DetailsBiz
 * Author: tandingbo
 * CreateTime: 2017-08-08 11:41
 */
public class DetailsBiz extends BaseBiz<DetailsRequest, DetailsResponse> {
    private static final Logger logger = LoggerFactory.getLogger(DetailsBiz.class);

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;

    @Autowired
    @Qualifier("workOrderInfoService")
    private WorkOrderInfoService workOrderInfoService;

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

            DetailsReqData detailsReqData = new DetailsReqData();
            BeanUtils.copyProperties(req, detailsReqData);
            DetailsResult result = workOrderInfoService.workOrderDetails(detailsReqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
