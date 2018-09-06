package com.edianniu.pscp.mis.biz.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.electrician.ElectricianInfo;
import com.edianniu.pscp.mis.bean.request.user.ElectricianAuthRequest;
import com.edianniu.pscp.mis.bean.response.user.ElectricianAuthResponse;
import com.edianniu.pscp.mis.bean.user.ElectricianAuthReq;
import com.edianniu.pscp.mis.bean.user.ElectricianAuthResult;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.ElectricianInfoService;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;

import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;

public class ElectricianAuthBiz extends BaseBiz<ElectricianAuthRequest,ElectricianAuthResponse>{

	private static final Logger logger = LoggerFactory.getLogger(ElectricianAuthBiz.class);
	
	
	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;
	@Autowired
	@Qualifier("electricianInfoService")
	private ElectricianInfoService electricianInfoService;
	
	
	@StateTemplate(init=true)
	class RecvReq{
		 RecvReq(){
			 
		 }
		 @OnAccept
		 Class<?>accept(FiniteStateMachine fsm, FSMContext ctx,ElectricianAuthRequest req){
			 logger.debug("ElectricianAuthRequest recv:{}",req);
			 ElectricianAuthResponse resp=(ElectricianAuthResponse)initResponse(ctx,req,new ElectricianAuthResponse()); 
			 Result isLogin=userInfoService.isLogin(req.getUid(), req.getToken());
				if(isLogin.getResultCode()!=ResultCode.SUCCESS){
					resp.setResultCode(isLogin.getResultCode());
					resp.setResultMessage(isLogin.getResultMessage());
					return SendResp.class;
				}
				ElectricianAuthReq electricianAuthReq=new ElectricianAuthReq();
				ElectricianInfo info=new ElectricianInfo();
				BeanUtils.copyProperties(req, info);
				info.setCertificateImgs(req.getCertificateImgs());
				electricianAuthReq.setInfo(info);				
				electricianAuthReq.setUid(req.getUid());
				ElectricianAuthResult result=electricianInfoService.auth(electricianAuthReq);
				resp.setResultCode(result.getResultCode());
				resp.setResultMessage(result.getResultMessage());
				return SendResp.class;
			 
		 }
	}

}
