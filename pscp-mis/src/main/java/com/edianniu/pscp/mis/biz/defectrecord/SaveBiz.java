package com.edianniu.pscp.mis.biz.defectrecord;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.defectrecord.SaveReqData;
import com.edianniu.pscp.mis.bean.defectrecord.SaveResult;
import com.edianniu.pscp.mis.bean.request.defectrecord.SaveRequest;
import com.edianniu.pscp.mis.bean.response.defectrecord.SaveResponse;
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
 * ClassName: SaveBiz
 * Author: tandingbo
 * CreateTime: 2017-09-12 15:10
 */
public class SaveBiz extends BaseBiz<SaveRequest, SaveResponse> {
    private static final Logger logger = LoggerFactory.getLogger(SaveBiz.class);

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
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, SaveRequest req) {
            logger.debug("SaveRequest recv req : {}", req);
            SaveResponse resp = (SaveResponse) initResponse(ctx, req, new SaveResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            SaveReqData reqData = new SaveReqData();
            BeanUtils.copyProperties(req, reqData);
            SaveResult result = workOrderDefectRecordInfoService.saveDefectRecord(reqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}