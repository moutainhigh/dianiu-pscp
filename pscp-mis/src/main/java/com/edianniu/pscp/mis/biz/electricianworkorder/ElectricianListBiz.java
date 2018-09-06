package com.edianniu.pscp.mis.biz.electricianworkorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.electricianworkorder.ElectricianListReqData;
import com.edianniu.pscp.mis.bean.electricianworkorder.ElectricianListResult;
import com.edianniu.pscp.mis.bean.request.electricianworkorder.ElectricianListRequest;
import com.edianniu.pscp.mis.bean.response.electricianworkorder.ElectricianListResponse;
import com.edianniu.pscp.mis.biz.BaseBiz;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.ElectricianWorkOrderInfoService;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;

/**
 * 电工工单電工列表接口
 *
 * @author yanlin.chen
 * @version V1.0
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月21日 下午4:19:21
 */
public class ElectricianListBiz extends BaseBiz<ElectricianListRequest, ElectricianListResponse> {
    private static final Logger logger = LoggerFactory.getLogger(ElectricianListBiz.class);

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;

    @Autowired
    @Qualifier("electricianWorkOrderInfoService")
    private ElectricianWorkOrderInfoService electricianWorkOrderInfoService;

    @StateTemplate(init = true)
    class RecvReq {
        RecvReq() {
        }

        @OnAccept
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, ElectricianListRequest req) {
            logger.debug("ElectricianListRequest recv req : {}", req);

            ElectricianListResponse resp = (ElectricianListResponse) initResponse(ctx, req, new ElectricianListResponse());
            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            ElectricianListReqData electricianListReqData = new ElectricianListReqData();
            electricianListReqData.setUid(req.getUid());
            electricianListReqData.setOrderId(req.getOrderId());
            ElectricianListResult result = electricianWorkOrderInfoService.electricianList(electricianListReqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
