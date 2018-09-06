package com.edianniu.pscp.mis.biz.pay;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.pay.ConfirmPayReq;
import com.edianniu.pscp.mis.bean.pay.ConfirmPayResult;
import com.edianniu.pscp.mis.bean.request.pay.ConfirmPayRequest;
import com.edianniu.pscp.mis.bean.response.pay.ConfirmPayResponse;
import com.edianniu.pscp.mis.bean.user.LoginInfo;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.PayInfoService;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
/**
 * 确认支付
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年6月26日 下午4:03:19 
 * @version V1.0
 */
public class ConfirmPayBiz extends BaseBiz<ConfirmPayRequest,ConfirmPayResponse>{

	private static final Logger logger = LoggerFactory.getLogger(ConfirmPayBiz.class);
	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;
	
	@Autowired
	@Qualifier("payInfoService")
	private PayInfoService payInfoService;
	
	@StateTemplate(init=true)
	class RecvReq{
		RecvReq(){
			
		}
		@OnAccept
		Class<?>accept(FiniteStateMachine fsm, FSMContext ctx,ConfirmPayRequest req){
			logger.debug("ConfirmPayRequest recv req : {}", req);
			ConfirmPayResponse resp=(ConfirmPayResponse)initResponse(ctx,req,new ConfirmPayResponse());
			Result isLogin=userInfoService.isLogin(req.getUid(), req.getToken());
			if(isLogin.getResultCode()!=ResultCode.SUCCESS){
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}
			
			ConfirmPayReq confirmPayReq=new ConfirmPayReq();
			BeanUtils.copyProperties(req, confirmPayReq);
			if(StringUtils.isBlank(confirmPayReq.getAppPkg())){
				LoginInfo loginInfo=userInfoService.getLoginInfo(req.getUid());
				if(loginInfo!=null)
				confirmPayReq.setAppPkg(loginInfo.getAppPkg());
			}
			ConfirmPayResult result=payInfoService.confirmPay(confirmPayReq);
			BeanUtils.copyProperties(result, resp);
			return SendResp.class;
			
		}
	}

}
