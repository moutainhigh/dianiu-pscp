package com.edianniu.pscp.mis.biz.pay;

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
import com.edianniu.pscp.mis.bean.pay.StartPayReq;
import com.edianniu.pscp.mis.bean.pay.StartPayResult;
import com.edianniu.pscp.mis.bean.request.pay.StartPayRequest;
import com.edianniu.pscp.mis.bean.response.pay.StartPayResponse;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.PayInfoService;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
/**
 * 发起支付
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年6月26日 下午4:04:50 
 * @version V1.0
 */
public class StartPayBiz extends BaseBiz<StartPayRequest,StartPayResponse>{

	private static final Logger logger = LoggerFactory.getLogger(StartPayBiz.class);
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
		Class<?>accept(FiniteStateMachine fsm, FSMContext ctx,StartPayRequest req){
			logger.debug("StartPayRequest recv req : {}", req);
			StartPayResponse resp=(StartPayResponse)initResponse(ctx,req,new StartPayResponse());
			Result isLogin=userInfoService.isLogin(req.getUid(), req.getToken());
			if(isLogin.getResultCode()!=ResultCode.SUCCESS){
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}
			StartPayReq startPayReq=new StartPayReq();
			BeanUtils.copyProperties(req, startPayReq);
			StartPayResult result=payInfoService.startPay(startPayReq);
			BeanUtils.copyProperties(result, resp);
			return SendResp.class;
			
		}
	}

}
