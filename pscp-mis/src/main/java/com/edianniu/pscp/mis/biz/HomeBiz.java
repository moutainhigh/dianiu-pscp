package com.edianniu.pscp.mis.biz;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.edianniu.pscp.mis.bean.HomeData;
import com.edianniu.pscp.mis.bean.HomeResult;
import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.request.HomeRequest;
import com.edianniu.pscp.mis.bean.response.HomeResponse;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.AppService;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;

import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;


public class HomeBiz extends BaseBiz<HomeRequest,HomeResponse>{
	private static final Logger logger = LoggerFactory
			.getLogger(HomeBiz.class);
	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;
	@Autowired
	private AppService appService;
	
	@StateTemplate(init = true)
	class RecvReq {
		RecvReq() {
		}

		@OnAccept
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx,
				HomeRequest req) {
			logger.debug("ChangePwdRequest recv req : {}", req);
			HomeResponse resp = (HomeResponse) initResponse(ctx, req, new HomeResponse());
			
			Result isLogin= userInfoService.isLogin(req.getUid(), req.getToken());
			if(isLogin.getResultCode()!=ResultCode.SUCCESS){
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}
			
			HomeData data=new HomeData();
			data.setUid(req.getUid());
			data.setNodesVersion(req.getNodesVersion());
			HomeResult result=appService.home(data);
			
			return SendResp.class;
		}
	}
}
