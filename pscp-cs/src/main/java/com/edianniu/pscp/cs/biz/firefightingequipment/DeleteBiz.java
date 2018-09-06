package com.edianniu.pscp.cs.biz.firefightingequipment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.edianniu.pscp.cs.bean.firefightingequipment.DeleteReqData;
import com.edianniu.pscp.cs.bean.firefightingequipment.DeleteResult;
import com.edianniu.pscp.cs.bean.request.firefightingequipment.DeleteRequest;
import com.edianniu.pscp.cs.bean.response.firefightingequipment.DeleteResponse;
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
 * 消防设施删除
 * @author zhoujianjian
 * @date 2017年11月2日 上午9:57:49
 */
public class DeleteBiz extends BaseBiz<DeleteRequest, DeleteResponse> {
	private static final Logger logger = LoggerFactory.getLogger(DeleteBiz.class);

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
			result = firefightingEquipmentInfoService.deleteFirefightingEquipment(deleteReqData);
			BeanUtils.copyProperties(result, resp);
			return SendResp.class;
		}
	}
}
