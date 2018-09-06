package com.edianniu.pscp.mis.biz.wallet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.request.wallet.DaybookRequest;
import com.edianniu.pscp.mis.bean.response.wallet.DaybookResponse;
import com.edianniu.pscp.mis.bean.wallet.DaybookReqData;
import com.edianniu.pscp.mis.bean.wallet.DaybookResult;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.commons.Constants;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.mis.service.dubbo.WalletInfoService;

import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;

public class DaybookBiz extends BaseBiz<DaybookRequest,DaybookResponse>{

	private static final Logger logger = LoggerFactory.getLogger(DaybookBiz.class);
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
		Class<?>accept(FiniteStateMachine fsm, FSMContext ctx,DaybookRequest req){
			DaybookResponse resp=(DaybookResponse)initResponse(ctx,req,new DaybookResponse());
			
			Result isLogin=userInfoService.isLogin(req.getUid(), req.getToken(),"/wallet/daybook");
			if(isLogin.getResultCode()!=ResultCode.SUCCESS){
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}
			
			DaybookReqData data=new DaybookReqData();
			BeanUtils.copyProperties(req, data);
			if(data.getLimit()==0){
				data.setLimit(Constants.DEFAULT_PAGE_SIZE);
			}
			
			DaybookResult result=walletInfoService.getDayBook(data);
			if(result.getResultCode()==ResultCode.SUCCESS){
				resp.setHasNext(result.isHasNext());
				resp.setNextOffset(result.getNextOffset());
				resp.setTotalCount(result.getTotalCount());
				resp.setWalletDetails(result.getWalletDetails());
			}
			resp.setResultCode(result.getResultCode());
			resp.setResultMessage(result.getResultMessage());
			return SendResp.class;
			
		}
	}

}
