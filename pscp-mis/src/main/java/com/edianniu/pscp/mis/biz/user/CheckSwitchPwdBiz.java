package com.edianniu.pscp.mis.biz.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;
import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.request.user.CheckSwitchPwdRequest;
import com.edianniu.pscp.mis.bean.response.user.CheckSwitchPwdResponse;
import com.edianniu.pscp.mis.bean.user.CheckSwitchPwdResult;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;

public class CheckSwitchPwdBiz extends BaseBiz<CheckSwitchPwdRequest, CheckSwitchPwdResponse> {
	private static final Logger logger = LoggerFactory.getLogger(CheckSwitchPwdBiz.class);
	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;

	@StateTemplate(init = true)
	class RecvReq {
		RecvReq() {
		}

		@OnAccept
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx,
				CheckSwitchPwdRequest req) {
			logger.debug("ChangePwdRequest recv req : {}", req);
			CheckSwitchPwdResponse resp = (CheckSwitchPwdResponse) initResponse(ctx, req, new CheckSwitchPwdResponse());
			Result isLogin=userInfoService.isLogin(req.getUid(), req.getToken());
			if(isLogin.getResultCode()!=ResultCode.SUCCESS){
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}
			CheckSwitchPwdResult result = userInfoService.checkSwitchPwd(req.getUid());
			resp.setResultCode(result.getResultCode());
			resp.setResultMessage(result.getResultMessage());
			resp.setIsExist(result.getIsExist());
			return SendResp.class;
		}
	}
}
