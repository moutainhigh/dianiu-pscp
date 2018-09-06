package com.edianniu.pscp.mis.biz.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;

import com.edianniu.pscp.mis.bean.GetUserInfoResult;
import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.request.user.GetUserRequest;
import com.edianniu.pscp.mis.bean.response.user.GetUserResponse;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;

public class GetUserBiz extends BaseBiz<GetUserRequest, GetUserResponse> {
	private static final Logger logger = LoggerFactory
			.getLogger(GetUserBiz.class);
	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;

	@StateTemplate(init = true)
	class RecvReq {
		RecvReq() {
		}

		@OnAccept
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx,
				GetUserRequest req) {
			logger.debug("GetUserRequest recv req : {}", req);
			GetUserResponse resp = (GetUserResponse) initResponse(ctx, req,
					new GetUserResponse());
			Result isLogin=userInfoService.isLogin(req.getUid(), req.getToken());
			if(isLogin.getResultCode()!=ResultCode.SUCCESS){
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}
			GetUserInfoResult result = userInfoService.getUserInfo(req.getUid());
			resp.setMemberInfo(result.getMemberInfo());
			resp.setCompanyInfo(result.getCompanyInfo());
			resp.setElectricianInfo(result.getElectricianInfo());
			resp.setResultCode(result.getResultCode());
			resp.setResultMessage(result.getResultMessage());
			return SendResp.class;
		}
	}
}
