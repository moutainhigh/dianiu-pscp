package com.edianniu.pscp.sps.biz.workOrder;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.sps.bean.electrician.ElectricianVOResult;
import com.edianniu.pscp.sps.bean.request.workOrder.ElectricianRequest;
import com.edianniu.pscp.sps.bean.response.workOrder.ElectricianResponse;
import com.edianniu.pscp.sps.biz.BaseBiz;
import com.edianniu.pscp.sps.commons.ResultCode;
import com.edianniu.pscp.sps.service.dubbo.SpsElectricianInfoService;
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
 * 企业电工信息接口
 * ClassName: ElectricianBiz
 * Author: tandingbo
 * CreateTime: 2017-06-29 10:35
 */
public class ElectricianBiz extends BaseBiz<ElectricianRequest, ElectricianResponse> {
    private static final Logger logger = LoggerFactory.getLogger(ElectricianBiz.class);

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;
    @Autowired
    @Qualifier("spsElectricianInfoService")
    private SpsElectricianInfoService spsElectricianInfoService;

    @StateTemplate(init = true)
    class RecvReq {
        RecvReq() {
        }

        @OnAccept
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, ElectricianRequest req) {
            logger.debug("ElectricianResponse recv req : {}", req);
            ElectricianResponse resp = (ElectricianResponse) initResponse(ctx, req, new ElectricianResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            ElectricianVOResult result = spsElectricianInfoService.selectElectricianVOByCompanyId(req.getUid());
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
