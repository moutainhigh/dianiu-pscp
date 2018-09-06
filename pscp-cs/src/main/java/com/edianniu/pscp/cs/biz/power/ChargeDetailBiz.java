package com.edianniu.pscp.cs.biz.power;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.edianniu.pscp.cs.bean.power.ChargeDetailReqData;
import com.edianniu.pscp.cs.bean.power.ChargeDetailResult;
import com.edianniu.pscp.cs.bean.request.power.ChargeDetailRequest;
import com.edianniu.pscp.cs.bean.response.power.ChargeDetailResponse;
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
 * 电费详情
 * @author zhoujianjian
 * @date 2017年12月11日 下午3:57:11
 */
public class ChargeDetailBiz extends BaseBiz<ChargeDetailRequest, ChargeDetailResponse> {
	private static final Logger logger = LoggerFactory.getLogger(ChargeDetailBiz.class);

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
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, ChargeDetailRequest req) {
			logger.debug("PowerDistributeRequest recv req : {}", req);
			ChargeDetailResponse resp = (ChargeDetailResponse) initResponse(ctx, req, new ChargeDetailResponse());

			Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
			if (isLogin.getResultCode() != ResultCode.SUCCESS) {
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}

			ChargeDetailReqData reqData = new ChargeDetailReqData();
			BeanUtils.copyProperties(req, reqData);
			ChargeDetailResult result = powerInfoService.getChargeDetail(reqData);
			BeanUtils.copyProperties(result, resp);
			return SendResp.class;
		}
	}
}
