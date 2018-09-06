package com.edianniu.pscp.sps.biz.customer;

import com.edianniu.pscp.mis.bean.GetUserInfoResult;
import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.sps.bean.customer.CustomerResult;
import com.edianniu.pscp.sps.bean.request.customer.ListRequest;
import com.edianniu.pscp.sps.bean.response.customer.ListResponse;
import com.edianniu.pscp.sps.biz.BaseBiz;
import com.edianniu.pscp.sps.biz.workOrder.SelectDataBiz;
import com.edianniu.pscp.sps.commons.ResultCode;
import com.edianniu.pscp.sps.service.dubbo.SpsCompanyCustomerInfoService;
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
 * 客户列表接口
 * ClassName: ListBiz
 * Author: tandingbo
 * CreateTime: 2017-07-12 10:16
 */
public class ListBiz extends BaseBiz<ListRequest, ListResponse> {
    private static final Logger logger = LoggerFactory.getLogger(SelectDataBiz.class);

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;
    @Autowired
    @Qualifier("spsCompanyCustomerInfoService")
    private SpsCompanyCustomerInfoService spsCompanyCustomerInfoService;

    @StateTemplate(init = true)
    class RecvReq {
        RecvReq() {
        }

        @OnAccept
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, ListRequest req) {
            logger.debug("ListRequest recv req : {}", req);
            ListResponse resp = (ListResponse) initResponse(ctx, req, new ListResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(req.getUid());
            if (!getUserInfoResult.isSuccess()) {
                resp.setResultCode(getUserInfoResult.getResultCode());
                resp.setResultMessage(getUserInfoResult.getResultMessage());
                return SendResp.class;
            }
            UserInfo userInfo = getUserInfoResult.getMemberInfo();

            CustomerResult result = spsCompanyCustomerInfoService.selectCustomerByCompanyId(userInfo.getCompanyId());
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
