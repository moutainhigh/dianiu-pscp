package com.edianniu.pscp.mis.biz.message;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.message.ListMessageReqData;
import com.edianniu.pscp.mis.bean.message.ListMessageResult;
import com.edianniu.pscp.mis.bean.request.message.ListMessageRequest;
import com.edianniu.pscp.mis.bean.response.message.ListMessageResponse;
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
 * 消息中心列表接口
 * ClassName: ListMessageBiz
 * Author: tandingbo
 * CreateTime: 2017-04-17 16:08
 */
public class ListMessageBiz extends BaseBiz<ListMessageRequest, ListMessageResponse> {
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
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, ListMessageRequest req) {
            logger.debug("ListMessageRequest recv req : {}", req);

            ListMessageResponse resp = (ListMessageResponse) initResponse(ctx, req, new ListMessageResponse());
            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            ListMessageReqData listMessageReqData = new ListMessageReqData();
            listMessageReqData.setUid(req.getUid());
            listMessageReqData.setOffset(req.getOffset());
            ListMessageResult result = memberMessageInfoService.listMessage(listMessageReqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
