package com.edianniu.pscp.cs.service;

import com.edianniu.pscp.cs.bean.workorder.DetailsReqData;
import com.edianniu.pscp.cs.bean.workorder.DetailsResult;
import com.edianniu.pscp.cs.bean.workorder.ListQuery;
import com.edianniu.pscp.cs.bean.workorder.vo.WorkOrderVO;
import com.edianniu.pscp.cs.commons.PageResult;
import com.edianniu.pscp.cs.domain.WorkOrder;
import com.edianniu.pscp.cs.domain.WorkOrderEvaluate;
import com.edianniu.pscp.cs.domain.WorkOrderEvaluateAttachment;

import java.util.List;

/**
 * ClassName: WorkOrderService
 * Author: tandingbo
 * CreateTime: 2017-05-12 16:48
 */
public interface WorkOrderService {
    PageResult<WorkOrderVO> selectPageWorkOrderInfo(ListQuery listQuery);

    DetailsResult getWorkOrderDetails(DetailsReqData detailsReqData);

    WorkOrder getWorkOrderByOrderId(String orderId);

    void updateEvaluateInfo(WorkOrder workOrder, WorkOrderEvaluate workOrderEvaluate, List<WorkOrderEvaluateAttachment> attachmentList);
}
