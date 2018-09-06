package com.edianniu.pscp.sps.biz.needs;

import com.edianniu.pscp.cs.bean.needs.NeedsMarketDetailsReqData;
import com.edianniu.pscp.cs.bean.needs.NeedsMarketDetailsResult;
import com.edianniu.pscp.cs.service.dubbo.NeedsInfoService;
import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.sps.bean.request.needs.DetailsRequest;
import com.edianniu.pscp.sps.bean.response.needs.DetailsResponse;
import com.edianniu.pscp.sps.biz.BaseBiz;
import com.edianniu.pscp.sps.commons.ResultCode;
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
 * ClassName: DetailsBiz
 * Author: tandingbo
 * CreateTime: 2017-09-25 16:47
 */
public class DetailsBiz extends BaseBiz<DetailsRequest, DetailsResponse> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, DetailsRequest req) {
            logger.debug("DetailsRequest recv req : {}", req);
            DetailsResponse resp = (DetailsResponse) initResponse(ctx, req, new DetailsResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            NeedsMarketDetailsReqData detailsReqData = new NeedsMarketDetailsReqData();
            BeanUtils.copyProperties(req, detailsReqData);
            NeedsMarketDetailsResult result = needsInfoService.getNeedsMarketDetails(detailsReqData);
            BeanUtils.copyProperties(result, resp);
            return BaseBiz.SendResp.class;
        }
    }
}