package com.edianniu.pscp.mis.biz.electricianresume;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.electricianresume.UpdateReqData;
import com.edianniu.pscp.mis.bean.electricianresume.UpdateResult;
import com.edianniu.pscp.mis.bean.request.electricianresume.UpdateRequest;
import com.edianniu.pscp.mis.bean.response.electricianresume.UpdateResponse;
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
 * 电工简历基本信息修改接口
 * ClassName: UpdateBiz
 * Author: tandingbo
 * CreateTime: 2017-04-19 16:33
 */
public class UpdateBiz extends BaseBiz<UpdateRequest, UpdateResponse> {
    private static final Logger logger = LoggerFactory.getLogger(UpdateBiz.class);

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
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, UpdateRequest req) {
            logger.debug("UpdateRequest recv req : {}", req);
            UpdateResponse resp = (UpdateResponse) initResponse(ctx, req, new UpdateResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            UpdateReqData reqData = new UpdateReqData();
            BeanUtils.copyProperties(req, reqData);
            UpdateResult result = electricianResumeInfoService.updateResume(reqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
