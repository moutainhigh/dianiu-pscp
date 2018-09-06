package com.edianniu.pscp.sps.biz.workOrder;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.sps.bean.request.workOrder.ListRequest;
import com.edianniu.pscp.sps.bean.response.workOrder.ListResponse;
import com.edianniu.pscp.sps.bean.workorder.chieforder.ListReqData;
import com.edianniu.pscp.sps.bean.workorder.chieforder.ListResult;
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
 * 工单列表接口
 * ClassName: ListBiz
 * Author: tandingbo
 * CreateTime: 2017-06-26 09:39
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

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken(),"/sps/workorder/list");
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            ListReqData listReqData = new ListReqData();
            BeanUtils.copyProperties(req, listReqData);
            ListResult result = workOrderInfoService.list(listReqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }

}
