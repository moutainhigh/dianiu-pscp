package com.edianniu.pscp.mis.biz.wallet;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.request.wallet.MyBankCardRequest;
import com.edianniu.pscp.mis.bean.response.wallet.MyBankCardResponse;
import com.edianniu.pscp.mis.bean.wallet.MyBankCardsResult;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.mis.service.dubbo.WalletInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;

public class MyBankCardBiz extends BaseBiz<MyBankCardRequest, MyBankCardResponse> {

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
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, MyBankCardRequest req) {
            MyBankCardResponse resp = (MyBankCardResponse) initResponse(ctx, req, new MyBankCardResponse());
            
            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken(),"/wallet/mybankcards");
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            MyBankCardsResult result = walletInfoService.myBankCards(req.getUid());
            if (result.getResultCode() == ResultCode.SUCCESS) {
                resp.setBankCards(result.getBankCards());
            }
            resp.setResultCode(result.getResultCode());
            resp.setResultMessage(result.getResultMessage());
            return SendResp.class;

        }
    }

}
