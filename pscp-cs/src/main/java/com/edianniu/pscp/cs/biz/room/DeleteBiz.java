package com.edianniu.pscp.cs.biz.room;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.edianniu.pscp.cs.bean.request.room.DeleteRequest;
import com.edianniu.pscp.cs.bean.response.room.DeleteResponse;
import com.edianniu.pscp.cs.biz.BaseBiz;
import com.edianniu.pscp.cs.commons.ResultCode;
import com.edianniu.pscp.cs.service.dubbo.RoomInfoService;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.sps.bean.project.DeleteResult;
import com.edianniu.pscp.mis.bean.Result;
import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;

/**
 * 配电房新增或编辑
 * @author zhoujianjian
 * 2017年9月12日下午6:03:56
 */
public class DeleteBiz extends BaseBiz<DeleteRequest, DeleteResponse>{
	 private static final Logger logger = LoggerFactory.getLogger(DeleteBiz.class);
	
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
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, DeleteRequest req) {
            logger.debug("SaveRoomRequest recv req : {}", req);
            DeleteResponse resp = 
        		(DeleteResponse) initResponse(ctx, req, new DeleteResponse());

            	Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            	if (isLogin.getResultCode() != ResultCode.SUCCESS) {
	            	resp.setResultCode(isLogin.getResultCode());
	                resp.setResultMessage(isLogin.getResultMessage());
	                return SendResp.class;
	            }            	

            Long id = req.getId();
            DeleteResult result = roomInfoService.delete(id, req.getUid());
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
            	
        }
    }
 
}
