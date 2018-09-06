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
import com.edianniu.pscp.mis.bean.request.user.invitation.AgreeElectricianInvitationRequest;
import com.edianniu.pscp.mis.bean.response.user.invitation.AgreeElectricianInvitationResponse;
import com.edianniu.pscp.mis.bean.user.invitation.AgreeElectricianInvitationReq;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.mis.service.dubbo.UserInvitationInfoService;

/**
 * 同意电工邀请
 * 
 * @author cyl
 *
 */
public class AgreeElectricianInvitationBiz
		extends
		BaseBiz<AgreeElectricianInvitationRequest, AgreeElectricianInvitationResponse> {
	private static final Logger logger = LoggerFactory
			.getLogger(AgreeElectricianInvitationBiz.class);
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
				AgreeElectricianInvitationRequest req) {
			logger.debug("AgreeElectricianInvitationRequest recv req : {}", req);
			AgreeElectricianInvitationResponse resp = (AgreeElectricianInvitationResponse) initResponse(
					ctx, req, new AgreeElectricianInvitationResponse());
			Result isLogin = userInfoService.isLogin(req.getUid(),
					req.getToken());
			if (isLogin.getResultCode() != ResultCode.SUCCESS) {
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}
			AgreeElectricianInvitationReq agreeElectricianInvitationReq=new AgreeElectricianInvitationReq();
			BeanUtils.copyProperties(req, agreeElectricianInvitationReq);
			Result result=userInvitationInfoService.agreeElectricianInvitation(agreeElectricianInvitationReq);
			resp.setResultCode(result.getResultCode());
			resp.setResultMessage(result.getResultMessage());
			return SendResp.class;
		}
	}
}
