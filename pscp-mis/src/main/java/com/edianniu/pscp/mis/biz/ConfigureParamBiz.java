package com.edianniu.pscp.mis.biz;

import com.edianniu.pscp.mis.bean.ConfigureParamResult;
import com.edianniu.pscp.mis.bean.request.ConfigureParamRequest;
import com.edianniu.pscp.mis.bean.response.ConfigureParamResponse;
import com.edianniu.pscp.mis.service.dubbo.ConfigureParamService;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;

/**
 * ClassName: ConfigureParamBiz
 * Author: tandingbo
 * CreateTime: 2017-04-20 15:23
 */
public class ConfigureParamBiz extends BaseBiz<ConfigureParamRequest, ConfigureParamResponse> {
    private static final Logger logger = LoggerFactory.getLogger(HomeBiz.class);

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;

    @Autowired
    @Qualifier("configureParamService")
    private ConfigureParamService configureParamService;

    @StateTemplate(init = true)
    class RecvReq {
        RecvReq() {
        }

        @OnAccept
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, ConfigureParamRequest req) {
            logger.debug("ConfigureParamRequest recv req : {}", req);
            ConfigureParamResponse resp = (ConfigureParamResponse) initResponse(ctx, req, new ConfigureParamResponse());

            ConfigureParamResult result = configureParamService.buildConfigureParam();
            BeanUtils.copyProperties(result, resp);
            return BaseBiz.SendResp.class;
        }
    }
}
