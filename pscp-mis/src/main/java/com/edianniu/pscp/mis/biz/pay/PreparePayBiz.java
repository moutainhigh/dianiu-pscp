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

import com.edianniu.pscp.mis.bean.AppPackage;
import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.pay.PayMethod;
import com.edianniu.pscp.mis.bean.pay.PreparePayReq;
import com.edianniu.pscp.mis.bean.pay.PreparePayResult;
import com.edianniu.pscp.mis.bean.request.pay.PreparePayRequest;
import com.edianniu.pscp.mis.bean.response.pay.PreparePayResponse;
import com.edianniu.pscp.mis.bean.user.LoginInfo;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.service.dubbo.PayInfoService;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;

/**
 * 准备支付
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年6月26日 下午4:03:32 
 * @version V1.0
 */
public class PreparePayBiz extends BaseBiz<PreparePayRequest,PreparePayResponse>{

	private static final Logger logger = LoggerFactory.getLogger(PreparePayBiz.class);
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
		Class<?>accept(FiniteStateMachine fsm, FSMContext ctx,PreparePayRequest req){
			logger.debug("PreparePayRequest recv req : {}", req);
			PreparePayResponse resp=(PreparePayResponse)initResponse(ctx,req,new PreparePayResponse());
			Result loginResult=userInfoService.isLogin(req.getUid(), req.getToken());
			if(!loginResult.isSuccess()){
				resp.setResultCode(loginResult.getResultCode());
				resp.setResultMessage(loginResult.getResultMessage());
				return SendResp.class;
			}
			PreparePayReq preparePayReq=new PreparePayReq();
			BeanUtils.copyProperties(req, preparePayReq);
			if(StringUtils.isBlank(preparePayReq.getAppPkg())){
				LoginInfo loginInfo=userInfoService.getLoginInfo(req.getUid());
				if(loginInfo!=null)
				preparePayReq.setAppPkg(loginInfo.getAppPkg());
			}
			preparePayReq.setIp(req.getClientHost());
			if(AppPackage.isRenterWapApp(preparePayReq.getAppPkg())){
				preparePayReq.setPayMethod(PayMethod.WAP.getValue());
			}
			else{
				preparePayReq.setPayMethod(PayMethod.APP.getValue());
			}
			PreparePayResult result=payInfoService.preparePay(preparePayReq);
			BeanUtils.copyProperties(result, resp);
			return SendResp.class;
			
		}
	}

}
