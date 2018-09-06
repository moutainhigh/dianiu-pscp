package com.edianniu.pscp.mis.biz.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.edianniu.pscp.mis.bean.request.user.RegisterRequest;
import com.edianniu.pscp.mis.bean.response.user.RegisterResponse;
import com.edianniu.pscp.mis.bean.user.RegisterReqData;
import com.edianniu.pscp.mis.bean.user.RegisterResult;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;

import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;

public class RegisterBiz extends BaseBiz<RegisterRequest,RegisterResponse>{

	private static final Logger logger = LoggerFactory.getLogger(RegisterBiz.class);
	
	
	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;
	
	
	@StateTemplate(init=true)
	class RecvReq{
		 RecvReq(){
			 
		 }
		 @OnAccept
		 Class<?>accept(FiniteStateMachine fsm, FSMContext ctx,RegisterRequest req){
			 RegisterResponse resp=(RegisterResponse)initResponse(ctx,req,new RegisterResponse()); 
			 
				RegisterReqData registerReqData=new RegisterReqData();
				BeanUtils.copyProperties(req, registerReqData);
				RegisterResult result=userInfoService.register(registerReqData);
				resp.setResultCode(result.getResultCode());
				resp.setResultMessage(result.getResultMessage());
				if(result.isSuccess()){
					resp.setMemberInfo(result.getUserInfo());
					resp.setElectricianInfo(result.getElectricianInfo());
					resp.setCpmpanyInfo(result.getCompanyInfo());
					resp.setToken(result.getToken());
				}
				return SendResp.class;
			 
		 }
	}

}
