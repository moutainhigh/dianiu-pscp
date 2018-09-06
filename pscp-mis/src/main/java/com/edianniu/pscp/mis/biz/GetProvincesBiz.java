package com.edianniu.pscp.mis.biz;

import com.edianniu.pscp.mis.bean.ProvinceInfo;
import com.edianniu.pscp.mis.bean.request.GetProvincesRequest;
import com.edianniu.pscp.mis.bean.response.GetProvincesResponse;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.AreaService;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;

import java.util.List;

public class GetProvincesBiz extends BaseBiz<GetProvincesRequest, GetProvincesResponse> {
    private static final Logger logger = LoggerFactory
            .getLogger(GetProvincesBiz.class);

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
                        GetProvincesRequest req) {
            logger.debug("GetProvincesRequest recv req : {}", req);
            GetProvincesResponse resp = initResponse(ctx, req,
                    new GetProvincesResponse());
            try {
                List<ProvinceInfo> provinces = areaService.getProvinceInfos();
                resp.setProvinces(provinces);
            } catch (Exception e) {
                resp.setResultCode(ResultCode.ERROR_500);
                resp.setResultMessage("系统异常");
                logger.error("GetCitysBiz {}", e);
            }
            return SendResp.class;

        }
    }

}
