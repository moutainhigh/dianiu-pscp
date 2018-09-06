package com.edianniu.pscp.sps.dao;

import com.edianniu.pscp.sps.domain.ElectricianWorkOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 电工工单dao
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-11 14:29:05
 */
public interface ElectricianWorkOrderDao extends BaseDao<ElectricianWorkOrder> {

    public ElectricianWorkOrder getByOrderId(String orderId);

    List<ElectricianWorkOrder> queryList(Map<String, Object> map);

    int updateByWorkOrderId(@Param("workOrderId") Long workOrderId, @Param("modifiedUser") String modifiedUser,
                            @Param("type") Integer type, @Param("bstatus") Integer bstatus, @Param("nstatus") Integer nstatus);

    int updateBySocialWorkOrderId(@Param("socialWorkOrderId") Long socialWorkOrderId, @Param("bstatus") Integer bstatus, @Param("nstatus") Integer nstatus);

    Map<String, Object> getWorkOrderLeader(Long workOrderId);

    List<Map<String, Object>> getCompanyElectricianWorkOrder(Map<String, Object> queryMap);

    List<Map<String, Object>> queryMapListSocialElectrician(@Param("workOrderId") Long workOrderId, @Param("socialWorkOrderId") Long socialWorkOrderId);

    int confirmBatch(ElectricianWorkOrder[] electricianWorkOrders);

    int updateBatchLiquidateInfo(List<ElectricianWorkOrder> electricianWorkOrderList);

    void updateLeader(ElectricianWorkOrder electricianWorkOrder);

    void updateBatch(List<ElectricianWorkOrder> electricianWorkOrderList);

    void updateBatchCheckOption(List<ElectricianWorkOrder> electricianWorkOrderList);

    int getUnLiquidatedSocialElectricianWorkOrderCount(@Param("socialWorkOrderId") Long socialWorkOrderId);

    int getConfirmedSocialElectricianWorkOrderCount(@Param("socialWorkOrderId") Long socialWorkOrderId);

    List<Long> getUnConfirmSocialElectricianWorkOrderIds(@Param("socialWorkOrderId") Long socialWorkOrderId);

    Map<String, Object> queryMapByCondition(Map<String, Object> queryMap);

    List<ElectricianWorkOrder> selectListByCondition(Map<String, Object> queryMap);

    List<Map<String,Object>> selectAllCheckOption(Map<String, Object> selectAllMap);
}
