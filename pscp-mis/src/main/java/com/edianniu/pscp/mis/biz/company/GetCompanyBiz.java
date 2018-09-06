package com.edianniu.pscp.mis.biz.company;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.company.GetCompanyInfoReqData;
import com.edianniu.pscp.mis.bean.company.GetCompanyInfoResult;
import com.edianniu.pscp.mis.bean.request.company.GetCompanyRequest;
import com.edianniu.pscp.mis.bean.response.company.GetCompanyResponse;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.service.dubbo.CompanyInfoService;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;

public class GetCompanyBiz extends BaseBiz<GetCompanyRequest,GetCompanyResponse>{
	
	private static final Logger logger = LoggerFactory
			.getLogger(GetCompanyBiz.class);
	
	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;
	@Autowired
	@Qualifier("companyInfoService")
	private CompanyInfoService companyInfoService;
	@StateTemplate(init=true)
	class RecvReq{
		RecvReq(){
			
		}
		@OnAccept
		Class<?>accept(FiniteStateMachine fsm, FSMContext ctx,GetCompanyRequest req){
			GetCompanyResponse resp=(GetCompanyResponse)initResponse(ctx,req ,new GetCompanyResponse());
			logger.debug("getCompanyRequest recv req : {}", req);
			Result isLogin=userInfoService.isLogin(req.getUid(), req.getToken());
			if(!isLogin.isSuccess()){
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}
			GetCompanyInfoReqData getCompanyReq=new GetCompanyInfoReqData();
			getCompanyReq.setUid(req.getUid());
			GetCompanyInfoResult result=companyInfoService.getComapnyInfo(getCompanyReq);
			resp.setResultCode(result.getResultCode());
			resp.setResultMessage(result.getResultMessage());
			resp.setCompanyInfo(result.getCompanyInfo());
			return SendResp.class;
			
		}
	}

	

}
