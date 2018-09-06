package com.edianniu.pscp.cs.biz.needs;

import com.edianniu.pscp.cs.bean.needs.AuditReqData;
import com.edianniu.pscp.cs.bean.needs.AuditResult;
import com.edianniu.pscp.cs.bean.request.needs.AuditRequest;
import com.edianniu.pscp.cs.bean.response.needs.AuditResponse;
import com.edianniu.pscp.cs.biz.BaseBiz;
import com.edianniu.pscp.cs.service.dubbo.NeedsInfoService;
import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.sps.commons.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;

/**
 * 需求审核
 * @author zhoujianjian
 * 2017年9月22日下午3:36:00
 */
public class AuditBiz extends BaseBiz<AuditRequest, AuditResponse> {
	private static final Logger logger = LoggerFactory.getLogger(AuditBiz.class);

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
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, AuditRequest req) {
			logger.debug("QuoteNeedsRequest recv req : {}", req);
			AuditResponse resp = (AuditResponse) initResponse(ctx, req, new AuditResponse());

			Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
			if (isLogin.getResultCode() != ResultCode.SUCCESS) {
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}

			AuditReqData auditReqData = new AuditReqData();
			BeanUtils.copyProperties(req, auditReqData);
			AuditResult result =needsInfoService.auditNeeds(auditReqData);
			BeanUtils.copyProperties(result, resp);
			return SendResp.class;
		}
	}
}
