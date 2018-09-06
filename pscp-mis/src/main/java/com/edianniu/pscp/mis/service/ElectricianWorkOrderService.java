package com.edianniu.pscp.mis.service;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.electricianworkorder.ElectricianWorkOrderInfo;
import com.edianniu.pscp.mis.bean.electricianworkorder.ListQueryResultInfo;
import com.edianniu.pscp.mis.bean.query.electricianworkorder.ListQuery;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.commons.PageResult;
import com.edianniu.pscp.mis.domain.Company;
import com.edianniu.pscp.mis.domain.ElectricianWorkOrder;
import com.edianniu.pscp.mis.domain.PayOrder;
import com.edianniu.pscp.mis.domain.UserWalletDetail;
import com.edianniu.pscp.mis.exception.BusinessException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * ClassName: ElectricianWorkOrderService
 * Author: tandingbo
 * CreateTime: 2017-04-13 10:05
 */
public interface ElectricianWorkOrderService {
    PageResult<ListQueryResultInfo> queryList(ListQuery listQuery);
    
    public ElectricianWorkOrder getByOrderId(String orderId);
    
    /**
     * 获取总的订单数量
     * @param uid
     * @param statuss
     * @return
     */
    int getTotalCount(Long uid, int statuss[]);
    /**
     * 获取社会工单总的电工工单数量
     * @param socialWorkOrderId
     * @param statuss
     * @return
     */
    int getTotalCountBySocialWorkOrderId(Long socialWorkOrderId, int statuss[]);
    /**
     * 获取社会工单总的实际费用
     * @param socialWorkOrderId
     * @param statuss
     * @return
     */
    Double getTotalActualFeeBySocialWorkOrderId(Long socialWorkOrderId, int statuss[]);
    /**
     * 获取总的结算费用
     * @param uid
     * @return
     */
    Double getTotalLiquidatedFee(Long uid);
    /**
     * 根据orderId获取工单信息
     *
     * @param uid
     * @param orderId
     * @return
     */
    ElectricianWorkOrder getEntityByOrderId(Long uid, String orderId);
    /**
     * 根据电工工单编号获取社会工单编号
     * @param orderId
     * @return
     */
    String getSocialWorkOrderOrderIdByOrderId(String orderId);

    /**
     * 是否已经接单
     *
     * @param uid
     * @param socialWorkOrderId
     * @return
     */
    boolean isExist(Long uid, Long socialWorkOrderId);

    /**
     * 获取已接社会工单ID列表
     *
     * @param uid
     * @return
     */
    public List<Long> getTakedSocialWorkOrderIds(Long uid);


    /**
     * 获取工单项目负责人信息
     *
     * @param workOrderId
     * @return
     */
    Map<String, Object> getProjectLeader(Long workOrderId);

    /**
     * 修改工单状态
     *
     * @param electricianWorkOrder
     * @param userInfo
     * @param updateEntity
     * @param isLeader
     * @return
     */
    Integer updateEntityByCondition(ElectricianWorkOrder electricianWorkOrder, UserInfo userInfo, ElectricianWorkOrder updateEntity, Boolean isLeader);
    /**
     * 修改工单结算支付状态
     * @param payOrder
     * @return
     */
    boolean updateSettlementPayStatus(PayOrder payOrder);

    /**
     * 创建工单
     *
     * @param electricianWorkOrder
     * @return
     * @throws BusinessException
     */
    public Result create(ElectricianWorkOrder electricianWorkOrder) throws BusinessException;

    /**
     * 获取工单人员列表(电工)
     *
     * @param workOrderId
     * @return
     */
    public List<ElectricianWorkOrderInfo> queryElectricianList(Long workOrderId, int statuss[]);

    /**
     * 获取工单相关电工信息
     *
     * @param uid
     * @param workOrderId
     * @return
     */
    List<Map<String, Object>> getAllElectricianInfo(Long uid, Long workOrderId);

    /**
     * 工单结算
     * 1)修改工单状态
     * 2)扣除服务商冻结余额
     * 3)增加服务商资金明细
     * 4)增加社会电工余额
     * 5)增加社会电工资金明细
     *  
     *
     * @param entity
     * @param companyWalletDetail
     * @param socialElectricianWalletDetail
     * @param company
     * @param totalAmount
     * @param userInfo
     */
    int settlementWorkOrder(ElectricianWorkOrder entity, UserWalletDetail companyWalletDetail, UserWalletDetail socialElectricianWalletDetail, Company company, BigDecimal totalAmount, UserInfo userInfo);

    /**
     * 根据条件获取所有电工工单信息
     *
     * @param selectAllMap
     * @return
     */
    List<Map<String, Object>> selectAllCheckOption(Map<String, Object> selectAllMap);

}
