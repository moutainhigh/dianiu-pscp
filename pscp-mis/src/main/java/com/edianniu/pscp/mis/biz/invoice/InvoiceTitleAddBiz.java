package com.edianniu.pscp.mis.biz.invoice;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.invoice.InvoiceTitleAddReqData;
import com.edianniu.pscp.mis.bean.invoice.InvoiceTitleAddResult;
import com.edianniu.pscp.mis.bean.request.invoice.title.InvoiceTitleAddRequest;
import com.edianniu.pscp.mis.bean.response.invoice.title.InvoiceTitleAddResponse;
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

public class InvoiceTitleAddBiz extends BaseBiz<InvoiceTitleAddRequest, InvoiceTitleAddResponse> {
    private static final Logger logger = LoggerFactory.getLogger(InvoiceTitleAddBiz.class);


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
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, InvoiceTitleAddRequest req) {
            logger.debug("InvoiceInfoDetailBiz recv req : {}", req);


            InvoiceTitleAddResponse resp = (InvoiceTitleAddResponse) initResponse(ctx, req, new InvoiceTitleAddResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (!isLogin.isSuccess()) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            InvoiceTitleAddReqData addReqData = new InvoiceTitleAddReqData();
            BeanUtils.copyProperties(req, addReqData);

            InvoiceTitleAddResult result = invoiceInfoService.addInvoiceTitleInfo(addReqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
