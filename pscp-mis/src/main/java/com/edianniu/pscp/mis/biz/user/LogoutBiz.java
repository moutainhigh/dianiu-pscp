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
import com.edianniu.pscp.mis.bean.request.user.LogoutRequest;
import com.edianniu.pscp.mis.bean.response.user.LogoutResponse;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;

public class LogoutBiz extends BaseBiz<LogoutRequest, LogoutResponse> {
	private static final Logger logger = LoggerFactory
			.getLogger(LogoutBiz.class);
	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;

	@StateTemplate(init = true)
	class RecvReq {
		RecvReq() {
		}

		@OnAccept
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx,
				LogoutRequest req) {
			LogoutBiz.logger.debug("LogoutRequest recv req : {}", req);
			LogoutResponse resp = (LogoutResponse) initResponse(ctx, req,
					new LogoutResponse());
			Result result = userInfoService
					.logout(req.getUid(), req.getToken());
			resp.setResultCode(result.getResultCode());
			resp.setResultMessage(result.getResultMessage());

			return SendResp.class;
		}
	}
}
