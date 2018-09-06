package com.edianniu.pscp.renter.mis.biz.renter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.renter.mis.bean.renter.OrderDetailReq;
import com.edianniu.pscp.renter.mis.bean.renter.OrderDetailResult;
import com.edianniu.pscp.renter.mis.bean.request.renter.OrderDetailRequest;
import com.edianniu.pscp.renter.mis.bean.response.renter.OrderDetailResponse;
import com.edianniu.pscp.renter.mis.biz.BaseBiz;
import com.edianniu.pscp.renter.mis.commons.ResultCode;
import com.edianniu.pscp.renter.mis.service.dubbo.RenterInfoService;
import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;

public class OrderDetailBiz extends BaseBiz<OrderDetailRequest, OrderDetailResponse> {
	private static final Logger logger = LoggerFactory.getLogger(OrderDetailBiz.class);

	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;
   
	@Autowired
	@Qualifier("renterInfoService")
	private RenterInfoService renterInfoService;

	@StateTemplate(init = true)
	class RecvReq {
		RecvReq() {
		}

		@OnAccept
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, OrderDetailRequest req) {
			logger.debug("RenterOrderDetailRequest recv req : {}", req);
			OrderDetailResponse resp = (OrderDetailResponse) initResponse(ctx, req, new OrderDetailResponse());

			Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
			if (isLogin.getResultCode() != ResultCode.SUCCESS) {
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}

			OrderDetailReq orderDetailReq = new OrderDetailReq();
			BeanUtils.copyProperties(req, orderDetailReq);
			OrderDetailResult result = renterInfoService.getOrderDetail(orderDetailReq);
			BeanUtils.copyProperties(result, resp);
			return SendResp.class;
			
		}
	}
}