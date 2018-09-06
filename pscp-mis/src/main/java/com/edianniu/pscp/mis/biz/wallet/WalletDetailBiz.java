package com.edianniu.pscp.mis.biz.wallet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.request.wallet.WalletDetailRequest;
import com.edianniu.pscp.mis.bean.response.wallet.WalletDetailResponse;
import com.edianniu.pscp.mis.bean.wallet.WalletDetailReqData;
import com.edianniu.pscp.mis.bean.wallet.WalletDetailResult;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.mis.service.dubbo.WalletInfoService;

import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;
/**
 * 资金明细详情
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年7月11日 下午2:08:03 
 * @version V1.0
 */
public class WalletDetailBiz extends BaseBiz<WalletDetailRequest,WalletDetailResponse>{

	private static final Logger logger = LoggerFactory.getLogger(WalletDetailBiz.class);
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
		Class<?>accept(FiniteStateMachine fsm, FSMContext ctx,WalletDetailRequest req){
			logger.debug("WalletDetailRequest recv:{}",req);
			WalletDetailResponse resp=(WalletDetailResponse)initResponse(ctx,req,new WalletDetailResponse());
			Result isLogin=userInfoService.isLogin(req.getUid(), req.getToken(),"/wallet/detail");
			if(isLogin.getResultCode()!=ResultCode.SUCCESS){
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}
			WalletDetailReqData walletDetailReqData=new WalletDetailReqData();
			walletDetailReqData.setUserWalleDetailId(req.getId());
			walletDetailReqData.setUid(req.getUid());
			WalletDetailResult result=walletInfoService.getDetail(walletDetailReqData);
			if(result.getResultCode()==ResultCode.SUCCESS){
				resp.setWalletDetail(result.getWalletDetail());
			}
			resp.setResultCode(result.getResultCode());
			resp.setResultMessage(result.getResultMessage());
			return SendResp.class;
			
		}
	}

}
