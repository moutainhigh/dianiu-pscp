package com.edianniu.pscp.sps.biz.workOrder;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.sps.bean.request.workOrder.SelectDataRequest;
import com.edianniu.pscp.sps.bean.response.workOrder.SelectDataResponse;
import com.edianniu.pscp.sps.bean.workorder.chieforder.SelectDataReqData;
import com.edianniu.pscp.sps.bean.workorder.chieforder.SelectDataResult;
import com.edianniu.pscp.sps.biz.BaseBiz;
import com.edianniu.pscp.sps.commons.ResultCode;
import com.edianniu.pscp.sps.service.dubbo.WorkOrderInfoService;
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
 * ClassName: SelectDataBiz
 * Author: tandingbo
 * CreateTime: 2017-07-11 16:13
 */
public class SelectDataBiz extends BaseBiz<SelectDataRequest, SelectDataResponse> {
    private static final Logger logger = LoggerFactory.getLogger(SelectDataBiz.class);

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
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, SelectDataRequest req) {
            logger.debug("SelectDataRequest recv req : {}", req);
            SelectDataResponse resp = (SelectDataResponse) initResponse(ctx, req, new SelectDataResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken(),"/sps/workorder/selectdata");
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            SelectDataReqData selectDataReqData = new SelectDataReqData();
            BeanUtils.copyProperties(req, selectDataReqData);
            SelectDataResult result = workOrderInfoService.listSelectData(selectDataReqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
