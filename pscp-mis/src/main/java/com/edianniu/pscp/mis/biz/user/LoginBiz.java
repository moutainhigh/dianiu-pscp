package com.edianniu.pscp.mis.biz.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;

import com.edianniu.pscp.mis.bean.request.user.LoginRequest;
import com.edianniu.pscp.mis.bean.response.user.LoginResponse;
import com.edianniu.pscp.mis.bean.user.LoginReqData;
import com.edianniu.pscp.mis.bean.user.LoginResult;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;

public class LoginBiz extends BaseBiz<LoginRequest, LoginResponse> {
	private static final Logger logger = LoggerFactory
			.getLogger(LoginBiz.class);
	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;

	@StateTemplate(init = true)
	class RecvReq {
		RecvReq() {
		}

		@OnAccept
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, LoginRequest req) {
			logger.debug("LoginRequest recv req : {}", req);
			LoginResponse resp = (LoginResponse) initResponse(ctx, req,
					new LoginResponse());
			LoginReqData loginReqData=new LoginReqData();
			BeanUtils.copyProperties(req, loginReqData);
			LoginResult result = userInfoService.login(loginReqData);
			resp.setResultCode(result.getResultCode());
			resp.setResultMessage(result.getResultMessage());
			resp.setMemberInfo(result.getUserInfo());
			resp.setElectricianInfo(result.getElectricianInfo());
			resp.setCompanyInfo(result.getCompanyInfo());
			resp.setToken(result.getToken());
			return SendResp.class;

		}
	}
}
