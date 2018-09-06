package com.edianniu.pscp.mis.biz.electricianworkorder;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.electricianworkorder.AddWorkLogReqData;
import com.edianniu.pscp.mis.bean.electricianworkorder.AddWorkLogResult;
import com.edianniu.pscp.mis.bean.request.electricianworkorder.AddWorkLogRequest;
import com.edianniu.pscp.mis.bean.response.electricianworkorder.AddWorkLogResponse;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.ElectricianWorkOrderInfoService;
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
 * 电工工单新增工作记录接口
 * ClassName: AddWorkLogBiz
 * Author: tandingbo
 * CreateTime: 2017-04-13 18:09
 */
public class AddWorkLogBiz extends BaseBiz<AddWorkLogRequest, AddWorkLogResponse> {
    private static final Logger logger = LoggerFactory.getLogger(AddWorkLogBiz.class);

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;

    @Autowired
    @Qualifier("electricianWorkOrderInfoService")
    private ElectricianWorkOrderInfoService electricianWorkOrderInfoService;

    @StateTemplate(init = true)
    class RecvReq {
        RecvReq() {
        }

        @OnAccept
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, AddWorkLogRequest req) {
            logger.debug("AddWorkLogRequest recv req : {}", req);
            AddWorkLogResponse resp = (AddWorkLogResponse) initResponse(ctx, req, new AddWorkLogResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            AddWorkLogReqData addWorkLogReqData = new AddWorkLogReqData();
            addWorkLogReqData.setUid(req.getUid());
            addWorkLogReqData.setOrderId(req.getOrderId());
            addWorkLogReqData.setText(req.getText());
            addWorkLogReqData.setFiles(req.getFiles());
            AddWorkLogResult result = electricianWorkOrderInfoService.addWorkLog(addWorkLogReqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }

}
