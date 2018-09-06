package com.edianniu.pscp.mis.biz.invoice;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.invoice.DetailInvoiceInfoReqData;
import com.edianniu.pscp.mis.bean.invoice.DetailInvoiceInfoResult;
import com.edianniu.pscp.mis.bean.request.invoice.info.DetailInvoiceInfoRequest;
import com.edianniu.pscp.mis.bean.response.invoice.info.DetailInvoiceInfoResponse;
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

public class InvoiceInfoDetailBiz extends BaseBiz<DetailInvoiceInfoRequest, DetailInvoiceInfoResponse> {
    private static final Logger logger = LoggerFactory.getLogger(InvoiceInfoDetailBiz.class);


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
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, DetailInvoiceInfoRequest req) {
            logger.debug("InvoiceInfoDetailBiz recv req : {}", req);

            DetailInvoiceInfoResponse resp = (DetailInvoiceInfoResponse) initResponse(ctx, req, new DetailInvoiceInfoResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (!isLogin.isSuccess()) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }


            DetailInvoiceInfoReqData detailInvoiceInfoReqData = new DetailInvoiceInfoReqData();
            BeanUtils.copyProperties(req, detailInvoiceInfoReqData);

            DetailInvoiceInfoResult result = invoiceInfoService.getDetailInvoiceInfo(detailInvoiceInfoReqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
