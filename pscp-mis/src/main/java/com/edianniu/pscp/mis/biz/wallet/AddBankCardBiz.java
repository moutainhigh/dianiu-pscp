package com.edianniu.pscp.mis.biz.wallet;


import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.request.wallet.AddBankCardRequest;
import com.edianniu.pscp.mis.bean.response.wallet.AddBankCardResponse;
import com.edianniu.pscp.mis.bean.wallet.AddBankCardReq;
import com.edianniu.pscp.mis.bean.wallet.AddBankCardResult;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.mis.service.dubbo.WalletInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;

public class AddBankCardBiz extends BaseBiz<AddBankCardRequest, AddBankCardResponse> {

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
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, AddBankCardRequest req) {
            AddBankCardResponse resp = (AddBankCardResponse) initResponse(ctx, req, new AddBankCardResponse());
           
            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken(),"/wallet/addbankcard");
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }
            
            AddBankCardReq addBankCardReq = new AddBankCardReq();
            BeanUtils.copyProperties(req, addBankCardReq);
            AddBankCardResult result = walletInfoService.addBankCard(addBankCardReq);
            resp.setResultCode(result.getResultCode());
            resp.setResultMessage(result.getResultMessage());
            return SendResp.class;

        }
    }

}
