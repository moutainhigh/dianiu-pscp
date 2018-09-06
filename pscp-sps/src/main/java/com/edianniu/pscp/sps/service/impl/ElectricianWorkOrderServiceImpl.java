package com.edianniu.pscp.sps.service.impl;

import com.edianniu.pscp.message.commons.MessageId;
import com.edianniu.pscp.message.service.dubbo.MessageInfoService;
import com.edianniu.pscp.mis.bean.GetUserInfoResult;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.bean.workorder.WorkOrderType;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.sps.bean.workorder.electrician.*;
import com.edianniu.pscp.sps.commons.PageResult;
import com.edianniu.pscp.sps.commons.ResultCode;
import com.edianniu.pscp.sps.dao.*;
import com.edianniu.pscp.sps.domain.*;
import com.edianniu.pscp.sps.service.ElectricianWorkOrderService;
import com.edianniu.pscp.sps.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@Repository("electricianWorkOrderService")
public class ElectricianWorkOrderServiceImpl implements ElectricianWorkOrderService {
    private static final Logger logger = LoggerFactory.getLogger(ElectricianWorkOrderServiceImpl.class);

    @Autowired
    private ElectricianWorkOrderDao electricianWorkOrderDao;
    @Autowired
    private ElectricianWorkOrderViewDao electricianWorkOrderViewDao;
    @Autowired
    private ElectricianEvaluateDao electricianEvaluateDao;
    @Autowired
    private ElectricianEvaluateAttachmentDao electricianEvaluateAttachmentDao;
    @Autowired
    private SocialWorkOrderDao socialWorkOrderDao;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private MessageInfoService messageInfoService;

    @Override
    public ElectricianWorkOrder queryObject(Long id) {
        return electricianWorkOrderDao.queryObject(id);
    }

    @Override
    public List<ElectricianWorkOrderView> queryList(Map<String, Object> map) {
        return electricianWorkOrderViewDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return electricianWorkOrderDao.queryTotal(map);
    }

    @Override
    @Transactional
    public void save(ElectricianWorkOrder electricianWorkOrder) {
        electricianWorkOrderDao.save(electricianWorkOrder);
    }

    @Override
    @Transactional
    public int update(ElectricianWorkOrder electricianWorkOrder) {
        return electricianWorkOrderDao.update(electricianWorkOrder);
    }

    @Override
    @Transactional
    public int delete(Long id) {
        return electricianWorkOrderDao.delete(id);
    }

    @Override
    @Transactional
    public int deleteBatch(Long[] ids) {
        return electricianWorkOrderDao.deleteBatch(ids);
    }

    /**
     * 社会电工工单审核
     *
     * @param auditReqData
     * @return
     */
    @Override
    @Transactional
    public AuditResult audit(AuditReqData auditReqData) {
        AuditResult result = new AuditResult();

        try {
            if (auditReqData.getUid() == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("参数有误");
                return result;
            }

            GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(auditReqData.getUid());
            if (!getUserInfoResult.isSuccess()) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("用户信息有误");
                return result;
            }
            UserInfo userInfo = getUserInfoResult.getMemberInfo();

            if (auditReqData.getId() == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("参数有误");
                return result;
            }

            if (StringUtils.isBlank(auditReqData.getAuditStatus()) ||
                    (!"fail".equals(auditReqData.getAuditStatus()) &&
                            !"pass".equals(auditReqData.getAuditStatus()))) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("参数有误");
                return result;
            }

