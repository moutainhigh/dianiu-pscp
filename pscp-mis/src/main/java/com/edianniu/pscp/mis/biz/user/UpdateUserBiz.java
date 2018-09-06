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
import com.edianniu.pscp.mis.bean.request.user.UpdateUserRequest;
import com.edianniu.pscp.mis.bean.response.user.UpdateUserResponse;
import com.edianniu.pscp.mis.bean.user.UpdateUserReqData;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
/**
 * 实名认证
 * @author cyl
 *
 */
public class UpdateUserBiz extends BaseBiz<UpdateUserRequest, UpdateUserResponse> {
	private static final Logger logger = LoggerFactory
			.getLogger(UpdateUserBiz.class);
	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;

	@StateTemplate(init = true)
	class RecvReq {
		RecvReq() {
		}

		@OnAccept
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx,
				UpdateUserRequest req) {
			logger.debug("UpdateUserRequest recv req : {}", req);
			UpdateUserResponse resp = (UpdateUserResponse) initResponse(ctx, req, new UpdateUserResponse());
			Result isLogin=userInfoService.isLogin(req.getUid(), req.getToken());
			if(isLogin.getResultCode()!=ResultCode.SUCCESS){
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}
			UpdateUserReqData updateUserReqData=new UpdateUserReqData();
			BeanUtils.copyProperties(req, updateUserReqData);
			Result result=userInfoService.updateUser(updateUserReqData);
			resp.setResultCode(result.getResultCode());
			resp.setResultMessage(result.getResultMessage());
			return SendResp.class;
		}
	}
}
