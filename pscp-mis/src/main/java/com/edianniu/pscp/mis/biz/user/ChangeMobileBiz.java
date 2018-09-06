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
import com.edianniu.pscp.mis.bean.request.user.ChangeMobileRequest;
import com.edianniu.pscp.mis.bean.response.user.ChangeMobileResponse;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;

public class ChangeMobileBiz extends BaseBiz<ChangeMobileRequest, ChangeMobileResponse> {
	private static final Logger logger = LoggerFactory
			.getLogger(ChangeMobileBiz.class);
	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;

	@StateTemplate(init = true)
	class RecvReq {
		RecvReq() {
		}

		@OnAccept
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx,
				ChangeMobileRequest req) {
			logger.debug("ChangePwdRequest recv req : {}", req);
			ChangeMobileResponse resp = (ChangeMobileResponse) initResponse(ctx, req, new ChangeMobileResponse());
			
			Result result = userInfoService.changeMobile(req.getOldMobile(), req.getNewMobile(), 
					req.getMsgcodeid(), req.getMsgcode(), req.getPwd());
			resp.setResultCode(result.getResultCode());
			resp.setResultMessage(result.getResultMessage());
			return SendResp.class;
		}
	}
}
