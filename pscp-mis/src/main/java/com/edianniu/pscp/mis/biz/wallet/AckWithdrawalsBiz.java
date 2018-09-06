package com.edianniu.pscp.mis.biz.wallet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.request.wallet.AckwithdrawalsRequest;
import com.edianniu.pscp.mis.bean.response.wallet.AckwithdrawalsResponse;
import com.edianniu.pscp.mis.bean.wallet.AckwithdrawalsReqData;
import com.edianniu.pscp.mis.bean.wallet.AckwithdrawalsResult;

import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.mis.service.dubbo.WalletInfoService;

import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;
/**
 * 我的钱包确认提现接口
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年7月11日 下午2:18:41 
 * @version V1.0
 */
public class AckWithdrawalsBiz extends BaseBiz<AckwithdrawalsRequest,AckwithdrawalsResponse>{

	private static final Logger logger = LoggerFactory.getLogger(AckWithdrawalsBiz.class);
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
		Class<?>accept(FiniteStateMachine fsm, FSMContext ctx,AckwithdrawalsRequest req){
			logger.debug("AckwithdrawalsRequest recv:{}",req);
			AckwithdrawalsResponse resp=(AckwithdrawalsResponse)initResponse(ctx,req,new AckwithdrawalsResponse());
			Result isLogin=userInfoService.isLogin(req.getUid(), req.getToken(),"/wallet/ackwithdrawals");
			if(isLogin.getResultCode()!=ResultCode.SUCCESS){
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}
			AckwithdrawalsReqData ackwithdrawalsReqData=new AckwithdrawalsReqData();
			BeanUtils.copyProperties(req, ackwithdrawalsReqData);
			AckwithdrawalsResult result=walletInfoService.addWalletDetail(ackwithdrawalsReqData);
			
			resp.setResultCode(result.getResultCode());
			resp.setResultMessage(result.getResultMessage());
			return SendResp.class;
			
		}
	}

}
