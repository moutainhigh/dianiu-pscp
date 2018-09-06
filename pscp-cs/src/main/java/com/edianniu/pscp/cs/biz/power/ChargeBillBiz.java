package com.edianniu.pscp.cs.biz.power;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.edianniu.pscp.cs.bean.power.ChargeBillReqData;
import com.edianniu.pscp.cs.bean.power.ChargeBillResult;
import com.edianniu.pscp.cs.bean.request.power.ChargeBillRequest;
import com.edianniu.pscp.cs.bean.response.power.ChargeBillResponse;
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
 * 电费单
 * @author zhoujianjian
 * @date 2017年12月8日 下午6:04:11
 */
public class ChargeBillBiz extends BaseBiz<ChargeBillRequest, ChargeBillResponse> {
	private static final Logger logger = LoggerFactory.getLogger(ChargeBillBiz.class);

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
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, ChargeBillRequest req) {
			logger.debug("PowerDistributeRequest recv req : {}", req);
			ChargeBillResponse resp = (ChargeBillResponse) initResponse(ctx, req, new ChargeBillResponse());

			Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
			if (isLogin.getResultCode() != ResultCode.SUCCESS) {
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}

			ChargeBillReqData reqData = new ChargeBillReqData();
			BeanUtils.copyProperties(req, reqData);
			ChargeBillResult result = powerInfoService.getChargeBill(reqData);
			BeanUtils.copyProperties(result, resp);
			return SendResp.class;
		}
	}
}
