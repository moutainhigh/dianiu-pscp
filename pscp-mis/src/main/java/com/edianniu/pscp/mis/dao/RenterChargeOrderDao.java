package com.edianniu.pscp.mis.dao;

import org.apache.ibatis.annotations.Param;

import com.edianniu.pscp.mis.domain.RenterChargeOrder;

/**
 * ElectricChargeOrderDao
 *
 * @author yanlin.chen
 * @version V1.0
 * @email yanlin.chen@edianniu.com
 * @date 2018年03月29日 下午2:35:25
 */
public interface RenterChargeOrderDao extends BaseDao<RenterChargeOrder> {
    public RenterChargeOrder getById(Long id);

    public RenterChargeOrder getByOrderId(@Param("orderId") String orderId);

    public RenterChargeOrder getByPayOrderId(@Param("payOrderId") String payOrderId);

    public int getCountByOrderId(String orderId);

    public int update(RenterChargeOrder electricChargeOrder);
    
    
}
