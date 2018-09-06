package com.edianniu.pscp.cs.biz.power;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.edianniu.pscp.cs.bean.power.PowerFactorReqData;
import com.edianniu.pscp.cs.bean.power.PowerFactorResult;
import com.edianniu.pscp.cs.bean.request.power.PowerFactorRequest;
import com.edianniu.pscp.cs.bean.response.power.PowerFactorResponse;
import com.edianniu.pscp.cs.biz.BaseBiz;
import com.edianniu.pscp.cs.commons.ResultCode;
import com.edianniu.pscp.cs.service.dubbo.PowerInfoService;
import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;

import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;

/**
 * 功率因数
 * @author zhoujianjian
 * @date 2017年12月8日 下午4:25:29
 */
public class PowerFactorBiz extends BaseBiz<PowerFactorRequest, PowerFactorResponse> {
	private static final Logger logger = LoggerFactory.getLogger(PowerFactorBiz.class);

	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;
	
	@Autowired
	@Qualifier("powerInfoService")
	private PowerInfoService powerInfoService;

	@StateTemplate(init = true)
	class RecvReq {
		RecvReq() {
		}

		@OnAccept
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, PowerFactorRequest req) {
			logger.debug("RoomListRequest recv req : {}", req);
			PowerFactorResponse resp = (PowerFactorResponse) initResponse(ctx, req, new PowerFactorResponse());

			Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
			if (isLogin.getResultCode() != ResultCode.SUCCESS) {
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}

			PowerFactorReqData reqData = new PowerFactorReqData();
			BeanUtils.copyProperties(req, reqData);
			PowerFactorResult result = powerInfoService.getPowerFactor(reqData);
			BeanUtils.copyProperties(result, resp);
			return SendResp.class;
		}
	}
}
