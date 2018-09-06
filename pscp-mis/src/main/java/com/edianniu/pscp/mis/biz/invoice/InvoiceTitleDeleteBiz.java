package com.edianniu.pscp.mis.biz.invoice;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.invoice.InvoiceTitleDeleteReqData;
import com.edianniu.pscp.mis.bean.invoice.InvoiceTitleDeleteResult;
import com.edianniu.pscp.mis.bean.request.invoice.title.InvoiceTitleDeleteRequest;
import com.edianniu.pscp.mis.bean.response.invoice.title.InvoiceTitleDeleteResponse;
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

public class InvoiceTitleDeleteBiz extends BaseBiz<InvoiceTitleDeleteRequest, InvoiceTitleDeleteResponse> {
    private static final Logger logger = LoggerFactory.getLogger(InvoiceTitleDeleteBiz.class);


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
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, InvoiceTitleDeleteRequest req) {
            logger.debug("InvoiceInfoDetailBiz recv req : {}", req);



            InvoiceTitleDeleteResponse resp = (InvoiceTitleDeleteResponse) initResponse(ctx, req, new InvoiceTitleDeleteResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (!isLogin.isSuccess()) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            InvoiceTitleDeleteReqData deleteReqData = new InvoiceTitleDeleteReqData();
            BeanUtils.copyProperties(req, deleteReqData);

            InvoiceTitleDeleteResult result = invoiceService.deleteInvoiceTitleInfo(deleteReqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
