package com.edianniu.pscp.sps.biz.socialworkorder;

import com.edianniu.pscp.mis.bean.GetUserInfoResult;
import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.sps.bean.request.socialworkorder.ListRequest;
import com.edianniu.pscp.sps.bean.response.socialworkorder.ListResponse;
import com.edianniu.pscp.sps.bean.socialworkorder.SocialWorkOrderRequestType;
import com.edianniu.pscp.sps.bean.socialworkorder.list.ListReqData;
import com.edianniu.pscp.sps.bean.socialworkorder.list.ListResult;
import com.edianniu.pscp.sps.biz.BaseBiz;
import com.edianniu.pscp.sps.commons.ResultCode;
import com.edianniu.pscp.sps.service.dubbo.SocialWorkOrderInfoService;
import org.apache.commons.lang3.StringUtils;
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
 * 社会工单列表接口
 * ClassName: ListBiz
 * Author: tandingbo
 * CreateTime: 2017-06-29 11:18
 */
public class ListBiz extends BaseBiz<ListRequest, ListResponse> {
    private static final Logger logger = LoggerFactory.getLogger(ListBiz.class);

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;

    @Autowired
    @Qualifier("socialWorkOrderInfoService")
    private SocialWorkOrderInfoService socialWorkOrderInfoService;

    @StateTemplate(init = true)
    class RecvReq {
        RecvReq() {
        }

        @OnAccept
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, ListRequest req) {
            logger.debug("ListRequest recv req : {}", req);
            ListResponse resp = (ListResponse) initResponse(ctx, req, new ListResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken(),"/sps/socialworkorder/list");
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            GetUserInfoResult userInfoResult = userInfoService.getUserInfo(req.getUid());
            if (!userInfoResult.isSuccess()) {
                return SendResp.class;
            }

            ListReqData listReqData = new ListReqData();
            BeanUtils.copyProperties(req, listReqData);
            listReqData.setMemberId(userInfoResult.getMemberInfo().getUid());
            listReqData.setCompanyId(userInfoResult.getMemberInfo().getCompanyId());

            if (StringUtils.isNotBlank(req.getStatus())) {
                if (!SocialWorkOrderRequestType.isExist(req.getStatus())) {
                    return SendResp.class;
                }
                SocialWorkOrderRequestType socialWorkOrderRequestType = SocialWorkOrderRequestType.get(req.getStatus());
                if (socialWorkOrderRequestType == null) {
                    return SendResp.class;
                }
                listReqData.setStatus(socialWorkOrderRequestType.getStatus());
            }

            ListResult result = socialWorkOrderInfoService.list(listReqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
