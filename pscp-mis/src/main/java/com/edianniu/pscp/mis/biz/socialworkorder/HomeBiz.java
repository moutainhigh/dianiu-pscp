package com.edianniu.pscp.mis.biz.socialworkorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.request.socialworkorder.HomeRequest;
import com.edianniu.pscp.mis.bean.response.socialworkorder.HomeResponse;
import com.edianniu.pscp.mis.bean.workorder.SocialWorkOrdeHomeReqData;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.SocialWorkOrderInfoService;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
/**
 * 社会工单首页
 * @author cyl
 *
 */
public class HomeBiz extends BaseBiz<HomeRequest, HomeResponse> {
	private static final Logger logger = LoggerFactory
			.getLogger(HomeBiz.class);
	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;
	@Autowired
	@Qualifier("socialWorkOrderInfoService")
	private SocialWorkOrderInfoService socialWorkOrderInfoService;
	@StateTemplate(init = true)
	class RecvReq {
		RecvReq() {
		}

		@OnAccept
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx,
				HomeRequest req) {
			logger.debug("HomeRequest recv req : {}", req);
			HomeResponse resp = (HomeResponse) initResponse(ctx, req, new HomeResponse());
			Result isLogin=userInfoService.isLogin(req.getUid(), req.getToken());
			if(isLogin.getResultCode()!=ResultCode.SUCCESS){
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}
			SocialWorkOrdeHomeReqData reqData=new SocialWorkOrdeHomeReqData();
			BeanUtils.copyProperties(req, reqData);
			Result result=socialWorkOrderInfoService.home(reqData);
			BeanUtils.copyProperties(result, resp);
			return SendResp.class;
		}
	}
}
