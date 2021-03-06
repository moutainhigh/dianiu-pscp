package com.edianniu.pscp.sps.biz.needs;

import com.edianniu.pscp.cs.bean.needs.NeedsMarketListReqData;
import com.edianniu.pscp.cs.bean.needs.NeedsMarketListResult;
import com.edianniu.pscp.cs.service.dubbo.NeedsInfoService;
import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.sps.bean.request.needs.ListRequest;
import com.edianniu.pscp.sps.bean.response.needs.ListResponse;
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
 * ClassName: ListBiz
 * Author: tandingbo
 * CreateTime: 2017-09-25 15:56
 */
public class ListBiz extends BaseBiz<ListRequest, ListResponse> {
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
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, ListRequest req) {
            logger.debug("ListRequest recv req : {}", req);
            ListResponse resp = (ListResponse) initResponse(ctx, req, new ListResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            NeedsMarketListReqData listReqData = new NeedsMarketListReqData();
            BeanUtils.copyProperties(req, listReqData);
            NeedsMarketListResult result = needsInfoService.queryListNeedsMarket(listReqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
