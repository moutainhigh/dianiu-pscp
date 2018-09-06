package com.edianniu.pscp.mis.dao;

import com.edianniu.pscp.mis.domain.WorkOrder;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * ClassName: WorkOrderDao
 * Author: tandingbo
 * CreateTime: 2017-04-14 14:17
 */
public interface WorkOrderDao {
    /**
     * 根据主键ID获取工单对象
     *
     * @param id
     * @return
     */
    WorkOrder getEntityById(Long id);

    /**
     * 开始工作
     *
     * @param id
     * @param modifiedUser
     * @param modifiedTime
     * @return
     */
    Integer updateStatus(@Param("id") Long id, @Param("modifiedUser") String modifiedUser,
                         @Param("modifiedTime") Date modifiedTime, @Param("nstatus") Integer nstatus,
                         @Param("bstatus") Integer bstatus);

    WorkOrder getEntityByOrderId(String orderId);

    void updateWorkOrderInfo(@Param("workOrder") WorkOrder workOrder, @Param("bstatus") Integer bstatus);
}
