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
import com.edianniu.pscp.mis.bean.request.socialworkorder.TakeRequest;
import com.edianniu.pscp.mis.bean.response.socialworkorder.TakeResponse;
import com.edianniu.pscp.mis.bean.workorder.SocialWorkOrderTakeReqData;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.SocialWorkOrderInfoService;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
/**
 * 社会电工接单接口
 * @author cyl
 *
 */
public class TakeBiz extends BaseBiz<TakeRequest, TakeResponse> {
	private static final Logger logger = LoggerFactory
			.getLogger(TakeBiz.class);
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
				TakeRequest req) {
			logger.debug("TakeRequest recv req : {}", req);
			TakeResponse resp = (TakeResponse) initResponse(ctx, req, new TakeResponse());
			Result isLogin=userInfoService.isLogin(req.getUid(), req.getToken());
			if(isLogin.getResultCode()!=ResultCode.SUCCESS){
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}
			SocialWorkOrderTakeReqData reqData=new SocialWorkOrderTakeReqData();
			BeanUtils.copyProperties(req, reqData);
			Result result=socialWorkOrderInfoService.take(reqData);
			BeanUtils.copyProperties(result, resp);
			return SendResp.class;
		}
	}
}
