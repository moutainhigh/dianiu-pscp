package com.edianniu.pscp.mis.biz.workorder;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.request.workorder.RoomListRequest;
import com.edianniu.pscp.mis.bean.response.workorder.RoomListResponse;
import com.edianniu.pscp.mis.bean.workorder.room.RoomListReqData;
import com.edianniu.pscp.mis.bean.workorder.room.RoomListResult;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.CustomerDistributionRoomInfoService;
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
 * ClassName: RoomListBiz
 * Author: tandingbo
 * CreateTime: 2017-09-15 16:45
 */
public class RoomListBiz extends BaseBiz<RoomListRequest, RoomListResponse> {
    private static final Logger logger = LoggerFactory.getLogger(RoomListBiz.class);

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;
    @Autowired
    @Qualifier("customerDistributionRoomInfoService")
    private CustomerDistributionRoomInfoService customerDistributionRoomInfoService;

    @StateTemplate(init = true)
    class RecvReq {
        RecvReq() {
        }

        @OnAccept
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, RoomListRequest req) {
            logger.debug("RoomListRequest recv req : {}", req);
            RoomListResponse resp = (RoomListResponse) initResponse(ctx, req, new RoomListResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            RoomListReqData reqData = new RoomListReqData();
            BeanUtils.copyProperties(req, reqData);
            RoomListResult result = customerDistributionRoomInfoService.listRoomInfo(reqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
