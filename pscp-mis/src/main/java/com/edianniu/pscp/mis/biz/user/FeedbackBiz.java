package com.edianniu.pscp.mis.biz.user;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.request.user.FeedbackRequest;
import com.edianniu.pscp.mis.bean.response.user.FeedbackResponse;
import com.edianniu.pscp.mis.bean.user.FeedbackReqData;
import com.edianniu.pscp.mis.bean.user.FeedbackResult;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;

import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;

/**
 * @author AbnerElk
 */
public class FeedbackBiz extends BaseBiz<FeedbackRequest, FeedbackResponse> {
	private static final Logger logger = LoggerFactory.getLogger(FeedbackBiz.class);

	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;

	@StateTemplate(init = true)
	class RecvReq {
		RecvReq() {
		}

		@OnAccept
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, FeedbackRequest req) {
			logger.debug("FeedbackRequest recv req : {}", req);
			FeedbackResponse resp = initResponse(ctx, req, new FeedbackResponse());

			Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
			if (isLogin.getResultCode() != ResultCode.SUCCESS) {
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}
            FeedbackReqData feedbackReqData=new FeedbackReqData();
            BeanUtils.copyProperties(req, feedbackReqData);
            feedbackReqData.setClientIp(req.getClientHost());
			FeedbackResult result = userInfoService.feedback(feedbackReqData);
			resp.setResultCode(result.getResultCode());
			resp.setResultMessage(result.getResultMessage());
			return SendResp.class;
		}
	}
}
