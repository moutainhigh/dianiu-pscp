package com.edianniu.pscp.sps.biz.socialworkorder;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.sps.bean.request.socialworkorder.SaveRequest;
import com.edianniu.pscp.sps.bean.response.socialworkorder.SaveResponse;
import com.edianniu.pscp.sps.bean.socialworkorder.recruit.RecruitReqData;
import com.edianniu.pscp.sps.bean.socialworkorder.recruit.RecruitResult;
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
 * 社会工单保存接口
 * ClassName: SaveBiz
 * Author: tandingbo
 * CreateTime: 2017-06-29 14:15
 */
public class SaveBiz extends BaseBiz<SaveRequest, SaveResponse> {
    private static final Logger logger = LoggerFactory.getLogger(SaveBiz.class);

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
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, SaveRequest req) {
            logger.debug("SaveRequest recv req : {}", req);
            SaveResponse resp = (SaveResponse) initResponse(ctx, req, new SaveResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken(),"/sps/socialworkorder/save");
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            RecruitReqData recruitReqData = new RecruitReqData();
            BeanUtils.copyProperties(req, recruitReqData);
            RecruitResult result = socialWorkOrderInfoService.recruit(recruitReqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
