package com.edianniu.pscp.mis.service.impl;

import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.mis.bean.DefaultResult;
import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.electricianworkorder.*;
import com.edianniu.pscp.mis.bean.pay.PayStatus;
import com.edianniu.pscp.mis.bean.query.electricianworkorder.ListQuery;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.bean.workorder.SocialWorkOrderStatus;
import com.edianniu.pscp.mis.bean.workorder.WorkOrderType;
import com.edianniu.pscp.mis.commons.PageResult;
import com.edianniu.pscp.mis.dao.*;
import com.edianniu.pscp.mis.domain.*;
import com.edianniu.pscp.mis.exception.BusinessException;
import com.edianniu.pscp.mis.service.ElectricianWorkOrderService;
import com.edianniu.pscp.mis.util.BigDecimalUtil;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * ClassName: DefaultWorkOrderService
 * Author: tandingbo
 * CreateTime: 2017-04-13 10:06
 */
@Service
@Repository("electricianWorkOrderService")
public class DefaultElectricianWorkOrderService implements ElectricianWorkOrderService {

    @Autowired
    private WorkOrderDao workOrderDao;
    @Autowired
    private ElectricianWorkOrderDao electricianWorkOrderDao;
    @Autowired
    private UserWalletDao userWalletDao;
    @Autowired
    private UserWalletDetailDao userWalletDetailDao;
    @Autowired
    private SocialWorkOrderDao socialWorkOrderDao;

    @Override
    public PageResult<ListQueryResultInfo> queryList(ListQuery listQuery) {
        PageResult<ListQueryResultInfo> result = new PageResult<ListQueryResultInfo>();

        int total = electricianWorkOrderDao.queryCount(listQuery);
        int nextOffset = 0;
        if (total > 0) {
            List<ListQueryResultInfo> list = electricianWorkOrderDao.queryList(listQuery);
            if (CollectionUtils.isNotEmpty(list)) {
                for (ListQueryResultInfo listQueryResultInfo : list) {
                    // 列表标题内容
                    listQueryResultInfo.setTitle(listQueryResultInfo.getCheckOption());
                    if (listQueryResultInfo.getWorkOrderLeader().equals(1)) {
                        // 工单负责人工单
                        listQueryResultInfo.setTitle(listQueryResultInfo.getName());
                    }

                    String distance = listQueryResultInfo.getDistance();
                    BigDecimal bigDecimal = new BigDecimal(distance);
                    if (bigDecimal.doubleValue() > 1000D) {
                        BigDecimal bigDecimalDistance = BigDecimalUtil.div(bigDecimal.doubleValue(), 1000D);
                        listQueryResultInfo.setDistance(String.format("%skm", bigDecimalDistance));
                    } else {
                        listQueryResultInfo.setDistance(String.format("%sm", BigDecimalUtil.CovertTwoDecimal(distance)));
                    }

                    // 工单类型名称
                    listQueryResultInfo.setTypeName(WorkOrderType.getDesc(listQueryResultInfo.getType()));
                }

                if (listQuery.getWorkOrderLeader().equals(1)) {
                    // 负责人工单
                    structureLeaderOrderData(list);
                } else {
                    // 电工工单
                    structureElectricianOrderData(list);
                }
            }
            result.setData(list);
            nextOffset = listQuery.getOffset() + list.size();
            result.setNextOffset(nextOffset);
        }
        if (nextOffset > 0 && nextOffset < total) {
            result.setHasNext(true);
        }
        result.setOffset(listQuery.getOffset());
        result.setNextOffset(nextOffset);
        result.setTotal(total);
        return result;
    }

