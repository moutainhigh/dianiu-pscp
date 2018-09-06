package com.edianniu.pscp.cs.biz.power;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.edianniu.pscp.cs.bean.power.WarningDetailReqData;
import com.edianniu.pscp.cs.bean.power.WarningDetailResult;
import com.edianniu.pscp.cs.bean.request.power.WarningDetailRequest;
import com.edianniu.pscp.cs.bean.response.power.WarningDetailResponse;
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
 * 报警详情
 * @author zhoujianjian
 * @date 2017年12月11日 下午5:58:07
 */
public class WarningDetailBiz extends BaseBiz<WarningDetailRequest, WarningDetailResponse> {
	private static final Logger logger = LoggerFactory.getLogger(WarningDetailBiz.class);

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
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, WarningDetailRequest req) {
			logger.debug("PowerDistributeRequest recv req : {}", req);
			WarningDetailResponse resp = (WarningDetailResponse) initResponse(ctx, req, new WarningDetailResponse());

			Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
			if (isLogin.getResultCode() != ResultCode.SUCCESS) {
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}

			WarningDetailReqData reqData = new WarningDetailReqData();
			BeanUtils.copyProperties(req, reqData);
			WarningDetailResult result = powerInfoService.getWarningDetail(reqData);
			BeanUtils.copyProperties(result, resp);
			return SendResp.class;
		}
	}
}
