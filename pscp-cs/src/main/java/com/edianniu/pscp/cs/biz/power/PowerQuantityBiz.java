package com.edianniu.pscp.cs.biz.power;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.edianniu.pscp.cs.bean.power.PowerQuantityReqData;
import com.edianniu.pscp.cs.bean.power.PowerQuantityResult;
import com.edianniu.pscp.cs.bean.request.power.PowerQuantityRequest;
import com.edianniu.pscp.cs.bean.response.power.PowerQuantityResponse;
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
 * @author zhoujianjian
 * @date 2017年12月7日 下午4:17:07
 */
public class PowerQuantityBiz extends BaseBiz<PowerQuantityRequest, PowerQuantityResponse> {
	private static final Logger logger = LoggerFactory.getLogger(PowerQuantityBiz.class);

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
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, PowerQuantityRequest req) {
			logger.debug("PowerQuantityRequest recv req : {}", req);
			PowerQuantityResponse resp = (PowerQuantityResponse) initResponse(ctx, req, new PowerQuantityResponse());

			Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
			if (isLogin.getResultCode() != ResultCode.SUCCESS) {
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}

			PowerQuantityReqData reqData = new PowerQuantityReqData();
			BeanUtils.copyProperties(req, reqData);
			PowerQuantityResult result = powerInfoService.getPowerQuantity(reqData);
			BeanUtils.copyProperties(result, resp);
			return SendResp.class;
		}
	}
}