    /**
     * 构建负责人工单数据
     *
     * @param list
     */
    private void structureLeaderOrderData(List<ListQueryResultInfo> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        Set<Long> workOrderIds = new HashSet<>();
        for (ListQueryResultInfo listQueryResultInfo : list) {
            if (listQueryResultInfo.getWorkOrderId() != null) {
                workOrderIds.add(listQueryResultInfo.getWorkOrderId());
            }
        }

        Map<Long, Integer> socialWorkOrderCount = new HashMap<>();
        if (workOrderIds.size() > 0) {
            List<SocialWorkOrder> socialWorkOrderList = socialWorkOrderDao.queryListByWorkOrderIds(workOrderIds.toArray(new Long[]{}));
            if (CollectionUtils.isNotEmpty(socialWorkOrderList)) {
                for (SocialWorkOrder socialWorkOrder : socialWorkOrderList) {
                    Long workOrderId = socialWorkOrder.getWorkOrderId();
                    if (socialWorkOrderCount.containsKey(workOrderId)) {
                        socialWorkOrderCount.put(workOrderId, socialWorkOrderCount.get(workOrderId) + 1);
                    } else {
                        socialWorkOrderCount.put(workOrderId, 1);
                    }
                }
            }
        }

        for (ListQueryResultInfo listQueryResultInfo : list) {
            Long workOrderId = listQueryResultInfo.getWorkOrderId();
            // 企业工单标识
            Map<String, Object> extendInfoMap = new HashMap<>();
            extendInfoMap.put("company", 1);
            // 社会工单标识
            extendInfoMap.put("social", 0);
            Integer count = socialWorkOrderCount.containsKey(workOrderId) ? socialWorkOrderCount.get(workOrderId) : 0;
            if (count > 0) {
                extendInfoMap.put("social", 1);
            }
            listQueryResultInfo.setExtendInfo(JSON.toJSONString(extendInfoMap));
        }
    }

    /**
     * 构建电工工单数据
     *
     * @param list
     */
    private void structureElectricianOrderData(List<ListQueryResultInfo> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        Set<Long> socialWorkOrderIds = new HashSet<>();
        for (ListQueryResultInfo listQueryResultInfo : list) {
            if (listQueryResultInfo.getSocialWorkOrderId() > 0L) {
                socialWorkOrderIds.add(listQueryResultInfo.getSocialWorkOrderId());
            }
//

        }

        Map<Long, String> mapIdToTitle = new HashMap<>();
        if (socialWorkOrderIds.size() > 0) {
            List<Map<String, Object>> mapListIdToContent = socialWorkOrderDao.getMapIdToTitleByIds(socialWorkOrderIds.toArray(new Long[]{}));
            if (CollectionUtils.isNotEmpty(mapListIdToContent)) {
                for (Map<String, Object> map : mapListIdToContent) {
                    if (map.get("title") != null) {
                        mapIdToTitle.put(Long.valueOf(map.get("id").toString()), map.get("title").toString());
                    }
                }
            }
        }

        if (MapUtils.isNotEmpty(mapIdToTitle)) {
            for (ListQueryResultInfo listQueryResultInfo : list) {
                Long id = listQueryResultInfo.getSocialWorkOrderId();
                if (mapIdToTitle.containsKey(id)) {
                    listQueryResultInfo.setTitle(mapIdToTitle.get(id));
                }
            }
        }
    }


    @Override
    public int getTotalCount(Long uid, int[] statuss) {
        return electricianWorkOrderDao.getCountByUidAndStatus(uid, statuss);
    }

    @Override
    public int getTotalCountBySocialWorkOrderId(Long socialWorkOrderId,
                                                int[] statuss) {
        return electricianWorkOrderDao.getCountBySocialWorkOrderIdAndStatus(socialWorkOrderId, statuss);
    }

    @Override
    public Double getTotalActualFeeBySocialWorkOrderId(Long socialWorkOrderId,
                                                       int[] statuss) {
        return electricianWorkOrderDao.getTotalActualFeeBySocialWorkOrderId(socialWorkOrderId, statuss);
    }

    @Override
    public ElectricianWorkOrder getEntityByOrderId(Long uid, String orderId) {
        return electricianWorkOrderDao.getByUidAndOrderId(uid, orderId);
    }

