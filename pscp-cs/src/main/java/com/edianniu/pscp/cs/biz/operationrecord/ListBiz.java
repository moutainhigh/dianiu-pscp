package com.edianniu.pscp.cs.biz.operationrecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.edianniu.pscp.cs.bean.operationrecord.ListReqData;
import com.edianniu.pscp.cs.bean.operationrecord.ListResult;
import com.edianniu.pscp.cs.bean.request.operationrecord.ListRequest;
import com.edianniu.pscp.cs.bean.response.operationrecord.ListResponse;
import com.edianniu.pscp.cs.biz.BaseBiz;
import com.edianniu.pscp.cs.service.dubbo.OperationRecordInfoService;
import com.edianniu.pscp.cs.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.mis.bean.Result;
import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;

/**
 * 配电房操作记录列表
 * @author zhoujianjian
 * @date 2017年10月29日 下午10:57:56
 */
public class ListBiz extends BaseBiz<ListRequest, ListResponse> {
	private static final Logger logger = LoggerFactory.getLogger(ListBiz.class);

	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;

	@Autowired
	@Qualifier("operationRecordInfoService")
	private OperationRecordInfoService operationRecordInfoService;

	@StateTemplate(init = true)
	class RecvReq {
		RecvReq() {
		}

		@OnAccept
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, ListRequest req) {
			logger.debug("ListRequest recv req : {}", req);
			ListResponse resp = (ListResponse) initResponse(ctx, req, new ListResponse());

			Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
			if (isLogin.getResultCode() != ResultCode.SUCCESS) {
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}

			ListReqData listReqData = new ListReqData();
			BeanUtils.copyProperties(req, listReqData);
			ListResult result = operationRecordInfoService.operationRecordList(listReqData);
			BeanUtils.copyProperties(result, resp);
			return SendResp.class;
		}
	}
}