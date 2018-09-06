package com.edianniu.pscp.mis.biz.invoice;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.invoice.ConfirmInvoiceInfoReqData;
import com.edianniu.pscp.mis.bean.invoice.ConfirmInvoiceInfoResult;
import com.edianniu.pscp.mis.bean.request.invoice.info.ConfirmInvoiceInfoRequest;
import com.edianniu.pscp.mis.bean.response.invoice.info.ConfirmInvoiceInfoResponse;
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

public class InvoiceInfoConfirmBiz extends BaseBiz<ConfirmInvoiceInfoRequest, ConfirmInvoiceInfoResponse> {
    private static final Logger logger = LoggerFactory.getLogger(InvoiceInfoConfirmBiz.class);


    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;

    @Autowired
    @Qualifier("memberInvoiceInfoService")
    private MemberInvoiceInfoService invoiceInfoService;

    @StateTemplate(init = true)
    class RecvReq {
        RecvReq() {
        }

        @OnAccept
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, ConfirmInvoiceInfoRequest req) {
            logger.debug("InvoiceInfoConfirmBiz recv req : {}", req);

            ConfirmInvoiceInfoResponse resp = (ConfirmInvoiceInfoResponse) initResponse(ctx, req, new ConfirmInvoiceInfoResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (!isLogin.isSuccess()) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            ConfirmInvoiceInfoReqData confirmInvoiceInfoReqData = new ConfirmInvoiceInfoReqData();
            BeanUtils.copyProperties(req, confirmInvoiceInfoReqData);

            ConfirmInvoiceInfoResult result = invoiceInfoService.confirmInvoice(confirmInvoiceInfoReqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
