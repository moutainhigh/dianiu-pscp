package com.edianniu.pscp.sps.biz.needsorder;

import com.edianniu.pscp.cs.bean.needsorder.SaveReqData;
import com.edianniu.pscp.cs.bean.needsorder.SaveResult;
import com.edianniu.pscp.cs.service.dubbo.CustomerNeedsOrderInfoService;
import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.sps.bean.request.needsorder.SaveRequest;
import com.edianniu.pscp.sps.bean.response.needsorder.SaveResponse;
import com.edianniu.pscp.sps.biz.BaseBiz;
import com.edianniu.pscp.sps.commons.ResultCode;
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
 * CreateTime: 2017-09-26 10:55
 */
public class SaveBiz extends BaseBiz<SaveRequest, SaveResponse> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;
    @Autowired
    @Qualifier("customerNeedsOrderInfoService")
    private CustomerNeedsOrderInfoService customerNeedsOrderInfoService;

    @StateTemplate(init = true)
    class RecvReq {
        RecvReq() {
        }

        @OnAccept
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, SaveRequest req) {
            logger.debug("SaveRequest recv req : {}", req);
            SaveResponse resp = (SaveResponse) initResponse(ctx, req, new SaveResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken(),"/needs/facilitator/responded");
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            SaveReqData saveReqData = new SaveReqData();
            BeanUtils.copyProperties(req, saveReqData);
            SaveResult result = customerNeedsOrderInfoService.save(saveReqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
