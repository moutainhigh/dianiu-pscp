package com.edianniu.pscp.sps.service.dubbo;

import com.edianniu.pscp.sps.bean.workorder.evaluate.ListReqData;
import com.edianniu.pscp.sps.bean.workorder.evaluate.ListResult;

/**
 * ClassName: WorkOrderEvaluateInfoService
 * Author: tandingbo
 * CreateTime: 2017-05-15 15:20
 */
public interface WorkOrderEvaluateInfoService {
    ListResult list(ListReqData listReqData);
}
