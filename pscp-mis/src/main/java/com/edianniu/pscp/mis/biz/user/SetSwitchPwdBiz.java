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
import com.edianniu.pscp.mis.bean.request.user.SetSwitchPwdRequest;
import com.edianniu.pscp.mis.bean.response.user.SetSwitchPwdResponse;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;

public class SetSwitchPwdBiz extends BaseBiz<SetSwitchPwdRequest, SetSwitchPwdResponse> {
	private static final Logger logger = LoggerFactory.getLogger(SetSwitchPwdBiz.class);
	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;

	@StateTemplate(init = true)
	class RecvReq {
		RecvReq() {
		}

		@OnAccept
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx,
				SetSwitchPwdRequest req) {
			logger.debug("ChangePwdRequest recv req : {}", req);
			SetSwitchPwdResponse resp = (SetSwitchPwdResponse) initResponse(ctx, req, new SetSwitchPwdResponse());
			Result isLogin=userInfoService.isLogin(req.getUid(), req.getToken());
			if(isLogin.getResultCode()!=ResultCode.SUCCESS){
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}
			Result result = userInfoService.setSwitchPwd(req.getUid(), req.getPwd(), 
					req.getRePwd(), req.getMsgcodeid(), req.getMsgcode());
			resp.setResultCode(result.getResultCode());
			resp.setResultMessage(result.getResultMessage());
			return SendResp.class;
		}
	}
}
