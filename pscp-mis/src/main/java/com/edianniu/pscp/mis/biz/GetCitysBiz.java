package com.edianniu.pscp.mis.biz;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.edianniu.pscp.mis.bean.CityInfo;
import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.request.GetCitysRequest;
import com.edianniu.pscp.mis.bean.response.GetCitysResponse;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.AreaService;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;

import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;

public class GetCitysBiz extends BaseBiz<GetCitysRequest, GetCitysResponse> {
	private static final Logger logger = LoggerFactory
			.getLogger(GetCitysBiz.class);

	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;

	@Autowired
	@Qualifier("areaService")
	private AreaService areaService;

	@StateTemplate(init = true)
	class RecvReq {
		RecvReq() {

		}

		@OnAccept
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx,
				GetCitysRequest req) {
			logger.debug("GetCitysRequest recv req : {}", req);
			GetCitysResponse resp = initResponse(ctx, req,
					new GetCitysResponse());
			try {
				List<CityInfo> list = areaService.getCityInfos(req.getProvinceId());
				resp.setCitys(list);
			} catch (Exception e) {
				resp.setResultCode(ResultCode.ERROR_500);
				resp.setResultMessage("系统异常");
				logger.error("GetCitysBiz {}", e);
			}
			return SendResp.class;

		}
	}

}
