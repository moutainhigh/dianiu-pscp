package com.edianniu.pscp.sps.biz.workOrder;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.sps.bean.request.workOrder.SaveRequest;
import com.edianniu.pscp.sps.bean.response.workOrder.SaveResponse;
import com.edianniu.pscp.sps.bean.workorder.chieforder.SaveOrUpdateInfo;
import com.edianniu.pscp.sps.bean.workorder.chieforder.SaveOrUpdateResult;
import com.edianniu.pscp.sps.biz.BaseBiz;
import com.edianniu.pscp.sps.commons.ResultCode;
import com.edianniu.pscp.sps.service.dubbo.WorkOrderInfoService;
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
 * 工单信息保存(派单)、修改接口
 * ClassName: SaveBiz
 * Author: tandingbo
 * CreateTime: 2017-06-28 16:06
 */
public class SaveBiz extends BaseBiz<SaveRequest, SaveResponse> {
    private static final Logger logger = LoggerFactory.getLogger(SaveBiz.class);

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;
    @Autowired
    @Qualifier("workOrderInfoService")
    private WorkOrderInfoService workOrderInfoService;

    @StateTemplate(init = true)
    class RecvReq {
        RecvReq() {
        }

        @OnAccept
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, SaveRequest req) {
            logger.debug("SaveResponse recv req : {}", req);
            SaveResponse resp = (SaveResponse) initResponse(ctx, req, new SaveResponse());
            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken(),req.getId()==null?"/sps/workorder/save":"/sps/workorder/update");
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            SaveOrUpdateInfo saveOrUpdateInfo = new SaveOrUpdateInfo();
            BeanUtils.copyProperties(req, saveOrUpdateInfo);
            SaveOrUpdateResult result = workOrderInfoService.saveOrUpdate(saveOrUpdateInfo);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
