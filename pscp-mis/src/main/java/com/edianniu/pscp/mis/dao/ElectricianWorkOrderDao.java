package com.edianniu.pscp.mis.dao;

import com.edianniu.pscp.mis.bean.electricianworkorder.ElectricianWorkOrderInfo;
import com.edianniu.pscp.mis.bean.electricianworkorder.ListQueryResultInfo;
import com.edianniu.pscp.mis.bean.query.electricianworkorder.ListQuery;
import com.edianniu.pscp.mis.domain.ElectricianWorkOrder;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ElectricianWorkOrderDao Author: tandingbo CreateTime: 2017-04-13
 * 10:42
 */
public interface ElectricianWorkOrderDao {
    List<ListQueryResultInfo> queryList(ListQuery listQuery);

    int getCountByUidAndStatus(@Param(value = "uid") Long uid,
                               @Param(value = "statuss") int statuss[]);
    
    int getCountBySocialWorkOrderIdAndStatus(@Param(value = "socialWorkOrderId") Long socialWorkOrderId,
            @Param(value = "statuss") int statuss[]);
    
    Double getTotalActualFeeBySocialWorkOrderId(@Param(value = "socialWorkOrderId") Long socialWorkOrderId,
            @Param(value = "statuss") int statuss[]);
    
    Double getTotalLiquidatedFeeByUid(@Param(value = "uid") Long uid);

    ElectricianWorkOrder getByUidAndOrderId(@Param("uid") Long uid,
                                            @Param("orderId") String orderId);
    
    ElectricianWorkOrder getByOrderId(@Param("orderId") String orderId);
    
    String getSocialWorkOrderOrderIdByOrderId(@Param("orderId") String orderId);

    int getCountByUidAndSocialWorkOrderIdAndStatus(
            @Param(value = "uid") Long uid,
            @Param(value = "socialWorkOrderId") Long socialWorkOrderId,
            @Param(value = "statuss") int statuss[]);

    List<Long> getTakedSocialWorkOrderIdsByUid(
            @Param(value = "uid") Long uid);

    /**
     * 获取工单项目负责人信息
     *
     * @param workOrderId
     * @return
     */
    Map<String, Object> getProjectLeader(Long workOrderId);

    /**
     * 修改工单信息
     *
     * @param updateEntity
     * @return
     */
    Integer updateEntityByCondition(ElectricianWorkOrder updateEntity);
    /**
     * 修改工单结算支付状态
     * @param electricianWorkOrder
     * @return
     */
    Integer updateSettlementPayStatus(ElectricianWorkOrder electricianWorkOrder);

    /**
     * 获取记录总数
     *
     * @param listQuery
     * @return
     */
    Integer queryCount(ListQuery listQuery);

    /**
     * 保存订单信息
     *
     * @param electricianWorkOrder
     * @return
     */
    Integer save(ElectricianWorkOrder electricianWorkOrder);

    List<ElectricianWorkOrderInfo> queryElectricianList(@Param(value = "workOrderId") Long workOrderId, @Param(value = "statuss") int[] statuss);

    /**
     * 获取工单相关电工信息
     *
     * @param uid
     * @param workOrderId
     * @return
     */
    List<Map<String, Object>> getAllElectricianInfo(@Param(value = "uid") Long uid, @Param(value = "workOrderId") Long workOrderId);

    /**
     * 根据条件获取所有检修信息信息
     *
     * @param selectAllMap
     * @return
     */
    List<Map<String, Object>> selectAllCheckOption(Map<String, Object> selectAllMap);

    /**
     * 根据工单ID获取招募社会电工人数
     *
     * @param workOrderId
     * @return
     */
    Integer queryCountSocial(Long workOrderId);

}
