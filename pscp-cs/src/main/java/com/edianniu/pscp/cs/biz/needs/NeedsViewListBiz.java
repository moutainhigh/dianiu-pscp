package com.edianniu.pscp.cs.biz.needs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.edianniu.pscp.cs.bean.needs.NeedsViewListReqData;
import com.edianniu.pscp.cs.bean.needs.NeedsViewListResult;
import com.edianniu.pscp.cs.bean.request.needs.NeedsViewListRequest;
import com.edianniu.pscp.cs.bean.response.needs.NeedsViewListResponse;
import com.edianniu.pscp.cs.biz.BaseBiz;
import com.edianniu.pscp.cs.service.dubbo.NeedsInfoService;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;
import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.cs.commons.ResultCode;

/**
 * 需求列表
 * @author zhoujianjian
 * 2017年9月21日下午11:42:57
 */
public class NeedsViewListBiz extends BaseBiz<NeedsViewListRequest, NeedsViewListResponse> {
	private static final Logger logger = LoggerFactory.getLogger(NeedsViewListBiz.class);

	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;

	@Autowired
	@Qualifier("needsInfoService")
	private NeedsInfoService needsInfoService;

	@StateTemplate(init = true)
	class RecvReq {
		RecvReq() {
		}

		@OnAccept
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, NeedsViewListRequest req) {
			logger.debug("SaveNeedsRequest recv req : {}", req);
			NeedsViewListResponse resp = (NeedsViewListResponse) initResponse(ctx, req, new NeedsViewListResponse());

			Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
			if (isLogin.getResultCode() != ResultCode.SUCCESS) {
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}

			NeedsViewListReqData needsViewListReqData = new NeedsViewListReqData();
			BeanUtils.copyProperties(req, needsViewListReqData);
			NeedsViewListResult result = needsInfoService.getNeedsViewList(needsViewListReqData);
			BeanUtils.copyProperties(result, resp);
			return SendResp.class;
		}
	}
}