package com.edianniu.pscp.mis.biz.wallet;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.request.wallet.DelBankCardRequest;
import com.edianniu.pscp.mis.bean.response.wallet.DelBankCardResponse;
import com.edianniu.pscp.mis.bean.wallet.DelBankReqData;
import com.edianniu.pscp.mis.bean.wallet.DelBankResult;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.mis.service.dubbo.WalletInfoService;

import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;

public class DelBankCardBiz extends BaseBiz<DelBankCardRequest, DelBankCardResponse> {

    private static final Logger logger = LoggerFactory.getLogger(DelBankCardBiz.class);
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
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, DelBankCardRequest req) {
            DelBankCardResponse resp = (DelBankCardResponse) initResponse(ctx, req, new DelBankCardResponse());
            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken(),"/wallet/delbankcard");
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }
            DelBankReqData data = new DelBankReqData();
            BeanUtils.copyProperties(req, data);
            data.setId(req.getBankCardId());
            DelBankResult result = walletInfoService.delBankCard(data);
            resp.setResultCode(result.getResultCode());
            resp.setResultMessage(result.getResultMessage());
            return SendResp.class;

        }
    }

}
