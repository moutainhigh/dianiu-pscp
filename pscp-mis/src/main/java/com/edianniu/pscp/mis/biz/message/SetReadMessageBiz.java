package com.edianniu.pscp.mis.biz.message;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.message.SetReadMessageReqData;
import com.edianniu.pscp.mis.bean.message.SetReadMessageResult;
import com.edianniu.pscp.mis.bean.request.message.SetReadMessageRequest;
import com.edianniu.pscp.mis.bean.response.message.SetReadMessageResponse;
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
 * 消息中心设置已读接口
 * ClassName: SetReadMessageBiz
 * Author: tandingbo
 * CreateTime: 2017-04-17 16:21
 */
public class SetReadMessageBiz extends BaseBiz<SetReadMessageRequest, SetReadMessageResponse> {
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
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, SetReadMessageRequest req) {
            logger.debug("SetReadMessageRequest recv req : {}", req);

            SetReadMessageResponse resp = (SetReadMessageResponse) initResponse(ctx, req, new SetReadMessageResponse());
            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            SetReadMessageReqData setReadMessageReqData = new SetReadMessageReqData();
            setReadMessageReqData.setId(req.getId());
            setReadMessageReqData.setUid(req.getUid());
            SetReadMessageResult result = memberMessageInfoService.setReadMessage(setReadMessageReqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
