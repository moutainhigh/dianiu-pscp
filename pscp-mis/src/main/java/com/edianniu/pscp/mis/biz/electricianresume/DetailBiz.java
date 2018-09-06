package com.edianniu.pscp.mis.biz.electricianresume;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.electricianresume.DetailReqData;
import com.edianniu.pscp.mis.bean.electricianresume.DetailResult;
import com.edianniu.pscp.mis.bean.request.electricianresume.DetailRequest;
import com.edianniu.pscp.mis.bean.response.electricianresume.DetailResponse;
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
 * 电工简历详情接口
 * ClassName: DetailBiz
 * Author: tandingbo
 * CreateTime: 2017-04-19 16:19
 */
public class DetailBiz extends BaseBiz<DetailRequest, DetailResponse> {
    private static final Logger logger = LoggerFactory.getLogger(DetailBiz.class);

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
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, DetailRequest req) {
            logger.debug("DetailRequest recv req : {}", req);
            DetailResponse resp = (DetailResponse) initResponse(ctx, req, new DetailResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            DetailReqData reqData = new DetailReqData();
            BeanUtils.copyProperties(req, reqData);
            DetailResult result = electricianResumeInfoService.resumeDetail(reqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
