package com.edianniu.pscp.mis.biz.user;

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
import com.edianniu.pscp.mis.bean.request.user.GetUserCenterRequest;
import com.edianniu.pscp.mis.bean.response.user.GetUserCenterResponse;
import com.edianniu.pscp.mis.bean.user.GetUserCenterReq;
import com.edianniu.pscp.mis.bean.user.GetUserCenterResult;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;

public class GetUserCenterBiz extends BaseBiz<GetUserCenterRequest, GetUserCenterResponse> {
	private static final Logger logger = LoggerFactory
			.getLogger(GetUserCenterBiz.class);
	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;

	@StateTemplate(init = true)
	class RecvReq {
		RecvReq() {
		}

		@OnAccept
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx,
				GetUserCenterRequest req) {
			logger.debug("GetUserCenterRequest recv req : {}", req);
			GetUserCenterResponse resp = (GetUserCenterResponse) initResponse(ctx, req,
					new GetUserCenterResponse());
			Result isLogin=userInfoService.isLogin(req.getUid(), req.getToken());
			if(isLogin.getResultCode()!=ResultCode.SUCCESS){
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}
			GetUserCenterReq data=new GetUserCenterReq();
			data.setUid(req.getUid());
			GetUserCenterResult result = userInfoService.getUserCenter(data);
			BeanUtils.copyProperties(result, resp);
			return SendResp.class;
		}
	}
}
