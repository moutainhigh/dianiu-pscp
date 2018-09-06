package com.edianniu.pscp.cs.dao;

import com.edianniu.pscp.cs.domain.ElectricianWorkOrder;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ElectricianWorkOrderDao
 * Author: tandingbo
 * CreateTime: 2017-08-14 15:07
 */
public interface ElectricianWorkOrderDao extends BaseDao<ElectricianWorkOrder> {
    Map<String, Object> getWorkOrderLeader(Long workOrderId);

    List<Map<String, Object>> getCompanyElectricianWorkOrder(Long workOrderId);
}
