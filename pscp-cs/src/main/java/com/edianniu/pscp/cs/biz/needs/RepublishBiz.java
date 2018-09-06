package com.edianniu.pscp.cs.biz.needs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.edianniu.pscp.cs.bean.needs.RepublishReqData;
import com.edianniu.pscp.cs.bean.needs.RepublishResult;
import com.edianniu.pscp.cs.bean.request.needs.RepublishRequest;
import com.edianniu.pscp.cs.bean.response.needs.RepublishResponse;
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
 * 需求重新发布
 * @author zhoujianjian
 * 2017年9月14日下午9:37:29
 */
public class RepublishBiz extends BaseBiz<RepublishRequest, RepublishResponse> {
	private static final Logger logger = LoggerFactory.getLogger(RepublishBiz.class);

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
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, RepublishRequest req) {
			logger.debug("RepublishRequest recv req : {}", req);
			RepublishResponse resp = (RepublishResponse) initResponse(ctx, req, new RepublishResponse());

			Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
			if (isLogin.getResultCode() != ResultCode.SUCCESS) {
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}
			
			RepublishReqData republishReqData = new RepublishReqData();
			BeanUtils.copyProperties(req, republishReqData);
			RepublishResult result = needsInfoService.republishNeeds(republishReqData);
			BeanUtils.copyProperties(result, resp);
			return SendResp.class;
		}
	}
}
