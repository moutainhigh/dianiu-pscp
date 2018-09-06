package com.edianniu.pscp.cs.biz.room;

import com.edianniu.pscp.cs.bean.request.room.SaveRequest;
import com.edianniu.pscp.cs.bean.response.room.SaveResponse;
import com.edianniu.pscp.cs.bean.room.RoomInfo;
import com.edianniu.pscp.cs.bean.room.SaveResult;
import com.edianniu.pscp.cs.biz.BaseBiz;
import com.edianniu.pscp.cs.commons.ResultCode;
import com.edianniu.pscp.cs.service.dubbo.RoomInfoService;
import com.edianniu.pscp.mis.bean.Result;
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
 * 配电房新增或编辑
 *
 * @author zhoujianjian
 * 2017年9月12日下午6:03:56
 */
public class SaveBiz extends BaseBiz<SaveRequest, SaveResponse> {
    private static final Logger logger = LoggerFactory.getLogger(SaveBiz.class);

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;

    @Autowired
    @Qualifier("roomInfoService")
    private RoomInfoService roomInfoService;

    @StateTemplate(init = true)
    class RecvReq {
        RecvReq() {
        }

        @OnAccept
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, SaveRequest req) {
            logger.debug("SaveRoomRequest recv req : {}", req);
            SaveResponse resp = (SaveResponse) initResponse(ctx, req, new SaveResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            RoomInfo roomInfo = new RoomInfo();
            BeanUtils.copyProperties(req, roomInfo);
            SaveResult result = roomInfoService.save(req.getUid(), roomInfo);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
