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
import com.edianniu.pscp.mis.bean.pay.PayOrderInfo;
import com.edianniu.pscp.mis.bean.pay.QueryPayOrderResult;
import com.edianniu.pscp.mis.bean.request.pay.QueryResultPayRequest;
import com.edianniu.pscp.mis.bean.response.pay.QueryResultPayResponse;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.PayInfoService;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.mis.util.DateUtils;
import com.edianniu.pscp.mis.util.MoneyUtils;

/**
 * 支付结果查询
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年03月30日 下午4:04:50 
 * @version V1.0
 */
public class QueryResultPayBiz extends BaseBiz<QueryResultPayRequest,QueryResultPayResponse>{

	private static final Logger logger = LoggerFactory.getLogger(QueryResultPayBiz.class);
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
		Class<?>accept(FiniteStateMachine fsm, FSMContext ctx,QueryResultPayRequest req){
			logger.debug("QueryResultPayRequest recv req : {}", req);
			QueryResultPayResponse resp=(QueryResultPayResponse)initResponse(ctx,req,new QueryResultPayResponse());
			Result isLogin=userInfoService.isLogin(req.getUid(), req.getToken());
			if(isLogin.getResultCode()!=ResultCode.SUCCESS){
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}
			QueryPayOrderResult result=payInfoService.queryPayOrder(req.getUid(), req.getOrderId());
			
			BeanUtils.copyProperties(result, resp);
			if(result.isSuccess()){
				PayOrderInfo orderInfo=new PayOrderInfo();
				BeanUtils.copyProperties(result, orderInfo,new String[]{"amount","payTime","paySyncTime","payAsyncTime"});
				orderInfo.setAmount(MoneyUtils.format(result.getAmount()));
				orderInfo.setPayTime(DateUtils.format(result.getPayTime(), DateUtils.DATE_TIME_PATTERN));
				orderInfo.setPayAsyncTime(DateUtils.format(result.getPayAsyncTime(), DateUtils.DATE_TIME_PATTERN));
				orderInfo.setPaySyncTime(DateUtils.format(result.getPaySyncTime(), DateUtils.DATE_TIME_PATTERN));
				resp.setOrderInfo(orderInfo);
			}
			return SendResp.class;
			
		}
	}

}
