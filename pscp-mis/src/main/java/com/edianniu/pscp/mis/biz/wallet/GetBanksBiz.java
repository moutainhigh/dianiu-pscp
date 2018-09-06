package com.edianniu.pscp.mis.biz.wallet;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.request.wallet.GetBanksRequest;
import com.edianniu.pscp.mis.bean.response.wallet.GetBanksResponse;
import com.edianniu.pscp.mis.bean.wallet.GetBanksResult;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.mis.service.dubbo.WalletInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;

public class GetBanksBiz extends BaseBiz<GetBanksRequest, GetBanksResponse> {

    private static final Logger logger = LoggerFactory.getLogger(GetBanksBiz.class);
    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;

    @Autowired
    @Qualifier("walletInfoService")
    private WalletInfoService walletInfoService;

    @StateTemplate(init = true)
    class RecvReq {
        RecvReq() {

        }

        @OnAccept
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, GetBanksRequest req) {
            GetBanksResponse resp = (GetBanksResponse) initResponse(ctx, req, new GetBanksResponse());
            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            GetBanksResult result = walletInfoService.getBanks(req.getUid());
            if (result.getResultCode() == ResultCode.SUCCESS) {
                resp.setBanks(result.getBanks());
            }
            resp.setResultCode(result.getResultCode());
            resp.setResultMessage(result.getResultMessage());
            return SendResp.class;

        }
    }

}
