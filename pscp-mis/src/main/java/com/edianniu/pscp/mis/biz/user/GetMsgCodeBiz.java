package com.edianniu.pscp.mis.biz.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;


import com.edianniu.pscp.mis.bean.GetMsgCodeResult;
import com.edianniu.pscp.mis.bean.GetUserInfoResult;
import com.edianniu.pscp.mis.bean.response.user.GetMsgCodeResponse;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.bean.request.user.GetMsgCodeRequest;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.commons.Constants;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;

public class GetMsgCodeBiz extends
		BaseBiz<GetMsgCodeRequest, GetMsgCodeResponse> {
	private static final Logger logger = LoggerFactory
			.getLogger(GetMsgCodeBiz.class);

	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;

	@StateTemplate(init = true)
	class RecvReq {
		RecvReq() {
		}

		@OnAccept
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx,
				GetMsgCodeRequest req) {
			GetMsgCodeBiz.logger
					.debug("GetMsgCodeRequest recv req : {}", req);
			GetMsgCodeResponse resp = (GetMsgCodeResponse) GetMsgCodeBiz.this
					.initResponse(ctx, req, new GetMsgCodeResponse());

			int type = req.getType();
			String mobile = req.getMobile();
			
			// 如果是更换手机号码，需先作验证（暂支持已认证的服务商）
			if (type == Constants.GET_MSG_CODE_TYPE_CHANGE_MOBILE) {
				GetUserInfoResult userInfoResult = userInfoService.getUserInfo(req.getUid());
				if(! userInfoResult.isSuccess()){
					resp.setResultCode(userInfoResult.getResultCode());
					resp.setResultMessage(userInfoResult.getResultMessage());
					return SendResp.class;
				}
				UserInfo userInfo = userInfoResult.getMemberInfo();
				if (null == userInfo) {
					resp.setResultCode(ResultCode.ERROR_401);
					resp.setResultMessage("用户信息不存在");
					return SendResp.class;
				}
				if (!userInfo.isFacilitator()) {
					resp.setResultCode(ResultCode.ERROR_401);
					resp.setResultMessage("仅认证通过的服务商可更换手机号码");
					return SendResp.class;
				}
				String oldMobile = userInfo.getMobile();
				if (oldMobile.compareTo(mobile.trim()) == 0) {
					resp.setResultCode(ResultCode.ERROR_401);
					resp.setResultMessage("新旧号码相同");
					return SendResp.class;
				}
			}
			
			GetMsgCodeResult result = userInfoService.getMsgCode(mobile, type);
			resp.setResultCode(result.getResultCode());
			resp.setResultMessage(result.getResultMessage());
			resp.setMsgcodeid(result.getMsgCodeId());
			return SendResp.class;
		}
	}
}
