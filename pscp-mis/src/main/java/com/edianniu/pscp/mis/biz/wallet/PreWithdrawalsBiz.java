package com.edianniu.pscp.mis.biz.wallet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.request.wallet.PreWithdrawalsRequest;
import com.edianniu.pscp.mis.bean.response.wallet.PreWithdrawalsResponse;
import com.edianniu.pscp.mis.bean.wallet.PreWithdrawalsReqData;
import com.edianniu.pscp.mis.bean.wallet.PreWithdrawalsResult;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.mis.service.dubbo.WalletInfoService;

import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;

/**
 * 我的钱包予提现接口
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年7月11日 下午2:10:26 
 * @version V1.0
 */
public class PreWithdrawalsBiz extends BaseBiz<PreWithdrawalsRequest,PreWithdrawalsResponse>{

	private static final Logger logger = LoggerFactory.getLogger(PreWithdrawalsBiz.class);
	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;
	
	@Autowired
	@Qualifier("walletInfoService")
	private WalletInfoService walletInfoService;
	
	@StateTemplate(init=true)
	class RecvReq{
		RecvReq(){
			
		}
		@OnAccept
		Class<?>accept(FiniteStateMachine fsm, FSMContext ctx,PreWithdrawalsRequest req){
			logger.debug("PreWithdrawalsRequest recv:{}",req);
			PreWithdrawalsResponse resp=(PreWithdrawalsResponse)initResponse(ctx,req,new PreWithdrawalsResponse());
			Result isLogin=userInfoService.isLogin(req.getUid(), req.getToken(),"/wallet/prewithdrawals");
			if(isLogin.getResultCode()!=ResultCode.SUCCESS){
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}
			PreWithdrawalsReqData preWithdrawalsReqData=new PreWithdrawalsReqData();
			preWithdrawalsReqData.setUid(req.getUid());
			PreWithdrawalsResult result=walletInfoService.getPreithdrawals(preWithdrawalsReqData);
			resp.setAmount(result.getAmount());
			resp.setResultCode(result.getResultCode());
			resp.setResultMessage(result.getResultMessage());
			return SendResp.class;
			
		}
	}

}
