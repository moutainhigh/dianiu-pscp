package com.edianniu.pscp.mis.service.impl;

import com.edianniu.pscp.mis.dao.WorkOrderDao;
import com.edianniu.pscp.mis.domain.WorkOrder;
import com.edianniu.pscp.mis.service.WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * ClassName: DefaultWorkOrderService
 * Author: tandingbo
 * CreateTime: 2017-04-14 14:18
 */
@Service
@Repository("workOrderService")
public class DefaultWorkOrderService implements WorkOrderService {

    @Autowired
    private WorkOrderDao workOrderDao;


    /**
     * 根据主键ID获取工单对象
     *
     * @param id
     * @return
     */
    @Override
    public WorkOrder getEntityById(Long id) {
        return workOrderDao.getEntityById(id);
    }

    /**
     * 根据工单编号获取工单对象
     *
     * @param orderId
     * @return
     */
    @Override
    public WorkOrder getEntityByOrderId(String orderId) {
        return workOrderDao.getEntityByOrderId(orderId);
    }
}
