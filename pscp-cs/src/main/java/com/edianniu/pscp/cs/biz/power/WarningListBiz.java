package com.edianniu.pscp.cs.biz.power;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.edianniu.pscp.cs.bean.power.WarningListReqData;
import com.edianniu.pscp.cs.bean.power.WarningListResult;
import com.edianniu.pscp.cs.bean.request.power.WarningListRequest;
import com.edianniu.pscp.cs.bean.response.power.WarningListResponse;
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
 * 报警列表
 * @author zhoujianjian
 * @date 2017年12月11日 下午5:15:36
 */
public class WarningListBiz extends BaseBiz<WarningListRequest, WarningListResponse> {
	private static final Logger logger = LoggerFactory.getLogger(WarningListBiz.class);

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
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, WarningListRequest req) {
			logger.debug("PowerDistributeRequest recv req : {}", req);
			WarningListResponse resp = (WarningListResponse) initResponse(ctx, req, new WarningListResponse());

			Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
			if (isLogin.getResultCode() != ResultCode.SUCCESS) {
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}

			WarningListReqData reqData = new WarningListReqData();
			BeanUtils.copyProperties(req, reqData);
			WarningListResult result = powerInfoService.getWarningList(reqData);
			BeanUtils.copyProperties(result, resp);
			return SendResp.class;
		}
	}
}
