package com.edianniu.pscp.mis.biz.invoice;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.invoice.InvoiceTitleReqData;
import com.edianniu.pscp.mis.bean.invoice.InvoiceTitleResult;
import com.edianniu.pscp.mis.bean.request.invoice.title.InvoiceTitleRequest;
import com.edianniu.pscp.mis.bean.response.invoice.title.InvoiceTitleResponse;
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

public class InvoiceTitleBiz extends BaseBiz<InvoiceTitleRequest, InvoiceTitleResponse> {
    private static final Logger logger = LoggerFactory.getLogger(InvoiceTitleBiz.class);


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
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, InvoiceTitleRequest req) {
            logger.debug("InvoiceInfoDetailBiz recv req : {}", req);


            InvoiceTitleResponse resp = (InvoiceTitleResponse) initResponse(ctx, req, new InvoiceTitleResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (!isLogin.isSuccess()) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            InvoiceTitleReqData titleReqData = new InvoiceTitleReqData();
            BeanUtils.copyProperties(req, titleReqData);
            InvoiceTitleResult result = invoiceService.getInvoiceTitleInfo(titleReqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
