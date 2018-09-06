package com.edianniu.pscp.cs.biz.power;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.edianniu.pscp.cs.bean.power.PowerDistributeReqData;
import com.edianniu.pscp.cs.bean.power.PowerDistributeResult;
import com.edianniu.pscp.cs.bean.request.power.PowerDistributeRequest;
import com.edianniu.pscp.cs.bean.response.power.PowerDistributeResponse;
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
 * 电量分布
 * @author zhoujianjian
 * @date 2017年12月8日 下午1:47:30
 */
public class PowerDistributeBiz extends BaseBiz<PowerDistributeRequest, PowerDistributeResponse> {
	private static final Logger logger = LoggerFactory.getLogger(PowerDistributeBiz.class);

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
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, PowerDistributeRequest req) {
			logger.debug("PowerDistributeRequest recv req : {}", req);
			PowerDistributeResponse resp = (PowerDistributeResponse) initResponse(ctx, req, new PowerDistributeResponse());

			Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
			if (isLogin.getResultCode() != ResultCode.SUCCESS) {
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}

			PowerDistributeReqData reqData = new PowerDistributeReqData();
			BeanUtils.copyProperties(req, reqData);
			PowerDistributeResult result = powerInfoService.getPowerDistribute(reqData);
			BeanUtils.copyProperties(result, resp);
			return SendResp.class;
		}
	}
}
