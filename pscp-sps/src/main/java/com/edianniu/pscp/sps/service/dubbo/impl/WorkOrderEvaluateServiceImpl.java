package com.edianniu.pscp.sps.service.dubbo.impl;

import com.edianniu.pscp.sps.bean.workorder.evaluate.ListReqData;
import com.edianniu.pscp.sps.bean.workorder.evaluate.ListResult;
import com.edianniu.pscp.sps.bean.workorder.evaluate.WorkOrderEvaluateInfo;
import com.edianniu.pscp.sps.commons.ResultCode;
import com.edianniu.pscp.sps.service.WorkOrderEvaluateService;
import com.edianniu.pscp.sps.service.dubbo.WorkOrderEvaluateInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * ClassName: WorkOrderEvaluateServiceImpl
 * Author: tandingbo
 * CreateTime: 2017-05-15 15:21
 */
@Service
@Repository("workOrderEvaluateInfoService")
public class WorkOrderEvaluateServiceImpl implements WorkOrderEvaluateInfoService {
    private static final Logger logger = LoggerFactory.getLogger(WorkOrderEvaluateServiceImpl.class);

    @Autowired
    @Qualifier("workOrderEvaluateService")
    private WorkOrderEvaluateService workOrderEvaluateService;

    @Override
    public ListResult list(ListReqData listReqData) {
        ListResult result = new ListResult();
        if (listReqData.getWorkOrderId() == null && StringUtils.isBlank(listReqData.getOrderId())) {
            return result;
        }
        try {
            WorkOrderEvaluateInfo workOrderEvaluate = workOrderEvaluateService.selectOneEvaluateInfo(listReqData);
            result.setWorkOrderEvaluate(workOrderEvaluate);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("Electrician WorkOrder list:{}", e);
        }
        return result;
    }
}
