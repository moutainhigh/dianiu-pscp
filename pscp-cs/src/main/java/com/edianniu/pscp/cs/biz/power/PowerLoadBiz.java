package com.edianniu.pscp.cs.biz.power;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.edianniu.pscp.cs.bean.power.PowerLoadReqData;
import com.edianniu.pscp.cs.bean.power.PowerLoadResult;
import com.edianniu.pscp.cs.bean.request.power.PowerLoadRequest;
import com.edianniu.pscp.cs.bean.response.power.PowerLoadResponse;
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
public class PowerLoadBiz extends BaseBiz<PowerLoadRequest, PowerLoadResponse> {
	private static final Logger logger = LoggerFactory.getLogger(PowerLoadBiz.class);

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
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, PowerLoadRequest req) {
			logger.debug("RoomListRequest recv req : {}", req);
			PowerLoadResponse resp = (PowerLoadResponse) initResponse(ctx, req, new PowerLoadResponse());

			Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
			if (isLogin.getResultCode() != ResultCode.SUCCESS) {
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}

			PowerLoadReqData reqData = new PowerLoadReqData();
			BeanUtils.copyProperties(req, reqData);
			PowerLoadResult result = powerInfoService.getPowerLoad(reqData);
			BeanUtils.copyProperties(result, resp);
			return SendResp.class;
		}
	}
}
