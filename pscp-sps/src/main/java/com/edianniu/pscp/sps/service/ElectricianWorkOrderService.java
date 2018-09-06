package com.edianniu.pscp.sps.service;


import com.edianniu.pscp.sps.bean.workorder.electrician.*;
import com.edianniu.pscp.sps.commons.PageResult;
import com.edianniu.pscp.sps.domain.ElectricianEvaluate;
import com.edianniu.pscp.sps.domain.ElectricianEvaluateAttachment;
import com.edianniu.pscp.sps.domain.ElectricianWorkOrder;
import com.edianniu.pscp.sps.domain.ElectricianWorkOrderView;

import java.util.List;
import java.util.Map;

/**
 * 电工工单服务
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-08 17:20:03
 */
public interface ElectricianWorkOrderService {

    ElectricianWorkOrder queryObject(Long id);

    ElectricianWorkOrder getByOrderId(String orderId);

    /**
     * 获取未结算社会电工工单数量
     * 不包括取消和审核不通过以及已结算状态
     *
     * @param socialWorkOrderId
     * @return
     */
    public int getUnLiquidatedSocialElectricianWorkOrderCount(Long socialWorkOrderId);

    /**
     * 获取社会电工工单数量
     *
     * @param socialWorkOrderId
     * @return
     */
    public int getConfirmedSocialElectricianWorkOrderCount(Long socialWorkOrderId);

    /**
     * 获取未确认社会电工工单Id
     *
     * @param socialWorkOrderId
     * @return
     */
    public List<Long> getUnConfirmSocialElectricianWorkOrderIds(Long socialWorkOrderId);

    List<ElectricianWorkOrderView> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    public PageResult<ElectricianWorkOrderInfo> query(ListQuery listQuery);

    void save(ElectricianWorkOrder electricianWorkOrder);

    int update(ElectricianWorkOrder electricianWorkOrder);

    int delete(Long id);

    int deleteBatch(Long[] ids);

    /**
     * 社会电工工单审核
     *
     * @param auditReqData
     * @return
     */
    AuditResult audit(AuditReqData auditReqData);

    Map<String, Object> getWorkOrderLeader(Long workOrderId);


    List<Map<String, Object>> queryMapListSocialElectrician(Long workOrderId, Long socialWorkOrderId);

    /**
     * 更新结算数据信息
     *
     * @param electricianWorkOrderList
     * @param evaluateList
     * @param updateEvaluateList
     * @param attachmentList
     * @return
     */
    int updateBatch(List<ElectricianWorkOrder> electricianWorkOrderList, List<ElectricianEvaluate> evaluateList, List<ElectricianEvaluate> updateEvaluateList, List<ElectricianEvaluateAttachment> attachmentList);

    Map<String, Object> queryMapByCondition(Map<String, Object> queryMap);

    List<ElectricianWorkOrder> selectListByCondition(Map<String, Object> queryMap);

    List<Map<String,Object>> selectAllCheckOption(Map<String, Object> selectAllMap);
}