            if ("fail".equals(auditReqData.getAuditStatus()) &&
                    StringUtils.isBlank(auditReqData.getFailureReason())) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("请填写审核不通过的原因");
                return result;
            }

            ElectricianWorkOrder electricianWorkOrder = electricianWorkOrderDao.queryObject(auditReqData.getId());
            if (electricianWorkOrder == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("电工工单不存在");
                return result;
            }

            if (!electricianWorkOrder.getType().equals(2) ||
                    !userInfo.getCompanyId().equals(electricianWorkOrder.getCompanyId()) ||
                    !electricianWorkOrder.getStatus().equals(ElectricianWorkOrderStatus.UNCONFIRMED.getValue())) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("电工工单不存在");
                return result;
            }

            // 修改信息
            ElectricianWorkOrder updateEntity = new ElectricianWorkOrder();
            updateEntity.setId(electricianWorkOrder.getId());
            updateEntity.setModifiedTime(new Date());
            updateEntity.setModifiedUser(userInfo.getLoginName());
            if ("pass".equals(auditReqData.getAuditStatus())) {
                updateEntity.setStatus(ElectricianWorkOrderStatus.CONFIRMED.getValue());
            } else {
                updateEntity.setMemo(auditReqData.getFailureReason());
                updateEntity.setStatus(ElectricianWorkOrderStatus.FAIL.getValue());
            }

            electricianWorkOrderDao.update(updateEntity);

            // 社会工单信息
            SocialWorkOrder socialWorkOrder = socialWorkOrderDao.getById(electricianWorkOrder.getSocialWorkOrderId());
            // 发送推送消息
            if (socialWorkOrder != null) {
                MessageId smsMsgId = MessageId.SOCIAL_ORDER_AUDIT_SUCCESS;
                Map<String, String> params = new HashMap<>();
                params.put("name", socialWorkOrder.getTitle());
                if ("fail".equals(auditReqData.getAuditStatus())) {
                    smsMsgId = MessageId.SOCIAL_ORDER_AUDIT_FAILURE;
                    String failureReason = auditReqData.getFailureReason();
                    if (StringUtils.isBlank(failureReason)) {
                        failureReason = "订单取消";// 默认
                    }
                    params.put("failure_cause", failureReason);
                    PropertiesUtil config = new PropertiesUtil("config.properties");
                    params.put("contact_number", config.getProperty("contact.number"));
                }

                // 社会电工->sms+push
                GetUserInfoResult dispatchPersonResult = userInfoService.getUserInfo(electricianWorkOrder.getElectricianId());
                if (dispatchPersonResult.isSuccess()) {
                    UserInfo dispatchPerson = dispatchPersonResult.getMemberInfo();
                    messageInfoService.sendSmsAndPushMessage(dispatchPerson.getUid(), dispatchPerson.getMobile(), smsMsgId, params);
                }
            }
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("Electrician WorkOrder list:{}", e);
        }
        return result;
    }

    @Override
    public Map<String, Object> getWorkOrderLeader(Long workOrderId) {
        return electricianWorkOrderDao.getWorkOrderLeader(workOrderId);
    }

    @Override
    public List<Map<String, Object>> queryMapListSocialElectrician(Long workOrderId, Long socialWorkOrderId) {
        return electricianWorkOrderDao.queryMapListSocialElectrician(workOrderId, socialWorkOrderId);
    }

    /**
     * 更新结算数据信息
     *
     * @param electricianWorkOrderList
     * @param evaluateList
     * @param updateEvaluateList
     * @param attachmentList           @return
     */
    @Override
    @Transactional
    public int updateBatch(List<ElectricianWorkOrder> electricianWorkOrderList,
                           List<ElectricianEvaluate> evaluateList,
                           List<ElectricianEvaluate> updateEvaluateList,
                           List<ElectricianEvaluateAttachment> attachmentList) {
        if (CollectionUtils.isNotEmpty(electricianWorkOrderList)) {
            // 更新工单结算信息（不更新工单状态）
            int c = electricianWorkOrderDao.updateBatchLiquidateInfo(electricianWorkOrderList);
            if (c > 0) {
                if (CollectionUtils.isNotEmpty(evaluateList)) {
                    electricianEvaluateDao.saveBatchEvaluate(evaluateList);
                }
                if (CollectionUtils.isNotEmpty(updateEvaluateList)) {
                    electricianEvaluateDao.updateBatchEvaluate(updateEvaluateList);
                }
                if (CollectionUtils.isNotEmpty(attachmentList)) {
                    electricianEvaluateAttachmentDao.saveBatchAttachment(attachmentList);
                }
            }
            return c;
        }
        return 0;
    }

    @Override
    public Map<String, Object> queryMapByCondition(Map<String, Object> queryMap) {
        return electricianWorkOrderDao.queryMapByCondition(queryMap);
    }

    @Override
    public List<ElectricianWorkOrder> selectListByCondition(Map<String, Object> queryMap) {
        return electricianWorkOrderDao.selectListByCondition(queryMap);
    }

    @Override
    public List<Map<String, Object>> selectAllCheckOption(Map<String, Object> selectAllMap) {
        return electricianWorkOrderDao.selectAllCheckOption(selectAllMap);
    }

    @Override
    public PageResult<ElectricianWorkOrderInfo> query(ListQuery listQuery) {
        PageResult<ElectricianWorkOrderInfo> result = new PageResult<ElectricianWorkOrderInfo>();
        int total = electricianWorkOrderViewDao.queryCount(listQuery);
        int nextOffset = 0;
        if (total > 0) {
            String latitude = listQuery.getLatitude();// 经度
            String longitude = listQuery.getLongitude();// 纬度

            List<ElectricianWorkOrderView> list = electricianWorkOrderViewDao.queryList(listQuery);
            List<ElectricianWorkOrderInfo> rlist = new ArrayList<ElectricianWorkOrderInfo>();
            for (ElectricianWorkOrderView ewov : list) {
                ElectricianWorkOrderInfo ewoi = getElectricianWorkOrderInfo(ewov, latitude, longitude);
                if (ewov.getCreateTime() != null) {
                    ewoi.setPublishTime(DateUtils.format(ewov.getCreateTime(), DateUtils.DATE_PATTERN));
                }
                rlist.add(ewoi);
            }
            result.setData(rlist);
            nextOffset = listQuery.getOffset() + rlist.size();
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

    private ElectricianWorkOrderInfo getElectricianWorkOrderInfo(ElectricianWorkOrderView electricianWorkOrderView, String latitude, String longitude) {
        ElectricianWorkOrderInfo info = new ElectricianWorkOrderInfo();
        BeanUtils.copyProperties(electricianWorkOrderView, info, new String[]{"actualFee", "fee", "preFee"});
        info.setActualFee(MoneyUtils.format(electricianWorkOrderView.getActualFee()));
        info.setFee(MoneyUtils.format(electricianWorkOrderView.getFee()));
        String preFee = "";
        if (electricianWorkOrderView.getBeginTime() != null &&
                electricianWorkOrderView.getEndWorkTime() != null) {
            long workTime = electricianWorkOrderView.getEndWorkTime().getTime() - electricianWorkOrderView.getBeginWorkTime().getTime();
            preFee = MoneyUtils.format(electricianWorkOrderView.getFee() * BizUtils.toD(workTime));
        }
        info.setPreFee(preFee);

        // 列表标题内容
        if (StringUtils.isBlank(info.getTitle())) {
            info.setTitle(electricianWorkOrderView.getCheckOption());
        }

        // 根据经纬度计算距离
        double distance = 0D;
        if (StringUtils.isNoneBlank(longitude) && StringUtils.isNoneBlank(latitude)) {
            distance = DistanceUtil.getDistance(Double.valueOf(latitude), Double.valueOf(longitude), 
            		electricianWorkOrderView.getLatitude(), electricianWorkOrderView.getLongitude());
        }
        if (distance > 1D) {
            info.setDistance(String.format("%skm", BigDecimalUtil.dobCoverTwoDecimal(distance)));
        } else {
            BigDecimal bigDecimal = BigDecimalUtil.mul(distance, 1000D);
            info.setDistance(String.format("%sm", BigDecimalUtil.dobCoverTwoDecimal(bigDecimal.doubleValue())));
        }
        // 工单类型名称
        info.setTypeName(WorkOrderType.getDesc(electricianWorkOrderView.getType()));
        return info;
    }

    @Override
    public ElectricianWorkOrder getByOrderId(String orderId) {

        return electricianWorkOrderDao.getByOrderId(orderId);
    }

    @Override
    public int getUnLiquidatedSocialElectricianWorkOrderCount(
            Long socialWorkOrderId) {
        return electricianWorkOrderDao.getUnLiquidatedSocialElectricianWorkOrderCount(socialWorkOrderId);
    }

    @Override
    public int getConfirmedSocialElectricianWorkOrderCount(
            Long socialWorkOrderId) {
        return electricianWorkOrderDao.getConfirmedSocialElectricianWorkOrderCount(socialWorkOrderId);
    }

    @Override
    public List<Long> getUnConfirmSocialElectricianWorkOrderIds(
            Long socialWorkOrderId) {

        return electricianWorkOrderDao.getUnConfirmSocialElectricianWorkOrderIds(socialWorkOrderId);
    }

}
