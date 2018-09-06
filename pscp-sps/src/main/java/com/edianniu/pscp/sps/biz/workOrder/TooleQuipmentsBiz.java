package com.edianniu.pscp.sps.biz.workOrder;

import com.edianniu.pscp.mis.bean.GetUserInfoResult;
import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.sps.bean.request.workOrder.TooleQuipmentsRequest;
import com.edianniu.pscp.sps.bean.response.workOrder.TooleQuipmentsResponse;
import com.edianniu.pscp.sps.bean.toolequipment.ToolEquipmentVOResult;
import com.edianniu.pscp.sps.biz.BaseBiz;
import com.edianniu.pscp.sps.commons.ResultCode;
import com.edianniu.pscp.sps.service.dubbo.SpsToolEquipmentInfoService;
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
 * ClassName: TooleQuipmentsBiz
 * Author: tandingbo
 * CreateTime: 2017-08-04 09:38
 */
public class TooleQuipmentsBiz extends BaseBiz<TooleQuipmentsRequest, TooleQuipmentsResponse> {
    private static final Logger logger = LoggerFactory.getLogger(TooleQuipmentsBiz.class);

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;
    @Autowired
    @Qualifier("spsToolEquipmentInfoService")
    private SpsToolEquipmentInfoService spsToolEquipmentInfoService;

    @StateTemplate(init = true)
    class RecvReq {
        RecvReq() {
        }

        @OnAccept
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, TooleQuipmentsRequest req) {
            logger.debug("ListRequest recv req : {}", req);
            TooleQuipmentsResponse resp = (TooleQuipmentsResponse) initResponse(ctx, req, new TooleQuipmentsResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            GetUserInfoResult userInfoResult = userInfoService.getUserInfo(req.getUid());
            if (userInfoResult.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(ResultCode.ERROR_500);
                resp.setResultMessage("用户不存在");
                return SendResp.class;
            }
            UserInfo userInfo = userInfoResult.getMemberInfo();
            if (userInfo == null) {
                resp.setResultCode(ResultCode.ERROR_500);
                resp.setResultMessage("用户不存在");
                return SendResp.class;
            }
            ToolEquipmentVOResult result = spsToolEquipmentInfoService.selectAllToolEquipmentVOByCompanyId(userInfo.getCompanyId());
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
