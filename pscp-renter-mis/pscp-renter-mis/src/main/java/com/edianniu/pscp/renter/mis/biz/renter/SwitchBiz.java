package com.edianniu.pscp.renter.mis.biz.renter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.renter.mis.bean.request.renter.SwitchRequest;
import com.edianniu.pscp.renter.mis.bean.response.renter.SwitchResponse;
import com.edianniu.pscp.renter.mis.biz.BaseBiz;
import com.edianniu.pscp.renter.mis.commons.ResultCode;
import com.edianniu.pscp.renter.mis.service.dubbo.RenterInfoService;
import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;

public class SwitchBiz extends BaseBiz<SwitchRequest, SwitchResponse> {
	private static final Logger logger = LoggerFactory.getLogger(SwitchBiz.class);

	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;

	@Autowired
	@Qualifier("renterInfoService")
	private RenterInfoService renterInfoService;

	@StateTemplate(init = true)
	class RecvReq {
		RecvReq() {
		}

		@OnAccept
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, SwitchRequest req) {
			logger.debug("SwitchRequest recv req : {}", req);
			SwitchResponse resp = (SwitchResponse) initResponse(ctx, req, new SwitchResponse());

			Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
			if (isLogin.getResultCode() != ResultCode.SUCCESS) {
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}

			com.edianniu.pscp.renter.mis.bean.Result result = renterInfoService.renterSwitch(req.getUid(),req.getToken(), req.getRenterId(), req.getType(), req.getPwd(), req.getMeterId());
			BeanUtils.copyProperties(result, resp);
			return SendResp.class;
			
		}
	}
}