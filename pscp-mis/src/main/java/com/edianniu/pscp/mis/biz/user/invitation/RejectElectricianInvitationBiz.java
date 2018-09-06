package com.edianniu.pscp.mis.biz.user.invitation;

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
import com.edianniu.pscp.mis.bean.request.user.invitation.RejectElectricianInvitationRequest;
import com.edianniu.pscp.mis.bean.response.user.invitation.RejectElectricianInvitationResponse;
import com.edianniu.pscp.mis.bean.user.invitation.RejectElectricianInvitationReq;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.mis.service.dubbo.UserInvitationInfoService;

/**
 * 电工拒绝邀请
 * 
 * @author cyl
 *
 */
public class RejectElectricianInvitationBiz
		extends
		BaseBiz<RejectElectricianInvitationRequest, RejectElectricianInvitationResponse> {
	private static final Logger logger = LoggerFactory
			.getLogger(RejectElectricianInvitationBiz.class);
	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;
	@Autowired
	@Qualifier("userInvitationInfoService")
    private UserInvitationInfoService userInvitationInfoService;
	@StateTemplate(init = true)
	class RecvReq {
		RecvReq() {
		}

		@OnAccept
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx,
				RejectElectricianInvitationRequest req) {
			logger.debug("RejectElectricianInvitationRequest recv req : {}",
					req);
			RejectElectricianInvitationResponse resp = (RejectElectricianInvitationResponse) initResponse(
					ctx, req, new RejectElectricianInvitationResponse());
			Result isLogin = userInfoService.isLogin(req.getUid(),
					req.getToken());
			if (isLogin.getResultCode() != ResultCode.SUCCESS) {
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}
			RejectElectricianInvitationReq rejectElectricianInvitationReq=new RejectElectricianInvitationReq();
			BeanUtils.copyProperties(req, rejectElectricianInvitationReq);
			Result result=userInvitationInfoService.rejectElectricianInvitation(rejectElectricianInvitationReq);
			resp.setResultCode(result.getResultCode());
			resp.setResultMessage(result.getResultMessage());
			return SendResp.class;
		}
	}
}
