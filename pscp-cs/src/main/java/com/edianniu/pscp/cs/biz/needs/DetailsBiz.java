package com.edianniu.pscp.cs.biz.needs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.edianniu.pscp.cs.bean.needs.DetailsReqData;
import com.edianniu.pscp.cs.bean.needs.DetailsResult;
import com.edianniu.pscp.cs.bean.request.needs.DetailsRequest;
import com.edianniu.pscp.cs.bean.response.needs.DetailsResponse;
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
 * 需求详情
 * @author zhoujianjian
 * 2017年9月18日上午11:36:56
 */
public class DetailsBiz extends BaseBiz<DetailsRequest, DetailsResponse> {
	private static final Logger logger = LoggerFactory.getLogger(DetailsBiz.class);

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
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, DetailsRequest req) {
			logger.debug("SaveNeedsRequest recv req : {}", req);
			DetailsResponse resp = (DetailsResponse) initResponse(ctx, req, new DetailsResponse());

			Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
			if (isLogin.getResultCode() != ResultCode.SUCCESS) {
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}
			
			DetailsReqData detailsReqData = new DetailsReqData();
			BeanUtils.copyProperties(req, detailsReqData);
			DetailsResult result = needsInfoService.query(detailsReqData);
			BeanUtils.copyProperties(result, resp);
			return SendResp.class;

		}
	}
}
