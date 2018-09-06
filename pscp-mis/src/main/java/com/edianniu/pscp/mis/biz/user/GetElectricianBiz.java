package com.edianniu.pscp.mis.biz.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.request.user.GetElectricianRequest;
import com.edianniu.pscp.mis.bean.response.user.GetElectricianResponse;
import com.edianniu.pscp.mis.bean.user.GetElectricianReq;
import com.edianniu.pscp.mis.bean.user.GetElectricianResult;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.ElectricianInfoService;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;

import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;

public class GetElectricianBiz extends BaseBiz<GetElectricianRequest,GetElectricianResponse>{
	private static final Logger logger = LoggerFactory.getLogger(GetElectricianBiz.class);
	
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
		Class<?>accept(FiniteStateMachine fsm, FSMContext ctx,GetElectricianRequest req){
			GetElectricianResponse resp=(GetElectricianResponse)initResponse(ctx,req, new GetElectricianResponse());
			logger.debug("getElectricianRequest recv req : {}", req);
			Result isLogin=userInfoService.isLogin(req.getUid(), req.getToken());
			if(isLogin.getResultCode()!=ResultCode.SUCCESS){
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}
			GetElectricianReq getElectricianReq=new GetElectricianReq();
			getElectricianReq.setUid(req.getUid());
			GetElectricianResult result=electricianInfoService.get(getElectricianReq);
			resp.setResultCode(result.getResultCode());
			resp.setResultMessage(result.getResultMessage());
			if(result.getResultCode()==ResultCode.SUCCESS){
				resp.setElectricianInfo(result.getInfo());
			}
			return SendResp.class;
			
		}
	}

}
