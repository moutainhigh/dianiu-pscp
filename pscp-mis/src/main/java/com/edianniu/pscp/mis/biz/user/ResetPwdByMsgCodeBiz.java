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
import com.edianniu.pscp.mis.bean.request.user.ResetPwdByMsgCodeRequest;
import com.edianniu.pscp.mis.bean.response.user.ResetPwdByMsgCodeResponse;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;

public class ResetPwdByMsgCodeBiz extends
		BaseBiz<ResetPwdByMsgCodeRequest, ResetPwdByMsgCodeResponse> {
	private static final Logger logger = LoggerFactory
			.getLogger(ResetPwdByMsgCodeBiz.class);
	
	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;

	@StateTemplate(init = true)
	class RecvReq {
		RecvReq() {
		}

		@OnAccept
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx,
				ResetPwdByMsgCodeRequest req) {
			ResetPwdByMsgCodeBiz.logger.debug(
					"resetPwdByMsgCode recv req : {}", req);
			String mobile = req.getMobile();
			String passwd = req.getPasswd();
			String msgCode = req.getMsgcode();
			String msgCodeId = req.getMsgcodeid();
			ResetPwdByMsgCodeResponse resp = (ResetPwdByMsgCodeResponse) initResponse(ctx, req, new ResetPwdByMsgCodeResponse());
			Result result=userInfoService.resetPwd(mobile, passwd, msgCodeId,msgCode);
			resp.setResultCode(result.getResultCode());
			resp.setResultMessage(result.getResultMessage());
			
			return SendResp.class;
		}
	}
}
