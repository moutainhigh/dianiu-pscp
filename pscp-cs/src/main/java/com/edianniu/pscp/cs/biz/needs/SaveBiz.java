package com.edianniu.pscp.cs.biz.needs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.edianniu.pscp.cs.bean.needs.SaveReqData;
import com.edianniu.pscp.cs.bean.needs.SaveResult;
import com.edianniu.pscp.cs.bean.request.needs.SaveRequest;
import com.edianniu.pscp.cs.bean.response.needs.SaveResponse;
import com.edianniu.pscp.cs.biz.BaseBiz;
import com.edianniu.pscp.cs.service.dubbo.NeedsInfoService;
import com.edianniu.pscp.cs.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.mis.bean.Result;
import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;

/**
 * @author zhoujianjian 2017年9月13日下午5:57:02
 */
public class SaveBiz extends BaseBiz<SaveRequest, SaveResponse> {
    private static final Logger logger = LoggerFactory.getLogger(SaveBiz.class);

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;

    @Autowired
    @Qualifier("needsInfoService")
    private NeedsInfoService needsInfoService;

    @StateTemplate(init = true)
    class RecvReq {
        RecvReq() {
        }

        @OnAccept
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, SaveRequest req) {
            logger.debug("SaveNeedsRequest recv req : {}", req);
            SaveResponse resp = (SaveResponse) initResponse(ctx, req, new SaveResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            SaveReqData saveReqData = new SaveReqData();
            BeanUtils.copyProperties(req, saveReqData);

            SaveResult result = needsInfoService.saveNeeds(saveReqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