    @Override
    public boolean isExist(Long uid, Long socialWorkOrderId) {
        int statuss[] = {
                ElectricianWorkOrderStatus.FAIL.getValue(),
                ElectricianWorkOrderStatus.UNCONFIRMED.getValue(),
                ElectricianWorkOrderStatus.CONFIRMED.getValue(),
                ElectricianWorkOrderStatus.EXECUTING.getValue(),
                ElectricianWorkOrderStatus.FINISHED.getValue(),
                ElectricianWorkOrderStatus.FEE_CONFIRM.getValue(),
                ElectricianWorkOrderStatus.LIQUIDATED.getValue()
        };
        int count = electricianWorkOrderDao.getCountByUidAndSocialWorkOrderIdAndStatus(uid, socialWorkOrderId, statuss);
        if (count > 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<Long> getTakedSocialWorkOrderIds(Long uid) {
        return electricianWorkOrderDao.getTakedSocialWorkOrderIdsByUid(uid);
    }

    @Override
    public Map<String, Object> getProjectLeader(Long workOrderId) {
        return electricianWorkOrderDao.getProjectLeader(workOrderId);
    }

    /**
     * 修改工单状态
     *
     * @param electricianWorkOrder
     * @param userInfo
     * @param updateEntity
     * @param isLeader
     * @return
     */
    @Override
    @Transactional
    public Integer updateEntityByCondition(ElectricianWorkOrder electricianWorkOrder, UserInfo userInfo, ElectricianWorkOrder updateEntity, Boolean isLeader) {
        Integer c = electricianWorkOrderDao.updateEntityByCondition(updateEntity);
        if (c > 0 && isLeader) {
            WorkOrder workOrder = new WorkOrder();
            workOrder.setId(updateEntity.getWorkOrderId());
            workOrder.setModifiedUser(updateEntity.getModifiedUser());
            workOrder.setModifiedTime(updateEntity.getModifiedTime());

            if (ElectricianWorkOrderStatus.CONFIRMED.getValue().equals(updateEntity.getStatus())) {
                /*工单确认*/
                workOrder.setStatus(WorkOrderStatus.CONFIM.getValue());
                workOrderDao.updateWorkOrderInfo(workOrder, WorkOrderStatus.UNCONFIM.getValue());
            } else if (ElectricianWorkOrderStatus.EXECUTING.getValue().equals(updateEntity.getStatus())) {
                /*工单开始*/
                workOrder.setStatus(WorkOrderStatus.EXECUTING.getValue());
                // 实际开始工作时间
                workOrder.setActualStartTime(updateEntity.getBeginTime());
                workOrderDao.updateWorkOrderInfo(workOrder, WorkOrderStatus.CONFIM.getValue());
            } else if (ElectricianWorkOrderStatus.FINISHED.getValue().equals(updateEntity.getStatus())) {
                /*工单结束(待评价)*/
                workOrder.setStatus(WorkOrderStatus.PENDING_EVALUATION.getValue());
                // 实际结束工作时间
                workOrder.setActualEndTime(updateEntity.getFinishTime());
                if (electricianWorkOrder.getMemo().equals(String.valueOf(WorkOrderType.PROSPECTING.getValue()))) {
                    workOrder.setStatus(WorkOrderStatus.EVALUATED.getValue());
                }
                workOrderDao.updateWorkOrderInfo(workOrder, WorkOrderStatus.EXECUTING.getValue());
                // 如果工单结束时，尚有社会工单未支付(未发布)，将其状态改为取消
                int n = socialWorkOrderDao.updateStatusById(null, workOrder.getId(), "系统", 
                		SocialWorkOrderStatus.CANCEL.getValue(), SocialWorkOrderStatus.UN_PUBLISH.getValue());
                
            }
        }
        if (c > 0 && userInfo != null && electricianWorkOrder != null && userInfo.isSocialElectrician() &&
                electricianWorkOrder.getType().equals(ElectricianWorkOrderType.SOCIAL_ELECTRICIAN_WD.getValue())) {
            int n = socialWorkOrderDao.updateStatusById(electricianWorkOrder.getSocialWorkOrderId(), null, "系统", 
            		SocialWorkOrderStatus.FINISHED.getValue(), SocialWorkOrderStatus.RECRUIT_END.getValue());
        }
        return c;
    }

    @Override
    @Transactional
    public Result create(ElectricianWorkOrder electricianWorkOrder)
            throws BusinessException {
        Result result = new DefaultResult();
        electricianWorkOrderDao.save(electricianWorkOrder);
        return result;
    }

    @Override
    public List<ElectricianWorkOrderInfo> queryElectricianList(Long workOrderId, int[] statuss) {
        return electricianWorkOrderDao.queryElectricianList(workOrderId, statuss);
    }

    /**
     * 获取工单相关电工信息
     *
     * @param uid
     * @param workOrderId
     * @return
     */
    @Override
    public List<Map<String, Object>> getAllElectricianInfo(Long uid, Long workOrderId) {
        return electricianWorkOrderDao.getAllElectricianInfo(uid, workOrderId);
    }

    /**
     * 工单结算
     * （修改工单状态，扣除服务商冻结余额，增加社会电工余额）
     *
     * @param entity
     * @param socialElectricianWalletDetail
     * @param socialElectricianWalletDetail
     * @param company
     * @param totalAmount             @return
     * @param userInfo
     */
    @Override
    @Transactional
    public int settlementWorkOrder(ElectricianWorkOrder entity, UserWalletDetail companyWalletDetail, UserWalletDetail socialElectricianWalletDetail, Company company, BigDecimal totalAmount, UserInfo userInfo) {
        // 扣除服务商冻结余额
        int c = userWalletDao.subtractFreezingAmount(company.getMemberId(), totalAmount.doubleValue(), userInfo.getLoginName());
        if (c > 0) {
            // 增加电工余额
            userWalletDao.addAmount(entity.getElectricianId(), totalAmount.doubleValue(), userInfo.getLoginName());
            UserWallet companyWallet=userWalletDao.getByUid(company.getMemberId());
        	if(companyWallet==null){
        		return 0;
        	}
        	companyWalletDetail.setAvailableAmount(companyWallet.getAmount());
        	companyWalletDetail.setAvailableFreezingAmount(companyWallet.getFreezingAmount());
        	UserWallet socialElectricianWallet=userWalletDao.get(entity.getElectricianId());
        	if(socialElectricianWallet==null){
        		return 0;
        	}
        	socialElectricianWalletDetail.setAvailableAmount(socialElectricianWallet.getAmount());
        	socialElectricianWalletDetail.setAvailableFreezingAmount(socialElectricianWallet.getFreezingAmount());
            // 插入金额变动记录
            userWalletDetailDao.insert(companyWalletDetail);
            userWalletDetailDao.insert(socialElectricianWalletDetail);

            // 修改电工工单状态
            ElectricianWorkOrder updateEntity = new ElectricianWorkOrder();
            updateEntity.setId(entity.getId());
            updateEntity.setElectricianId(entity.getElectricianId());
            updateEntity.setStatus(ElectricianWorkOrderStatus.LIQUIDATED.getValue());
            updateEntity.setModifiedUser(userInfo.getLoginName());
            electricianWorkOrderDao.updateEntityByCondition(updateEntity);
        }
        return c;
    }

    /**
     * 根据条件获取所有检修信息
     *
     * @param selectAllMap
     * @return
     */
    @Override
    public List<Map<String, Object>> selectAllCheckOption(Map<String, Object> selectAllMap) {
        return electricianWorkOrderDao.selectAllCheckOption(selectAllMap);
    }

    @Override
    public Double getTotalLiquidatedFee(Long uid) {
        return electricianWorkOrderDao.getTotalLiquidatedFeeByUid(uid);
    }

    @Override
    public ElectricianWorkOrder getByOrderId(String orderId) {
        return electricianWorkOrderDao.getByOrderId(orderId);
    }

    @Override
    public boolean updateSettlementPayStatus(
            PayOrder payOrder) {
        String[] orderIds = StringUtils.split(payOrder.getAssociatedOrderIds(), ",");
        for (String orderId : orderIds) {
            ElectricianWorkOrder electricianWorkOrder = getByOrderId(orderId);
            if (electricianWorkOrder.getSettlementPayStatus() == SettlementPayStatus.UNPAY
                    .getValue()) {
                // 更新结算支付状态
                if (payOrder.getStatus() == PayStatus.SUCCESS.getValue()) {//TODO
                    electricianWorkOrder.setSettlementPayStatus(SettlementPayStatus.PAIED.getValue());
                }
                int num = electricianWorkOrderDao.updateSettlementPayStatus(electricianWorkOrder);
                if (num == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String getSocialWorkOrderOrderIdByOrderId(String orderId) {

        return electricianWorkOrderDao.getSocialWorkOrderOrderIdByOrderId(orderId);
    }


}
