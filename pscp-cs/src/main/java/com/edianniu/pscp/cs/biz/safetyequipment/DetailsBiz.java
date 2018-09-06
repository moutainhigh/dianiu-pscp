package com.edianniu.pscp.cs.biz.safetyequipment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.edianniu.pscp.cs.bean.request.safetyequipment.DetailsRequest;
import com.edianniu.pscp.cs.bean.response.safetyequipment.DetailsResponse;
import com.edianniu.pscp.cs.bean.safetyequipment.DetailsReqData;
import com.edianniu.pscp.cs.bean.safetyequipment.DetailsResult;
import com.edianniu.pscp.cs.biz.BaseBiz;
import com.edianniu.pscp.cs.service.dubbo.SafetyEquipmentInfoService;
import com.edianniu.pscp.cs.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.mis.bean.Result;
import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;

/**
 * 配电房安全用具详情
 * @author zhoujianjian
 * @date 2017年11月1日 上午9:40:03
 */
public class DetailsBiz extends BaseBiz<DetailsRequest, DetailsResponse> {
	private static final Logger logger = LoggerFactory.getLogger(DetailsBiz.class);

	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;

	@Autowired
	@Qualifier("safetyEquipmentInfoService")
	private SafetyEquipmentInfoService safetyEquipmentInfoService;

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
			DetailsResult result = safetyEquipmentInfoService.getSafetyEquipmentDetails(detailsReqData);
			BeanUtils.copyProperties(result, resp);
			return SendResp.class;
		}
	}
}
