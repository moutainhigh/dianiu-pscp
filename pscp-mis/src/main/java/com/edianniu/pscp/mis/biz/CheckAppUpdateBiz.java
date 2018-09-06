package com.edianniu.pscp.mis.biz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.edianniu.pscp.mis.bean.CheckAppUpdateReqData;
import com.edianniu.pscp.mis.bean.CheckAppUpdateResult;
import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.request.CheckAppUpdateRequest;
import com.edianniu.pscp.mis.bean.response.CheckAppUpdateResponse;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.AppService;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;

import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;



/**
 * App自更新检查接口
 *
 * @author cyl
 */
public class CheckAppUpdateBiz extends BaseBiz<CheckAppUpdateRequest, CheckAppUpdateResponse> {
    private static final Logger logger = LoggerFactory.getLogger(CheckAppUpdateBiz.class);

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;
    @Autowired
    @Qualifier("appService")
    private AppService appService;

    @StateTemplate(init = true)
    class RecvReq {
        RecvReq() {
        }

        @OnAccept
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, CheckAppUpdateRequest req) {
            logger.debug("CheckAppUpdateRequest recv req : {}", req);
            CheckAppUpdateResponse resp = initResponse(ctx, req, new CheckAppUpdateResponse());

            // 判断用户登录状态
          
            Result isLoginRes = userInfoService.isLogin(req.getUid(), req.getToken());
//            if(isLoginRes.getResultCode()==ResultCode.ERROR_495){
//            	resp.setResultCode(isLoginRes.getResultCode());
//                resp.setResultMessage(isLoginRes.getResultMessage());
//                return SendResp.class;
//            }
           
            boolean isLogin = isLoginRes.getResultCode() == ResultCode.SUCCESS;
            
            // 获取用户首页显示所需的内容
            CheckAppUpdateReqData checkAppUdpateReqData=new CheckAppUpdateReqData();
            checkAppUdpateReqData.setUid(req.getUid());
            checkAppUdpateReqData.setAppPkg(req.getAppPkg());
            checkAppUdpateReqData.setAppVer(req.getAppVer());
            checkAppUdpateReqData.setOsType(req.getOsType());
            checkAppUdpateReqData.setOsVer(req.getOsVersion());
            CheckAppUpdateResult result = appService.checkAppUpdate(checkAppUdpateReqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
