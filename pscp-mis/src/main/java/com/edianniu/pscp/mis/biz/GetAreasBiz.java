package com.edianniu.pscp.mis.biz;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.edianniu.pscp.mis.bean.AreaInfo;
import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.request.GetAreasRequest;
import com.edianniu.pscp.mis.bean.response.GetAreasResponse;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.AreaService;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;

import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;

/**
 * 获取区域列表接口
 *
 * @author cyl
 */
public class GetAreasBiz extends BaseBiz<GetAreasRequest, GetAreasResponse> {
	private static final Logger logger = LoggerFactory
			.getLogger(GetAreasBiz.class);

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
				GetAreasRequest req) {
			logger.debug("GetAreasRequest recv req : {}", req);
			GetAreasResponse resp = initResponse(ctx, req,
					new GetAreasResponse());
			try {
				if (req.getCityId() == null) {
					resp.setResultCode(ResultCode.ERROR_203);
					resp.setResultMessage("cityId不能为空");
					return SendResp.class;
				}
				if (req.getCityId().intValue() <= 0) {
					resp.setResultCode(ResultCode.ERROR_203);
					resp.setResultMessage("cityId必须大于0");
					return SendResp.class;
				}
				List<AreaInfo> list = areaService.getAreaInfos(req.getCityId());
				resp.setAreas(list);
			} catch (Exception e) {
				logger.error("GetAreas:{}", e);
				resp.setResultCode(ResultCode.ERROR_500);
				resp.setResultMessage("系统异常");
			}

			return SendResp.class;
		}
	}
}
