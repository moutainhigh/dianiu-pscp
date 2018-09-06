package com.edianniu.pscp.mis.biz.defectrecord;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.defectrecord.RemoveReqData;
import com.edianniu.pscp.mis.bean.defectrecord.RemoveResult;
import com.edianniu.pscp.mis.bean.request.defectrecord.RemoveRequest;
import com.edianniu.pscp.mis.bean.response.defectrecord.RemoveResponse;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.mis.service.dubbo.WorkOrderDefectRecordInfoService;
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
 * ClassName: RemoveBiz
 * Author: tandingbo
 * CreateTime: 2017-09-13 09:58
 */
public class RemoveBiz extends BaseBiz<RemoveRequest, RemoveResponse> {
    private static final Logger logger = LoggerFactory.getLogger(RemoveBiz.class);

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;
    @Autowired
    @Qualifier("workOrderDefectRecordInfoService")
    private WorkOrderDefectRecordInfoService workOrderDefectRecordInfoService;

    @StateTemplate(init = true)
    class RecvReq {
        RecvReq() {
        }

        @OnAccept
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, RemoveRequest req) {
            logger.debug("RemoveRequest recv req : {}", req);
            RemoveResponse resp = (RemoveResponse) initResponse(ctx, req, new RemoveResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            RemoveReqData reqData = new RemoveReqData();
            BeanUtils.copyProperties(req, reqData);
            RemoveResult result = workOrderDefectRecordInfoService.removeDefectRecord(reqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}