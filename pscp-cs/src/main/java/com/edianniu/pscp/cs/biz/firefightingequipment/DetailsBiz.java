package com.edianniu.pscp.cs.biz.firefightingequipment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.edianniu.pscp.cs.bean.firefightingequipment.DetailsReqData;
import com.edianniu.pscp.cs.bean.firefightingequipment.DetailsResult;
import com.edianniu.pscp.cs.bean.request.firefightingequipment.DetailsRequest;
import com.edianniu.pscp.cs.bean.response.firefightingequipment.DetailsResponse;
import com.edianniu.pscp.cs.biz.BaseBiz;
import com.edianniu.pscp.cs.service.dubbo.FirefightingEquipmentInfoService;
import com.edianniu.pscp.cs.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.mis.bean.Result;
import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;

/**
 * 消防设施详情
 * @author zhoujianjian
 * @date 2017年11月1日 下午10:02:46
 */
public class DetailsBiz extends BaseBiz<DetailsRequest, DetailsResponse> {
	private static final Logger logger = LoggerFactory.getLogger(DetailsBiz.class);

	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;

	@Autowired
	@Qualifier("firefightingEquipmentInfoService")
	private FirefightingEquipmentInfoService firefightingEquipmentInfoService;

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
			DetailsResult result = firefightingEquipmentInfoService.getFriefightingEquipmentDetails(detailsReqData);
			BeanUtils.copyProperties(result, resp);
			return SendResp.class;
		}
	}
}
