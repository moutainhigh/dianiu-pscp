package com.edianniu.pscp.cs.biz.equipment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.edianniu.pscp.cs.bean.equipment.SaveReqData;
import com.edianniu.pscp.cs.bean.equipment.SaveResult;
import com.edianniu.pscp.cs.bean.request.equipment.SaveRequest;
import com.edianniu.pscp.cs.bean.response.equipment.SaveResponse;
import com.edianniu.pscp.cs.biz.BaseBiz;
import com.edianniu.pscp.cs.service.dubbo.EquipmentInfoService;
import com.edianniu.pscp.cs.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;
import com.edianniu.pscp.mis.bean.Result;

/**
 * 配电房设备新增和编辑
 * @author zhoujianjian
 * 2017年9月29日上午10:35:20
 */
public class SaveBiz extends BaseBiz<SaveRequest, SaveResponse> {
	private static final Logger logger = LoggerFactory.getLogger(SaveBiz.class);

	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;

	@Autowired
	@Qualifier("equipmentInfoService")
	private EquipmentInfoService equipmentInfoService;

	@StateTemplate(init = true)
	class RecvReq {
		RecvReq() {
		}

		@OnAccept
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, SaveRequest req) {
			logger.debug("SaveNeedsRequest recv req : {}", req);
			SaveResponse resp = (SaveResponse) initResponse(ctx, req, new SaveResponse());

			Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
			if (isLogin.getResultCode() != ResultCode.SUCCESS) {
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}

			SaveReqData saveReqData = new SaveReqData();
			BeanUtils.copyProperties(req, saveReqData);

			SaveResult result = equipmentInfoService.saveEquipment(saveReqData);
			BeanUtils.copyProperties(result, resp);
			return SendResp.class;
		}
	}
}
