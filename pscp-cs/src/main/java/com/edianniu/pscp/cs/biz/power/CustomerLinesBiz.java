package com.edianniu.pscp.cs.biz.power;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.edianniu.pscp.cs.bean.power.CompanyLinesReqData;
import com.edianniu.pscp.cs.bean.power.CompanyLinesResult;
import com.edianniu.pscp.cs.bean.request.power.CustomerLinesRequest;
import com.edianniu.pscp.cs.bean.response.power.CustomerLinesResponse;
import com.edianniu.pscp.cs.biz.BaseBiz;
import com.edianniu.pscp.cs.commons.ResultCode;
import com.edianniu.pscp.cs.service.dubbo.PowerInfoService;
import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;

import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;

/**
 * @author zhoujianjian
 * @date 2017年12月7日 下午4:17:07
 */
public class CustomerLinesBiz extends BaseBiz<CustomerLinesRequest, CustomerLinesResponse> {
	private static final Logger logger = LoggerFactory.getLogger(CustomerLinesBiz.class);

	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;
	
	@Autowired
	@Qualifier("powerInfoService")
	private PowerInfoService powerInfoService;

	@StateTemplate(init = true)
	class RecvReq {
		RecvReq() {
		}

		@OnAccept
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, CustomerLinesRequest req) {
			logger.debug("RoomListRequest recv req : {}", req);
			CustomerLinesResponse resp = (CustomerLinesResponse) initResponse(ctx, req, new CustomerLinesResponse());

			Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
			if (isLogin.getResultCode() != ResultCode.SUCCESS) {
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}

			CompanyLinesReqData reqData = new CompanyLinesReqData();
			BeanUtils.copyProperties(req, reqData);
			CompanyLinesResult result = powerInfoService.getCompanyLines(reqData);
			BeanUtils.copyProperties(result, resp);
			return SendResp.class;
		}
	}
}
