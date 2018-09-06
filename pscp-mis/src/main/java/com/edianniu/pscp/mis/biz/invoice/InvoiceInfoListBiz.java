package com.edianniu.pscp.mis.biz.invoice;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.invoice.ListInvoiceInfoReqData;
import com.edianniu.pscp.mis.bean.invoice.ListInvoiceInfoResult;
import com.edianniu.pscp.mis.bean.request.invoice.info.ListInvoiceInfoRequest;
import com.edianniu.pscp.mis.bean.response.invoice.info.ListInvoiceInfoResponse;
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

public class InvoiceInfoListBiz extends BaseBiz<ListInvoiceInfoRequest, ListInvoiceInfoResponse> {
    private static final Logger logger = LoggerFactory.getLogger(InvoiceInfoListBiz.class);


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
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, ListInvoiceInfoRequest req) {
            logger.debug("ListInvoiceInfoRequest recv req : {}", req);


            ListInvoiceInfoResponse resp = (ListInvoiceInfoResponse) initResponse(ctx, req, new ListInvoiceInfoResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (!isLogin.isSuccess()) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }


            ListInvoiceInfoReqData listInvoiceInfoReqData = new ListInvoiceInfoReqData();
            BeanUtils.copyProperties(req, listInvoiceInfoReqData);

            ListInvoiceInfoResult result = invoiceInfoService.queryListInvoiceInfo(listInvoiceInfoReqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
