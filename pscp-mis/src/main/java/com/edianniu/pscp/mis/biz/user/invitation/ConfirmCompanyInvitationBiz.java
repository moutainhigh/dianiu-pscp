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
import com.edianniu.pscp.mis.bean.request.user.invitation.ConfirmCompanyInvitationRequest;
import com.edianniu.pscp.mis.bean.response.user.invitation.ConfirmCompanyInvitationResponse;
import com.edianniu.pscp.mis.bean.user.UpdateUserReqData;
import com.edianniu.pscp.mis.bean.user.invitation.ConfirmCompanyInvitationReq;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.mis.service.dubbo.UserInvitationInfoService;

/**
 * 企业邀请确认
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月22日 下午12:10:05
 * @version V1.0
 */
public class ConfirmCompanyInvitationBiz extends
		BaseBiz<ConfirmCompanyInvitationRequest, ConfirmCompanyInvitationResponse> {
	private static final Logger logger = LoggerFactory
			.getLogger(ConfirmCompanyInvitationBiz.class);
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
				ConfirmCompanyInvitationRequest req) {
			logger.debug("ConfirmCompanyInvitationRequest recv req : {}", req);
			ConfirmCompanyInvitationResponse resp = (ConfirmCompanyInvitationResponse) initResponse(
					ctx, req, new ConfirmCompanyInvitationResponse());
			Result isLogin = userInfoService.isLogin(req.getUid(),
					req.getToken());
			if (!isLogin.isSuccess()) {
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}
			ConfirmCompanyInvitationReq confirmCompanyInvitationReq = new ConfirmCompanyInvitationReq();
			BeanUtils.copyProperties(req, confirmCompanyInvitationReq);
			Result result = userInvitationInfoService.confirmCompanyInvitation(confirmCompanyInvitationReq);
			resp.setResultCode(result.getResultCode());
			resp.setResultMessage(result.getResultMessage());
			return SendResp.class;
		}
	}
}
