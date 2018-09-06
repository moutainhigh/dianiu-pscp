package com.edianniu.pscp.mis.biz.workexperience;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.request.workexperience.SaveOrUpdateRequest;
import com.edianniu.pscp.mis.bean.response.workexperience.SaveOrUpdateResponse;
import com.edianniu.pscp.mis.bean.workexperience.SaveOrUpdateReqData;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.ElectricianWorkExperienceInfoService;
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
 * 电工工作经历保存修改接口
 * ClassName: SaveOrUpdateBiz
 * Author: tandingbo
 * CreateTime: 2017-04-19 15:33
 */
public class SaveOrUpdateBiz extends BaseBiz<SaveOrUpdateRequest, SaveOrUpdateResponse> {
    private static final Logger logger = LoggerFactory.getLogger(SaveOrUpdateBiz.class);

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;

    @Autowired
    @Qualifier("electricianWorkExperienceInfoService")
    private ElectricianWorkExperienceInfoService electricianWorkExperienceInfoService;

    @StateTemplate(init = true)
    class RecvReq {
        RecvReq() {
        }

        @OnAccept
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, SaveOrUpdateRequest req) {
            logger.debug("SaveOrUpdateRequest recv req : {}", req);
            SaveOrUpdateResponse resp = (SaveOrUpdateResponse) initResponse(ctx, req, new SaveOrUpdateResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            SaveOrUpdateReqData reqData = new SaveOrUpdateReqData();
            BeanUtils.copyProperties(req, reqData);
            Result result = electricianWorkExperienceInfoService.saveOrUpdate(reqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
