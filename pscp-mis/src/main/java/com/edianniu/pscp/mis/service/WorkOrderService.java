package com.edianniu.pscp.mis.service;

import com.edianniu.pscp.mis.domain.WorkOrder;

/**
 * ClassName: WorkOrderService
 * Author: tandingbo
 * CreateTime: 2017-04-14 14:18
 */
public interface WorkOrderService {
    /**
     * 根据主键ID获取工单对象
     *
     * @param id
     * @return
     */
    WorkOrder getEntityById(Long id);

    WorkOrder getEntityByOrderId(String orderId);
}
