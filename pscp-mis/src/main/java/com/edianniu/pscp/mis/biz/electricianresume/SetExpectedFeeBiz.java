package com.edianniu.pscp.mis.biz.electricianresume;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.electricianresume.SetExpectedFeeReqData;
import com.edianniu.pscp.mis.bean.electricianresume.SetExpectedFeeResult;
import com.edianniu.pscp.mis.bean.request.electricianresume.SetExpectedFeeRequest;
import com.edianniu.pscp.mis.bean.response.electricianresume.SetExpectedFeeResponse;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.ElectricianResumeInfoService;
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
 * 电工简历期望费用设置接口
 * ClassName: SetExpectedFeeBiz
 * Author: tandingbo
 * CreateTime: 2017-04-19 17:17
 */
public class SetExpectedFeeBiz extends BaseBiz<SetExpectedFeeRequest, SetExpectedFeeResponse> {
    private static final Logger logger = LoggerFactory.getLogger(SetExpectedFeeBiz.class);

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;

    @Autowired
    @Qualifier("electricianResumeInfoService")
    private ElectricianResumeInfoService electricianResumeInfoService;


    @StateTemplate(init = true)
    class RecvReq {
        RecvReq() {
        }

        @OnAccept
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, SetExpectedFeeRequest req) {
            logger.debug("SetExpectedFeeRequest recv req : {}", req);
            SetExpectedFeeResponse resp = (SetExpectedFeeResponse) initResponse(ctx, req, new SetExpectedFeeResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            SetExpectedFeeReqData reqData = new SetExpectedFeeReqData();
            BeanUtils.copyProperties(req, reqData);
            SetExpectedFeeResult result = electricianResumeInfoService.setExpectedFee(reqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
