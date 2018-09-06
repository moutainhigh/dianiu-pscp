package com.edianniu.pscp.sps.biz.workOrder;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.sps.bean.request.workOrder.InitDataRequest;
import com.edianniu.pscp.sps.bean.response.workOrder.InitDataResponse;
import com.edianniu.pscp.sps.bean.workorder.chieforder.InitDataReqData;
import com.edianniu.pscp.sps.bean.workorder.chieforder.InitDataResult;
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
 * 派单数据初始化接口
 * ClassName: InitDataBiz
 * Author: tandingbo
 * CreateTime: 2017-06-28 10:19
 */
public class InitDataBiz extends BaseBiz<InitDataRequest, InitDataResponse> {
    private static final Logger logger = LoggerFactory.getLogger(InitDataBiz.class);

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
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, InitDataRequest req) {
            logger.debug("InitDataResponse recv req : {}", req);
            InitDataResponse resp = (InitDataResponse) initResponse(ctx, req, new InitDataResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken(),"/sps/workorder/initialdata");
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            InitDataReqData initDataReqData = new InitDataReqData();
            BeanUtils.copyProperties(req, initDataReqData);
            InitDataResult result = workOrderInfoService.initData(initDataReqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
