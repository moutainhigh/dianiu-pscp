package com.edianniu.pscp.cs.biz.company;

import com.edianniu.pscp.cs.bean.company.BindingFacilitatorReqData;
import com.edianniu.pscp.cs.bean.company.BindingFacilitatorResult;
import com.edianniu.pscp.cs.bean.request.company.BindingFacilitatorRequest;
import com.edianniu.pscp.cs.bean.response.company.BindingFacilitatorResponse;
import com.edianniu.pscp.cs.biz.BaseBiz;
import com.edianniu.pscp.cs.commons.ResultCode;
import com.edianniu.pscp.cs.service.dubbo.CompanyCustomerInfoService;
import com.edianniu.pscp.mis.bean.Result;
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
 * ClassName: BindingFacilitatorListBiz
 * Author: tandingbo
 * CreateTime: 2017-10-30 14:57
 */
public class BindingFacilitatorListBiz extends BaseBiz<BindingFacilitatorRequest, BindingFacilitatorResponse> {
    private static final Logger logger = LoggerFactory.getLogger(BindingFacilitatorListBiz.class);

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;
    @Autowired
    @Qualifier("csCompanyCustomerInfoService")
    private CompanyCustomerInfoService companyCustomerInfoService;

    @StateTemplate(init = true)
    class RecvReq {
        RecvReq() {
        }

        @OnAccept
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, BindingFacilitatorRequest req) {
            logger.debug("BindingFacilitatorRequest recv req : {}", req);
            BindingFacilitatorResponse resp = (BindingFacilitatorResponse) initResponse(ctx, req, new BindingFacilitatorResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            BindingFacilitatorReqData listReqData = new BindingFacilitatorReqData();
            BeanUtils.copyProperties(req, listReqData);
            BindingFacilitatorResult result = companyCustomerInfoService.queryBindingFacilitator(listReqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
