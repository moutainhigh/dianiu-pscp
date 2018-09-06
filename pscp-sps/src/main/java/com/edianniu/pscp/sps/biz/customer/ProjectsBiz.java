package com.edianniu.pscp.sps.biz.customer;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.sps.bean.project.ListProjectsReqData;
import com.edianniu.pscp.sps.bean.project.ListProjectsResult;
import com.edianniu.pscp.sps.bean.request.customer.ProjectsRequest;
import com.edianniu.pscp.sps.bean.response.customer.ProjectsResponse;
import com.edianniu.pscp.sps.biz.BaseBiz;
import com.edianniu.pscp.sps.biz.workOrder.SelectDataBiz;
import com.edianniu.pscp.sps.commons.ResultCode;
import com.edianniu.pscp.sps.service.dubbo.EngineeringProjectInfoService;
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
 * 客户项目列表接口
 * ClassName: ProjectsBiz
 * Author: tandingbo
 * CreateTime: 2017-07-12 09:45
 */
public class ProjectsBiz extends BaseBiz<ProjectsRequest, ProjectsResponse> {
    private static final Logger logger = LoggerFactory.getLogger(SelectDataBiz.class);

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;
    @Autowired
    @Qualifier("engineeringProjectInfoService")
    private EngineeringProjectInfoService engineeringProjectInfoService;

    @StateTemplate(init = true)
    class RecvReq {
        RecvReq() {
        }

        @OnAccept
        Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, ProjectsRequest req) {
            logger.debug("ProjectsRequest recv req : {}", req);
            ProjectsResponse resp = (ProjectsResponse) initResponse(ctx, req, new ProjectsResponse());

            Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
            if (isLogin.getResultCode() != ResultCode.SUCCESS) {
                resp.setResultCode(isLogin.getResultCode());
                resp.setResultMessage(isLogin.getResultMessage());
                return SendResp.class;
            }

            ListProjectsReqData reqData = new ListProjectsReqData();
            BeanUtils.copyProperties(req, reqData);
            ListProjectsResult result = engineeringProjectInfoService.getListByCompanyIdAndCustomerId(reqData);
            BeanUtils.copyProperties(result, resp);
            return SendResp.class;
        }
    }
}
