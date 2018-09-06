package com.edianniu.pscp.cs.service.dubbo;

import com.edianniu.pscp.cs.bean.workorder.*;

/**
 * ClassName: WorkOrderInfoService
 * Author: tandingbo
 * CreateTime: 2017-05-12 16:47
 */
public interface WorkOrderInfoService {
    /**
     * 列表
     *
     * @param listReqData
     * @return
     */
    ListResult listWorkOrder(ListReqData listReqData);

    /**
     * 工单详情
     *
     * @param detailsReqData
     * @return
     */
    DetailsResult workOrderDetails(DetailsReqData detailsReqData);

    /**
     * 工单评价
     *
     * @param evaluateReqData
     * @return
     */
    EvaluateResult workOrderEvaluate(EvaluateReqData evaluateReqData);
}
