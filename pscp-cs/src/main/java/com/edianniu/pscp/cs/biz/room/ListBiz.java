package com.edianniu.pscp.cs.biz.room;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.edianniu.pscp.cs.bean.request.room.ListRequest;
import com.edianniu.pscp.cs.bean.response.room.ListResponse;
import com.edianniu.pscp.cs.bean.room.ListReqData;
import com.edianniu.pscp.cs.bean.room.ListResult;
import com.edianniu.pscp.cs.biz.BaseBiz;
import com.edianniu.pscp.cs.commons.ResultCode;
import com.edianniu.pscp.cs.service.dubbo.RoomInfoService;
import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;

import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;

/**
 * 
 * @author zhoujianjian 2017年9月12日下午2:27:04
 */
public class ListBiz extends BaseBiz<ListRequest, ListResponse> {
	private static final Logger logger = LoggerFactory.getLogger(ListBiz.class);

	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;

	@Autowired
	@Qualifier("roomInfoService")
	private RoomInfoService roomInfoService;

	@StateTemplate(init = true)
	class RecvReq {
		RecvReq() {
		}

		@OnAccept
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, ListRequest req) {
			logger.debug("RoomListRequest recv req : {}", req);
			ListResponse resp = (ListResponse) initResponse(ctx, req, new ListResponse());

			Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
			if (isLogin.getResultCode() != ResultCode.SUCCESS) {
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}

			ListReqData listReqData = new ListReqData();
			BeanUtils.copyProperties(req, listReqData);
			ListResult result = roomInfoService.roomListResult(listReqData);
			BeanUtils.copyProperties(result, resp);
			return SendResp.class;
		}
	}
}
