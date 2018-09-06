package com.edianniu.pscp.mis.biz.socialworkorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.request.socialworkorder.DetailRequest;
import com.edianniu.pscp.mis.bean.response.socialworkorder.DetailResponse;
import com.edianniu.pscp.mis.bean.workorder.SocialWorkOrderDetailReqData;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.SocialWorkOrderInfoService;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;

/**
 * 社会工单详情
 *
 * @author cyl
 */
public class DetailBiz extends BaseBiz<DetailRequest, DetailResponse> {
    private static final Logger logger = LoggerFactory
            .getLogger(DetailBiz.class);
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
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx,
                        DetailRequest req) {
            logger.debug("DetailRequest recv req : {}", req);
            DetailResponse resp = (DetailResponse) initResponse(ctx, req, new DetailResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }
            SocialWorkOrderDetailReqData reqData = new SocialWorkOrderDetailReqData();
            BeanUtils.copyProperties(req, reqData);
            Result result = socialWorkOrderInfoService.detail(reqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
