package com.edianniu.pscp.cs.biz.workorder;

import com.edianniu.pscp.cs.bean.request.workorder.ListRequest;
import com.edianniu.pscp.cs.bean.response.workorder.ListResponse;
import com.edianniu.pscp.cs.bean.workorder.ListReqData;
import com.edianniu.pscp.cs.bean.workorder.ListResult;
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
 * ClassName: ListBiz
 * Author: tandingbo
 * CreateTime: 2017-08-07 16:50
 */
public class ListBiz extends BaseBiz<ListRequest, ListResponse> {
    private static final Logger logger = LoggerFactory.getLogger(ListBiz.class);

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
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, ListRequest req) {
            logger.debug("ListRequest recv req : {}", req);
            ListResponse resp = (ListResponse) initResponse(ctx, req, new ListResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            ListReqData listReqData = new ListReqData();
            BeanUtils.copyProperties(req, listReqData);
            ListResult result = workOrderInfoService.listWorkOrder(listReqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
