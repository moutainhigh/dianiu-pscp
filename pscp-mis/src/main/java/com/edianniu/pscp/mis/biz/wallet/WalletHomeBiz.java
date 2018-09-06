package com.edianniu.pscp.mis.biz.wallet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.request.wallet.WalletHomeRequest;
import com.edianniu.pscp.mis.bean.response.wallet.WalletHomeResponse;
import com.edianniu.pscp.mis.bean.wallet.WalletHomeResult;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.mis.service.dubbo.WalletInfoService;

import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;
/**
 * 我的钱包首页
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年7月11日 下午2:09:08 
 * @version V1.0
 */
public class WalletHomeBiz extends BaseBiz<WalletHomeRequest,WalletHomeResponse>{

	private static final Logger logger = LoggerFactory.getLogger(WalletHomeBiz.class);
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
		Class<?>accept(FiniteStateMachine fsm, FSMContext ctx,WalletHomeRequest req){
			logger.debug("WalletHomeRequest recv:{}",req);
			WalletHomeResponse resp=(WalletHomeResponse)initResponse(ctx,req,new WalletHomeResponse());
			Result isLogin=userInfoService.isLogin(req.getUid(), req.getToken(),"/wallet/home");
			if(isLogin.getResultCode()!=ResultCode.SUCCESS){
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}
			WalletHomeResult result=walletInfoService.getHome(req.getUid(),true);
			resp.setAmount(result.getAmount());
			resp.setFreezingAmount(result.getFreezingAmount());
			resp.setWalletDetails(result.getWalletDetails());
			resp.setResultCode(result.getResultCode());
			resp.setResultMessage(result.getResultMessage());
			return SendResp.class;
			
		}
	}

}
