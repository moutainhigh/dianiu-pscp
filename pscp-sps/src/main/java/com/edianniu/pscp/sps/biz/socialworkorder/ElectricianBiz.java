package com.edianniu.pscp.sps.biz.socialworkorder;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.sps.bean.request.socialworkorder.ElectricianRequest;
import com.edianniu.pscp.sps.bean.response.socialworkorder.ElectricianResponse;
import com.edianniu.pscp.sps.bean.socialworkorder.electrician.ElectricianReqData;
import com.edianniu.pscp.sps.bean.socialworkorder.electrician.ElectricianResult;
import com.edianniu.pscp.sps.biz.BaseBiz;
import com.edianniu.pscp.sps.commons.ResultCode;
import com.edianniu.pscp.sps.service.dubbo.SocialWorkOrderInfoService;
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
 * 社会工单人员列表接口
 * ClassName: ElectricianBiz
 * Author: tandingbo
 * CreateTime: 2017-06-29 15:33
 */
public class ElectricianBiz extends BaseBiz<ElectricianRequest, ElectricianResponse> {
    private static final Logger logger = LoggerFactory.getLogger(ElectricianBiz.class);

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
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, ElectricianRequest req) {
            logger.debug("ElectricianRequest recv req : {}", req);
            ElectricianResponse resp = (ElectricianResponse) initResponse(ctx, req, new ElectricianResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken(),"/sps/socialworkorder/electrician");
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            ElectricianReqData electricianReqData = new ElectricianReqData();
            BeanUtils.copyProperties(req, electricianReqData);
            ElectricianResult result = socialWorkOrderInfoService.electricianWorkOrder(electricianReqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }

}
