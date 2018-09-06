package com.edianniu.pscp.mis.biz.electricianworkorder;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.electricianworkorder.DelWorkLogAttachmentReqData;
import com.edianniu.pscp.mis.bean.electricianworkorder.DelWorkLogAttachmentResult;
import com.edianniu.pscp.mis.bean.request.electricianworkorder.DelWorkLogAttachmentRequest;
import com.edianniu.pscp.mis.bean.response.electricianworkorder.DelWorkLogAttachmentResponse;
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
 * 电工工单删除工作记录附件接口
 * ClassName: DelWorkLogAttachmentBiz
 * Author: tandingbo
 * CreateTime: 2017-04-13 18:11
 */
public class DelWorkLogAttachmentBiz extends BaseBiz<DelWorkLogAttachmentRequest, DelWorkLogAttachmentResponse> {
    private static final Logger logger = LoggerFactory.getLogger(DelWorkLogAttachmentBiz.class);

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
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, DelWorkLogAttachmentRequest req) {
            logger.debug("DelWorkLogAttachmentRequest recv req : {}", req);

            DelWorkLogAttachmentResponse resp = (DelWorkLogAttachmentResponse) initResponse(ctx, req, new DelWorkLogAttachmentResponse());
            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            DelWorkLogAttachmentReqData attachmentReqData = new DelWorkLogAttachmentReqData();
            attachmentReqData.setUid(req.getUid());
            attachmentReqData.setOrderId(req.getOrderId());
            attachmentReqData.setWorkLogAttachmentId(req.getWorkLogAttachmentId());
            DelWorkLogAttachmentResult result = electricianWorkOrderInfoService.delWorkLogAttachment(attachmentReqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
