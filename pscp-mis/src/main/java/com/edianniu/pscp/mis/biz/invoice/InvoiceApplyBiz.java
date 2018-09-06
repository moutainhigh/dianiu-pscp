package com.edianniu.pscp.mis.biz.invoice;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.invoice.InvoiceApplyReqData;
import com.edianniu.pscp.mis.bean.invoice.InvoiceApplyResult;
import com.edianniu.pscp.mis.bean.request.invoice.ApplyInvoiceRequest;
import com.edianniu.pscp.mis.bean.response.invoice.ApplyInvoiceResponse;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.service.dubbo.MemberInvoiceInfoService;
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

public class InvoiceApplyBiz extends BaseBiz<ApplyInvoiceRequest, ApplyInvoiceResponse> {
    private static final Logger logger = LoggerFactory.getLogger(InvoiceApplyBiz.class);

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;


    @Autowired
    @Qualifier("memberInvoiceInfoService")
    private MemberInvoiceInfoService invoiceService;

    @StateTemplate(init = true)
    class RecvReq {
        RecvReq() {
        }

        @OnAccept
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, ApplyInvoiceRequest req) {
            logger.debug("InvoiceInfoDetailBiz recv req : {}", req);

            ApplyInvoiceResponse resp = (ApplyInvoiceResponse) initResponse(ctx, req, new ApplyInvoiceResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (!isLogin.isSuccess()) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }
            InvoiceApplyReqData applyReqData = new InvoiceApplyReqData();
            BeanUtils.copyProperties(req, applyReqData);
            InvoiceApplyResult result = invoiceService.applyInvoice(applyReqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
