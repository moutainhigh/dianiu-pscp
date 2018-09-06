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
import com.edianniu.pscp.mis.bean.request.user.invitation.GetElectricianInvitationRequest;
import com.edianniu.pscp.mis.bean.response.user.invitation.GetElectricianInvitationResponse;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.mis.service.dubbo.UserInvitationInfoService;

/**
 * 获取电工邀请信息
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月22日 下午12:10:05
 * @version V1.0
 */
public class GetElectricianInvitationBiz extends
		BaseBiz<GetElectricianInvitationRequest, GetElectricianInvitationResponse> {
	private static final Logger logger = LoggerFactory
			.getLogger(GetElectricianInvitationBiz.class);
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
				GetElectricianInvitationRequest req) {
			logger.debug("GetElectricianInvitationRequest recv req : {}", req);
			GetElectricianInvitationResponse resp = (GetElectricianInvitationResponse) initResponse(
					ctx, req, new GetElectricianInvitationResponse());
			Result isLogin = userInfoService.isLogin(req.getUid(),
					req.getToken());
			if (!isLogin.isSuccess()) {
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}
			Result result = userInvitationInfoService.getElectricianInvitation(req.getUid());
			BeanUtils.copyProperties(result, resp);
			return SendResp.class;
		}
	}
}
