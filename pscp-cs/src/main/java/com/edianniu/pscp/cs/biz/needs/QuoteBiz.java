package com.edianniu.pscp.cs.biz.needs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.edianniu.pscp.cs.bean.needs.QuoteReqData;
import com.edianniu.pscp.cs.bean.needs.QuoteResult;
import com.edianniu.pscp.cs.bean.request.needs.QuoteRequest;
import com.edianniu.pscp.cs.bean.response.needs.QuoteResponse;
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
 * 需求询价
 * @author zhoujianjian
 * 2017年9月15日上午11:13:53
 */
public class QuoteBiz extends BaseBiz<QuoteRequest, QuoteResponse> {
	private static final Logger logger = LoggerFactory.getLogger(QuoteBiz.class);

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
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, QuoteRequest req) {
			logger.debug("QuoteNeedsRequest recv req : {}", req);
			QuoteResponse resp = (QuoteResponse) initResponse(ctx, req, new QuoteResponse());

			Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
			if (isLogin.getResultCode() != ResultCode.SUCCESS) {
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}

			QuoteResult result = 
					needsInfoService.quoteNeeds(req.getOrderId(), req.getUid(), req.getResponsedOrderIds());
			BeanUtils.copyProperties(result, resp);
			return SendResp.class;
		}
	}
}
