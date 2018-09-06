package com.edianniu.pscp.mis.biz.company;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.company.AddressInfo;
import com.edianniu.pscp.mis.bean.company.CompanyMemberType;
import com.edianniu.pscp.mis.bean.company.CompanySaveOrAuthReqData;
import com.edianniu.pscp.mis.bean.company.CompanySaveOrAuthResult;
import com.edianniu.pscp.mis.bean.company.CompanyInfo;
import com.edianniu.pscp.mis.bean.request.company.CompanySaveOrAuthRequest;
import com.edianniu.pscp.mis.bean.response.company.CompanySaveOrAuthResponse;
import com.edianniu.pscp.mis.bean.user.LoginInfo;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.service.dubbo.CompanyInfoService;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;

import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;

public class CompanySaveOrAuthBiz extends
		BaseBiz<CompanySaveOrAuthRequest, CompanySaveOrAuthResponse> {
	private static final Logger logger = LoggerFactory
			.getLogger(CompanySaveOrAuthBiz.class);

	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;

	@Autowired
	@Qualifier("companyInfoService")
	private CompanyInfoService companyInfoService;

	@StateTemplate(init = true)
	class RecvReq {
		RecvReq() {

		}

		@OnAccept
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx,
				CompanySaveOrAuthRequest req) {
			logger.debug("CompanySaveOrAuthRequest recv:{}", req);
			CompanySaveOrAuthResponse resp = (CompanySaveOrAuthResponse) initResponse(ctx,
					req, new CompanySaveOrAuthResponse());

			Result isLogin = userInfoService.isLogin(req.getUid(),
					req.getToken());
			if (!isLogin.isSuccess()) {
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}

			CompanySaveOrAuthReqData companySaveOrAuthReqData = new CompanySaveOrAuthReqData();
			CompanyInfo companyInfo = new CompanyInfo();
			BeanUtils.copyProperties(req, companyInfo);
			AddressInfo addressInfo=new AddressInfo();
			addressInfo.setProvinceId(req.getProvinceId());
			addressInfo.setCityId(req.getCityId());
			addressInfo.setAreaId(req.getAreaId());
			addressInfo.setAddress(req.getAddress());
			companyInfo.setAddressInfo(addressInfo);
			companyInfo.setMemberId(req.getUid());
			companySaveOrAuthReqData.setCompanyInfo(companyInfo);
			companySaveOrAuthReqData.setUid(req.getUid());
			companySaveOrAuthReqData.setActionType(req.getActionType());
			LoginInfo loginInfo=userInfoService.getLoginInfo(req.getUid());
			if(loginInfo.isFacilitatorApp()){
				companySaveOrAuthReqData.setMemberType(CompanyMemberType.FACILITATOR.getValue());
			}
			else if(loginInfo.isCustomerApp()){
				companySaveOrAuthReqData.setMemberType(CompanyMemberType.CUSTOMER.getValue());
			}
			CompanySaveOrAuthResult result = companyInfoService.saveOrAuth(companySaveOrAuthReqData);
			resp.setResultCode(result.getResultCode());
			resp.setResultMessage(result.getResultMessage());
			return SendResp.class;

		}
	}

}
