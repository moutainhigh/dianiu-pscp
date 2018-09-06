package com.edianniu.pscp.cs.biz.needs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.edianniu.pscp.cs.bean.needs.CancelReqData;
import com.edianniu.pscp.cs.bean.needs.CancelResult;
import com.edianniu.pscp.cs.bean.request.needs.CancelRequest;
import com.edianniu.pscp.cs.bean.response.needs.CancelResponse;
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
 * 取消需求
 * @author zhoujianjian
 * 2017年9月14日下午11:30:07
 */
public class CancelBiz extends BaseBiz<CancelRequest, CancelResponse> {
	private static final Logger logger = LoggerFactory.getLogger(CancelBiz.class);

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
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, CancelRequest req) {
			logger.debug("CancelNeedsRequest recv req : {}", req);
			CancelResponse resp = (CancelResponse) initResponse(ctx, req, new CancelResponse());

			Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
			if (isLogin.getResultCode() != ResultCode.SUCCESS) {
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}

			CancelReqData cancelReqData = new CancelReqData();
			BeanUtils.copyProperties(req, cancelReqData);
			CancelResult result = new CancelResult();
			result = needsInfoService.cancelNeeds(cancelReqData.getOrderId(), cancelReqData.getUid());
			BeanUtils.copyProperties(result, resp);
			return SendResp.class;
		}
	}
}
