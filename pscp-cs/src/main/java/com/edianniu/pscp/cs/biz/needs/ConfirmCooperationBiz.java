package com.edianniu.pscp.cs.biz.needs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.edianniu.pscp.cs.bean.needs.ConfirmCooperationReqData;
import com.edianniu.pscp.cs.bean.needs.ConfirmCooperationResult;
import com.edianniu.pscp.cs.bean.request.needs.ConfirmCooperationRequest;
import com.edianniu.pscp.cs.bean.response.needs.ConfirmCooperationResponse;
import com.edianniu.pscp.cs.biz.BaseBiz;
import com.edianniu.pscp.cs.service.dubbo.NeedsInfoService;
import com.edianniu.pscp.cs.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.mis.bean.Result;
import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;

/**
 * @author zhoujianjian 2017年9月13日下午5:57:02
 */
public class ConfirmCooperationBiz extends BaseBiz<ConfirmCooperationRequest, ConfirmCooperationResponse> {
	private static final Logger logger = LoggerFactory.getLogger(ConfirmCooperationBiz.class);

	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;

	@Autowired
	@Qualifier("needsInfoService")
	private NeedsInfoService needsInfoService;

	@StateTemplate(init = true)
	class RecvReq {
		RecvReq() {
		}

		@OnAccept
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, ConfirmCooperationRequest req) {
			logger.debug("SaveNeedsRequest recv req : {}", req);
			ConfirmCooperationResponse resp = 
					(ConfirmCooperationResponse) initResponse(ctx, req, new ConfirmCooperationResponse());

			Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
			if (isLogin.getResultCode() != ResultCode.SUCCESS) {
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}
			
			ConfirmCooperationReqData confirmCooperationReqData = new ConfirmCooperationReqData();
			BeanUtils.copyProperties(req, confirmCooperationReqData);
			ConfirmCooperationResult result = 
					needsInfoService.confirmCooperation(confirmCooperationReqData);
			
			BeanUtils.copyProperties(result, resp);
			return SendResp.class;
		}
	}
}
