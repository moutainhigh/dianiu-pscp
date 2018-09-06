package com.edianniu.pscp.mis.biz.pay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;
import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.pay.PayOrderListResult;
import com.edianniu.pscp.mis.bean.request.pay.PayOrderListRequest;
import com.edianniu.pscp.mis.bean.response.pay.PayOrderListResponse;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.PayInfoService;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;

/**
 * 支付结果查询
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年03月30日 下午4:04:50 
 * @version V1.0
 */
public class PayOrderListBiz extends BaseBiz<PayOrderListRequest, PayOrderListResponse>{

	private static final Logger logger = LoggerFactory.getLogger(PayOrderListBiz.class);
	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;
	
	@Autowired
	@Qualifier("payInfoService")
	private PayInfoService payInfoService;
	
	@StateTemplate(init=true)
	class RecvReq{
		RecvReq(){
			
		}
		@OnAccept
		Class<?>accept(FiniteStateMachine fsm, FSMContext ctx, PayOrderListRequest req){
			logger.debug("QueryResultPayRequest recv req : {}", req);
			PayOrderListResponse resp=(PayOrderListResponse)initResponse(ctx,req,new PayOrderListResponse());
			Result isLogin=userInfoService.isLogin(req.getUid(), req.getToken());
			if(isLogin.getResultCode()!=ResultCode.SUCCESS){
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}
			PayOrderListResult result=payInfoService.queryPayOrderList(req.getUid(), req.getType(), req.getStatus(), req.getOffset());
			
			BeanUtils.copyProperties(result, resp);
			return SendResp.class;
			
		}
	}

}
