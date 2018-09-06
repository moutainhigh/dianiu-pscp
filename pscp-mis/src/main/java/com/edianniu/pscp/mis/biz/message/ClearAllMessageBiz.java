package com.edianniu.pscp.mis.biz.message;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.message.ClearAllMessageReqData;
import com.edianniu.pscp.mis.bean.message.ClearAllMessageResult;
import com.edianniu.pscp.mis.bean.request.message.ClearAllMessageRequest;
import com.edianniu.pscp.mis.bean.response.message.ClearAllMessageResponse;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.biz.electricianworkorder.AddWorkLogBiz;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.MemberMessageInfoService;
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
 * 消息中心清空全部接口
 * ClassName: ClearAllMessageBiz
 * Author: tandingbo
 * CreateTime: 2017-04-17 16:28
 */
public class ClearAllMessageBiz extends BaseBiz<ClearAllMessageRequest, ClearAllMessageResponse> {
    private static final Logger logger = LoggerFactory.getLogger(AddWorkLogBiz.class);

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;

    @Autowired
    @Qualifier("memberMessageInfoService")
    private MemberMessageInfoService memberMessageInfoService;

    @StateTemplate(init = true)
    class RecvReq {
        RecvReq() {
        }

        @OnAccept
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, ClearAllMessageRequest req) {
            logger.debug("ListMessageRequest recv req : {}", req);

            ClearAllMessageResponse resp = (ClearAllMessageResponse) initResponse(ctx, req, new ClearAllMessageResponse());
            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            ClearAllMessageReqData clearAllMessageReqData = new ClearAllMessageReqData();
            clearAllMessageReqData.setUid(req.getUid());
            ClearAllMessageResult result = memberMessageInfoService.clearAllMessage(clearAllMessageReqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
