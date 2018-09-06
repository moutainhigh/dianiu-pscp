package com.edianniu.pscp.cs.biz.equipment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.edianniu.pscp.cs.bean.equipment.DeleteReqData;
import com.edianniu.pscp.cs.bean.equipment.DeleteResult;
import com.edianniu.pscp.cs.bean.request.equipment.DeleteRequest;
import com.edianniu.pscp.cs.bean.response.equipment.DeleteResponse;
import com.edianniu.pscp.cs.biz.BaseBiz;
import com.edianniu.pscp.cs.service.dubbo.EquipmentInfoService;
import com.edianniu.pscp.cs.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.mis.bean.Result;
import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;

/**
 * 配电房设备删除
 * @author zhoujianjian
 * 2017年9月29日下午10:53:12
 */
public class DeleteBiz extends BaseBiz<DeleteRequest, DeleteResponse> {
	private static final Logger logger = LoggerFactory.getLogger(DeleteBiz.class);

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
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, DeleteRequest req) {
			logger.debug("CancelNeedsRequest recv req : {}", req);
			DeleteResponse resp = (DeleteResponse) initResponse(ctx, req, new DeleteResponse());

			Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
			if (isLogin.getResultCode() != ResultCode.SUCCESS) {
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}

			DeleteReqData deleteReqData = new DeleteReqData();
			BeanUtils.copyProperties(req, deleteReqData);
			DeleteResult result = new DeleteResult();
			result = equipmentInfoService.deleteEquipment(deleteReqData);
			BeanUtils.copyProperties(result, resp);
			return SendResp.class;
		}
	}
}
