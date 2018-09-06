package com.edianniu.pscp.sps.service;

import com.edianniu.pscp.sps.bean.workorder.evaluate.ListReqData;
import com.edianniu.pscp.sps.bean.workorder.evaluate.WorkOrderEvaluateInfo;

import java.util.List;

/**
 * ClassName: WorkOrderEvaluateService
 * Author: tandingbo
 * CreateTime: 2017-05-15 15:19
 */
public interface WorkOrderEvaluateService {
    List<WorkOrderEvaluateInfo> selectPageEvaluateInfo(ListReqData listReqData);

    WorkOrderEvaluateInfo selectOneEvaluateInfo(ListReqData listReqData);
}
