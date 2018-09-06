package com.edianniu.pscp.sps.service.dubbo.impl;

import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.message.commons.MessageId;
import com.edianniu.pscp.message.service.dubbo.MessageInfoService;
import com.edianniu.pscp.mis.bean.FileUploadResult;
import com.edianniu.pscp.mis.bean.GetUserInfoResult;
import com.edianniu.pscp.mis.bean.electricianworkorder.SettlementPayStatus;
import com.edianniu.pscp.mis.bean.pay.*;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.service.dubbo.FileUploadService;
import com.edianniu.pscp.mis.service.dubbo.PayInfoService;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.sps.bean.DefaultResult;
import com.edianniu.pscp.sps.bean.OrderIdPrefix;
import com.edianniu.pscp.sps.bean.Result;
import com.edianniu.pscp.sps.bean.evaluate.electrician.VO.EvaluateAttachmentVO;
import com.edianniu.pscp.sps.bean.evaluate.electrician.VO.EvaluateVO;
import com.edianniu.pscp.sps.bean.project.ProjectInfo;
import com.edianniu.pscp.sps.bean.project.vo.ProjectVO;
import com.edianniu.pscp.sps.bean.socialworkorder.SocialWorkOrderStatus;
import com.edianniu.pscp.sps.bean.socialworkorder.cancel.CancelReqData;
import com.edianniu.pscp.sps.bean.socialworkorder.cancel.CancelResult;
import com.edianniu.pscp.sps.bean.socialworkorder.confirm.ConfirmReqData;
import com.edianniu.pscp.sps.bean.socialworkorder.confirm.ConfirmResult;
import com.edianniu.pscp.sps.bean.socialworkorder.details.DetailsReqData;
import com.edianniu.pscp.sps.bean.socialworkorder.details.DetailsResult;
import com.edianniu.pscp.sps.bean.socialworkorder.electrician.ElectricianReqData;
import com.edianniu.pscp.sps.bean.socialworkorder.electrician.ElectricianResult;
import com.edianniu.pscp.sps.bean.socialworkorder.liquidate.*;
import com.edianniu.pscp.sps.bean.socialworkorder.list.ListQuery;
import com.edianniu.pscp.sps.bean.socialworkorder.list.ListReqData;
import com.edianniu.pscp.sps.bean.socialworkorder.list.ListResult;
import com.edianniu.pscp.sps.bean.socialworkorder.list.vo.SocialWorkOrderVO;
import com.edianniu.pscp.sps.bean.socialworkorder.payment.PaymentReqData;
import com.edianniu.pscp.sps.bean.socialworkorder.payment.PaymentResult;
import com.edianniu.pscp.sps.bean.socialworkorder.recruit.RecruitInfo;
import com.edianniu.pscp.sps.bean.socialworkorder.recruit.RecruitReqData;
import com.edianniu.pscp.sps.bean.socialworkorder.recruit.RecruitResult;
import com.edianniu.pscp.sps.bean.workorder.chieforder.WorkOrderStatus;
import com.edianniu.pscp.sps.bean.workorder.chieforder.vo.CompanyVO;
import com.edianniu.pscp.sps.bean.workorder.chieforder.vo.ElectricianVO;
import com.edianniu.pscp.sps.bean.workorder.chieforder.vo.WorkOrderVO;
import com.edianniu.pscp.sps.bean.workorder.electrician.ElectricianWorkOrderStatus;
import com.edianniu.pscp.sps.commons.CacheKey;
import com.edianniu.pscp.sps.commons.Constants;
import com.edianniu.pscp.sps.commons.PageResult;
import com.edianniu.pscp.sps.commons.ResultCode;
import com.edianniu.pscp.sps.domain.*;
import com.edianniu.pscp.sps.service.*;
import com.edianniu.pscp.sps.service.dubbo.SocialWorkOrderInfoService;
import com.edianniu.pscp.sps.util.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import stc.skymobi.cache.redis.JedisUtil;

import java.math.BigDecimal;
import java.util.*;

/**
 * ClassName: SocialWorkOrderServiceImpl Author: tandingbo CreateTime:
 * 2017-05-18 09:42
 */
@Service
@Repository("socialWorkOrderInfoService")
public class SocialWorkOrderServiceImpl implements SocialWorkOrderInfoService {
    private static final Logger logger = LoggerFactory
            .getLogger(SocialWorkOrderServiceImpl.class);

    @Autowired
    @Qualifier("socialWorkOrderService")
    private SocialWorkOrderService socialWorkOrderService;
    @Autowired
    @Qualifier("electricianWorkOrderService")
    private ElectricianWorkOrderService electricianWorkOrderService;
    @Autowired
    @Qualifier("workOrderService")
    private WorkOrderService workOrderService;
    @Autowired
    @Qualifier("engineeringProjectService")
    private EngineeringProjectService engineeringProjectService;
    @Autowired
    @Qualifier("spsCompanyService")
    private SpsCompanyService spsCompanyService;
    @Autowired
    @Qualifier("payInfoService")
    private PayInfoService payInfoService;
    @Autowired
    @Qualifier("fileUploadService")
    private FileUploadService fileUploadService;
    @Autowired
    @Qualifier("electricianEvaluateService")
    private ElectricianEvaluateService electricianEvaluateService;
    @Autowired
    @Qualifier("electricianEvaluateAttachmentService")
    private ElectricianEvaluateAttachmentService electricianEvaluateAttachmentService;
    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;
    @Autowired
    @Qualifier("memberWalletService")
    private MemberWalletService memberWalletService;
    @Autowired
    @Qualifier("memberWalletDetailService")
    private MemberWalletDetailService memberWalletDetailService;
    @Autowired
    @Qualifier("messageInfoService")
    private MessageInfoService messageInfoService;
    @Autowired
    private JedisUtil jedisUtil;

    @Override
    public ListResult list(ListReqData listReqData) {
        ListResult result = new ListResult();
        try {
            ListQuery listQuery = new ListQuery();
            BeanUtils.copyProperties(listReqData, listQuery);
            if (listReqData.getListQueryRequestInfo() != null) {
                if (StringUtils.isNoneBlank(listReqData.getListQueryRequestInfo().getName())) {
                    listQuery.setName(listReqData.getListQueryRequestInfo().getName());
                }
                if (StringUtils.isNoneBlank(listReqData.getListQueryRequestInfo()
                        .getProjectName())) {
                    listQuery.setProjectName(listReqData.getListQueryRequestInfo()
                            .getProjectName());
                }
                if (StringUtils.isNoneBlank(listReqData.getListQueryRequestInfo()
                        .getCustomerName())) {
                    listQuery.setCustomerName(listReqData.getListQueryRequestInfo()
                            .getCustomerName());
                }
            }

            Company company = spsCompanyService.getCompanyById(listReqData.getCompanyId());
            if (company == null || !company.getStatus().equals(2)) {
                result.setSocialWorkOrderList(new ArrayList<SocialWorkOrderVO>());
                result.setHasNext(false);
                result.setNextOffset(0);
                result.setTotalCount(0);
                return result;
            }

            PageResult<SocialWorkOrderVO> pageResult = socialWorkOrderService
                    .selectPageSocialWorkOrderVO(listQuery);
            result.setSocialWorkOrderList(pageResult.getData());
            result.setHasNext(pageResult.isHasNext());
            result.setNextOffset(pageResult.getNextOffset());
            result.setTotalCount(pageResult.getTotal());
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("Social WorkOrder list:{}", e);
        }
        return result;
    }

    @Override
    public RecruitResult recruit(RecruitReqData recruitReqData) {
        RecruitResult result = new RecruitResult();
        try {
            Long uid = recruitReqData.getUid();
            GetUserInfoResult getUserInfoResult = userInfoService
                    .getUserInfo(uid);
            if (!getUserInfoResult.isSuccess()) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("登录信息异常");
                return result;
            }
            UserInfo userInfo = getUserInfoResult.getMemberInfo();
            if (userInfo == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("登录信息异常");
                return result;
            }

            if (recruitReqData.getWorkOrderId() == null && StringUtils.isBlank(recruitReqData.getOrderId())) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("工单不存在");
                return result;
            }

            Map<String, Object> queryMap = new HashMap<>();
            queryMap.put("companyId", userInfo.getCompanyId());
            queryMap.put("id", recruitReqData.getWorkOrderId());
            queryMap.put("orderId", recruitReqData.getOrderId());
            WorkOrder workOrder = workOrderService.getWorkOrderByCondition(queryMap);
            if (workOrder == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("工单不存在");
                return result;
            }

            if (workOrder.getStatus().equals(WorkOrderStatus.UN_EVALUATE.getValue()) ||
                    workOrder.getStatus().equals(WorkOrderStatus.EVALUATED.getValue()) ||
                    workOrder.getStatus().equals(WorkOrderStatus.CANCELED.getValue())) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("工单已结束不能发起招募");
                return result;
            }

            if (CollectionUtils.isEmpty(recruitReqData.getRecruitInfoList())) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("招募信息不能为空");
                return result;
            }

            Date now = new Date();

            List<SocialWorkOrder> socialWorkOrderList = new ArrayList<>();
            for (RecruitInfo recruitInfo : recruitReqData.getRecruitInfoList()) {
                SocialWorkOrder socialWorkOrder = new SocialWorkOrder();

                if (StringUtils.isBlank(recruitInfo.getQualifications())) {
                    result.setResultCode(ResultCode.ERROR_402);
                    result.setResultMessage("电工资质不能为空");
                    return result;
                }
                socialWorkOrder.setQualifications(recruitInfo
                        .getQualifications());

                if (recruitInfo.getQuantity() == null) {
                    result.setResultCode(ResultCode.ERROR_402);
                    result.setResultMessage("人数格式不正确");
                    return result;
                }
                socialWorkOrder.setQuantity(recruitInfo.getQuantity());

                if (recruitInfo.getFee() == null) {
                    result.setResultCode(ResultCode.ERROR_402);
                    result.setResultMessage("费用格式不正确");
                    return result;
                }
                socialWorkOrder.setFee(recruitInfo.getFee());

                if (StringUtils.isBlank(recruitInfo.getTitle())) {
                    result.setResultCode(ResultCode.ERROR_402);
                    result.setResultMessage("标题不能为空");
                    return result;
                }
                if (! BizUtils.checkLength(recruitInfo.getTitle(), 50)  ) {
                    result.setResultCode(ResultCode.ERROR_402);
                    result.setResultMessage("标题长度不能超过50个字");
                    return result;
                }
                socialWorkOrder.setTitle(recruitInfo.getTitle());

                if (StringUtils.isBlank(recruitInfo.getContent())) {
                    result.setResultCode(ResultCode.ERROR_402);
                    result.setResultMessage("需求描述不能为空");
                    return result;
                }
                if (! BizUtils.checkLength(recruitInfo.getContent(), 1000)) {
                	result.setResultCode(ResultCode.ERROR_402);
                    result.setResultMessage("需求描述不能超过1000");
                    return result;
    			}
                socialWorkOrder.setContent(recruitInfo.getContent());

                Date beginWorkTime = DateUtils.parse(recruitInfo
                        .getBeginWorkTime());
                if (beginWorkTime == null) {
                    result.setResultCode(ResultCode.ERROR_402);
                    result.setResultMessage("开始工作时间格式不正确");
                    return result;
                }

                Date endWorkTime = DateUtils
                        .parse(recruitInfo.getEndWorkTime());
                if (endWorkTime == null) {
                    result.setResultCode(ResultCode.ERROR_402);
                    result.setResultMessage("结束工作时间格式不正确");
                    return result;
                }

                if (beginWorkTime.after(endWorkTime)) {
                    result.setResultCode(ResultCode.ERROR_402);
                    result.setResultMessage("开始工作时间必须在结束工作时间之前");
                    return result;
                }
                socialWorkOrder.setBeginWorkTime(DateUtils.getStartDate(beginWorkTime));
                socialWorkOrder.setEndWorkTime(DateUtils.getEndDate(endWorkTime));

                Date expiryTime = DateUtils.parse(recruitInfo.getExpiryTime());
                if (expiryTime == null) {
                    result.setResultCode(ResultCode.ERROR_402);
                    result.setResultMessage("派单截止时间格式不正确");
                    return result;
                }
                if (DateUtils.getEndDate(expiryTime).before(now)) {
                    result.setResultCode(ResultCode.ERROR_402);
                    result.setResultMessage("派单截止时间不能早于当前时间");
                    return result;
                }
                if (!expiryTime.before(beginWorkTime)) {
                    result.setResultCode(ResultCode.ERROR_402);
                    result.setResultMessage("派单截止时间不能晚于开始工作时间");
                    return result;
                }

                socialWorkOrder.setExpiryTime(DateUtils.getEndDate(expiryTime));

                socialWorkOrder.setUnit(0);// 0:元/天
                socialWorkOrder.setDeleted(0);
                socialWorkOrder.setWorkOrderId(workOrder.getId());
                socialWorkOrder.setCompanyId(userInfo.getCompanyId());
                socialWorkOrder.setStatus(SocialWorkOrderStatus.UN_PUBLISH.getValue());
                socialWorkOrder.setCreateTime(now);
                socialWorkOrder.setCreateUser(userInfo.getLoginName());
                socialWorkOrder.setOrderId(BizUtils.getOrderId(OrderIdPrefix.SOCIAL_WORK_ORDER_PREFIX));// 招募订单ID

                // 计算支付金额
                BigDecimal fee = BigDecimalUtil.mul(socialWorkOrder.getQuantity(), socialWorkOrder.getFee());
                Double days = Double.valueOf(DateUtils.daysBetween(
                        socialWorkOrder.getBeginWorkTime(),
                        socialWorkOrder.getEndWorkTime())) + 1;
                BigDecimal totalFeeDecimal = BigDecimalUtil.mul(
                        fee.doubleValue(), days.doubleValue());

                socialWorkOrder.setTotalFee(totalFeeDecimal.doubleValue());
                socialWorkOrderList.add(socialWorkOrder);
            }

            // 这验证循环处理是否验证通过，未通过返回异常信息
            if (ResultCode.SUCCESS != result.getResultCode()) {
                return result;
            }

            if (CollectionUtils.isNotEmpty(socialWorkOrderList)) {
                socialWorkOrderService.batchSave(socialWorkOrderList);
                List<Long> socialWorkOrderIds = new ArrayList<>();
                StringBuilder orderIdStr = new StringBuilder("");
                for (SocialWorkOrder socialWorkOrder : socialWorkOrderList) {
                    Long id = socialWorkOrder.getId();
                    if (!socialWorkOrderIds.contains(id)) {
                        orderIdStr.append(socialWorkOrder.getOrderId()).append(",");
                        socialWorkOrderIds.add(socialWorkOrder.getId());
                    }
                }

                // 返回总支付费用
                result.setSocialWorkOrderIds(socialWorkOrderIds);
                String socialOrderIdStr = orderIdStr.substring(0, orderIdStr.lastIndexOf(","));
                result.setSocialOrderIdStr(socialOrderIdStr);

                try {
                    // 更新工单修改人信息
                    WorkOrder updateWorkOrder = new WorkOrder();
                    updateWorkOrder.setId(workOrder.getId());
                    updateWorkOrder.setModifiedTime(now);
                    updateWorkOrder.setModifiedUser(userInfo.getLoginName());
                    workOrderService.updateModifiedUserInfo(updateWorkOrder);
                } catch (Exception e) {
                    logger.error(String.format("工单【%s】更新修改者信息失败", workOrder.getId()), e);
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("Social WorkOrder recruit:{}", e);
        }
        return result;
    }

    @Override
    public CancelResult cancel(CancelReqData cancelReqData) {
        CancelResult result = new CancelResult();
        try {
            Long uid = cancelReqData.getUid();
            GetUserInfoResult getUserInfoResult = userInfoService
                    .getUserInfo(uid);
            if (!getUserInfoResult.isSuccess()) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("登录信息异常");
                return result;
            }
            UserInfo userInfo = getUserInfoResult.getMemberInfo();
            if (userInfo == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("登录信息异常");
                return result;
            }

            if (cancelReqData.getId() == null && StringUtils.isBlank(cancelReqData.getOrderId())) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("社会工单ID不能为空");
                return result;
            }

            SocialWorkOrder socialWorkOrder = socialWorkOrderService.getById(
                    cancelReqData.getId(), cancelReqData.getOrderId(), userInfo.getCompanyId());
            if (socialWorkOrder == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("社会工单不存在");
                return result;
            }

            Date now = new Date();

            Map<String, Object> queryMap = new HashMap<>();
            queryMap.put("type", 2);// 社会电工订单
            queryMap.put("socialWorkOrderId", socialWorkOrder.getId());
            queryMap.put("workOrderId", socialWorkOrder.getWorkOrderId());
            queryMap.put("greaterThanStatus", -1);
            int c = electricianWorkOrderService.queryTotal(queryMap);
            if (c > 0) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("社会电工已经接单，不能直接取消");
                return result;
            }

            // 更新信息
            SocialWorkOrder updateEntity = new SocialWorkOrder();
            updateEntity.setId(socialWorkOrder.getId());
            updateEntity.setModifiedTime(now);
            updateEntity.setModifiedUser(userInfo.getLoginName());
            updateEntity.setStatus(SocialWorkOrderStatus.CANCEL.getValue());

            int u = socialWorkOrderService.update(updateEntity);
            if (u < 1) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("取消失败");
                return result;
            }

            try {
                // 更新工单修改人信息
                WorkOrder workOrder = new WorkOrder();
                workOrder.setId(socialWorkOrder.getWorkOrderId());
                workOrder.setModifiedTime(now);
                workOrder.setModifiedUser(userInfo.getLoginName());
                workOrderService.updateModifiedUserInfo(workOrder);
            } catch (Exception e) {
                logger.error(String.format("工单【%s】更新修改者信息失败", socialWorkOrder.getWorkOrderId()), e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("Social WorkOrder cancel:{}", e);
        }
        return result;
    }

    @Override
    public CancelResult cancel(Long id) {
        CancelResult result = new CancelResult();
        try {
            if (id == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("社会工单ID不能为空");
                return result;
            }

            SocialWorkOrder socialWorkOrder = socialWorkOrderService
                    .getById(id);
            if (socialWorkOrder == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("社会工单不存在");
                return result;
            }

            Date now = new Date();

            Map<String, Object> queryMap = new HashMap<>();
            queryMap.put("type", 2);// 社会电工订单
            queryMap.put("socialWorkOrderId", socialWorkOrder.getId());
            queryMap.put("workOrderId", socialWorkOrder.getWorkOrderId());
            queryMap.put("greaterThanStatus", -1);
            int c = electricianWorkOrderService.queryTotal(queryMap);
            if (c > 0) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("社会电工已经接单，不能直接取消");
                return result;
            }

            // 更新信息
            SocialWorkOrder updateEntity = new SocialWorkOrder();
            updateEntity.setId(id);
            updateEntity.setModifiedTime(now);
            updateEntity.setModifiedUser("task");
            updateEntity.setStatus(SocialWorkOrderStatus.CANCEL.getValue());

            int u = socialWorkOrderService.update(updateEntity);
            if (u < 1) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("取消失败");
                return result;
            }
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("Social WorkOrder cancel:{}", e);
        }
        return result;
    }

    @Override
    public DetailsResult details(DetailsReqData detailsReqData) {
        DetailsResult result = new DetailsResult();
        try {
            if (detailsReqData.getId() == null && StringUtils.isBlank(detailsReqData.getOrderId())) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("请求参数不合法");
                return result;
            }

            result = socialWorkOrderService.details(detailsReqData);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("Social WorkOrder details:{}", e);
        }
        return result;
    }

    @Override
    public ConfirmResult confirm(ConfirmReqData confirmReqData) {
        ConfirmResult result = new ConfirmResult();
        try {
            Long uid = confirmReqData.getUid();
            GetUserInfoResult getUserInfoResult = userInfoService
                    .getUserInfo(uid);
            if (!getUserInfoResult.isSuccess()) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("登录信息异常");
                return result;
            }
            UserInfo userInfo = getUserInfoResult.getMemberInfo();
            if (userInfo == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("登录信息异常");
                return result;
            }

            if (CollectionUtils.isEmpty(confirmReqData
                    .getSocialElectricianList())) {
                return result;
            }

            if (confirmReqData.getSocialWorkOrderId() == null &&
                    StringUtils.isBlank(confirmReqData.getOrderId())) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("社会工单标识不能为空");
                return result;
            }

            final SocialWorkOrder socialWorkOrder = socialWorkOrderService.getById(confirmReqData.getSocialWorkOrderId(), confirmReqData.getOrderId(), null);
            if (socialWorkOrder == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("未匹配到社会工单信息");
                return result;
            }

            Map<String, Object> queryMap = new HashMap<>();
            queryMap.put("type", 2);
            queryMap.put("socialWorkOrderId", socialWorkOrder.getId());
            queryMap.put("workOrderId", socialWorkOrder.getWorkOrderId());
            queryMap.put("greaterThanStatus", 0);
            // 获取已确认的社会电工数量
            Integer c = electricianWorkOrderService.queryTotal(queryMap);

            Date now = new Date();
            Integer passCount = 0;
            List<ElectricianWorkOrder> electricianWorkOrderList = new ArrayList<>();
            for (Map<String, Object> map : confirmReqData
                    .getSocialElectricianList()) {
                if (map.get("auditing") != null && StringUtils.isNoneBlank(map.get("auditing").toString())) {
                    ElectricianWorkOrder electricianWorkOrder = new ElectricianWorkOrder();
                    electricianWorkOrder.setSocialWorkOrderId(socialWorkOrder.getId());
                    electricianWorkOrder.setId(Long.valueOf(map.get("electricianWorkOrderId").toString()));
                    electricianWorkOrder.setElectricianId(Long.valueOf(map.get("electricianId").toString()));
                    if ("pass".equals(map.get("auditing").toString())) {
                        electricianWorkOrder.setStatus(ElectricianWorkOrderStatus.CONFIRMED.getValue());
                        passCount++;
                    } else {
                        electricianWorkOrder.setStatus(ElectricianWorkOrderStatus.FAIL.getValue());
                    }

                    electricianWorkOrderList.add(electricianWorkOrder);
                }
            }

            if ((c + passCount) > socialWorkOrder.getQuantity()) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("确认电工人数已超过招募人数");
                return result;
            }

            // 确认人数等于招募人数，社会工单状态修改为已招募
            SocialWorkOrder updateSocialWorkOrder = null;
            if (socialWorkOrder.getQuantity().equals(c + passCount)) {
                updateSocialWorkOrder = new SocialWorkOrder();
                updateSocialWorkOrder.setId(socialWorkOrder.getId());
                updateSocialWorkOrder
                        .setStatus(SocialWorkOrderStatus.RECRUIT_END.getValue());
            }

            socialWorkOrderService.confirm(updateSocialWorkOrder,
                    electricianWorkOrderList,
                    ElectricianWorkOrderStatus.UNCONFIRMED.getValue(),
                    ElectricianWorkOrderStatus.FAIL.getValue());

            PropertiesUtil config = new PropertiesUtil("app.properties");
            final String contact_number = config.getProperty("contact.number");
            // 异步发送推送消息
            final List<ElectricianWorkOrder> electricianWorkOrderFinalList = electricianWorkOrderList;
            ThreadUtil.getSortTimeOutThread(new Runnable() {
                @Override
                public void run() {
                    // 发送推送消息
                    for (ElectricianWorkOrder electricianWorkOrder : electricianWorkOrderFinalList) {
                        MessageId smsMsgId = MessageId.SOCIAL_ORDER_AUDIT_SUCCESS;
                        Map<String, String> params = new HashMap<>();
                        params.put("name", socialWorkOrder.getTitle());
                        params.put("contact_number", contact_number);

                        if (electricianWorkOrder.getStatus().equals(ElectricianWorkOrderStatus.FAIL.getValue())) {
                            smsMsgId = MessageId.SOCIAL_ORDER_AUDIT_FAILURE;
                            String failureReason = "";
                            if (StringUtils.isBlank(failureReason)) {
                                failureReason = "订单取消";// 默认
                            }
                            params.put("failure_cause", failureReason);
                        }

                        // 社会电工->sms+push
                        GetUserInfoResult dispatchPersonResult = userInfoService.getUserInfo(electricianWorkOrder.getElectricianId());
                        if (dispatchPersonResult.isSuccess()) {
                            UserInfo dispatchPerson = dispatchPersonResult.getMemberInfo();
                            messageInfoService.sendSmsAndPushMessage(dispatchPerson.getUid(), dispatchPerson.getMobile(), smsMsgId, params);
                        }
                    }
                }
            });
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("Social WorkOrder confirm:{}", e);
        }
        return result;
    }

    @Override
    public LiquidateResult liquidate(LiquidateReqData liquidateReqData) {
        String cacheKey = "";
        LiquidateResult result = new LiquidateResult();
        try {
            Long uid = liquidateReqData.getUid();
            GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(uid);
            if (!getUserInfoResult.isSuccess()) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("登录信息异常");
                return result;
            }
            UserInfo userInfo = getUserInfoResult.getMemberInfo();
            if (userInfo == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("登录信息异常");
                return result;
            }

            SocialWorkOrder socialWorkOrder = socialWorkOrderService.getById(
                    liquidateReqData.getSocialWorkOrderId(), null, null);
            if (socialWorkOrder == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("支付信息不完整");
                return result;
            }

            // 并发控制
            cacheKey = String.format("%s_%s", socialWorkOrder.getOrderId(), Constants.SETTLEMENT_CACHE_KEY_PREFIX);
            String cacheValue = jedisUtil.get(cacheKey);
            if (StringUtils.isNotBlank(cacheValue)) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("工单正在结算中，请耐心等待");
                return result;
            }

            // 检查工单是否正在支付中
            String cachePayOrderValue = jedisUtil.get(socialWorkOrder.getOrderId());
            if (StringUtils.isNotBlank(cachePayOrderValue)) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("工单正在支付中，请耐心等待");
                return result;
            }

            // 设置正在结算缓存标识信息
            Long rs = jedisUtil.setnx(cacheKey, String.format("%s-%s", uid, socialWorkOrder.getOrderId()), 1);
            if (rs == 0) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("工单正在结算中，请耐心等待");
                return result;
            }

            result = liquidateHelper(liquidateReqData, socialWorkOrder, userInfo);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("Social WorkOrder liquidateBackground:{}", e);
        } finally {
            jedisUtil.del(cacheKey);
        }
        return result;
    }

    private Map<String, Object> obtainFreezingAmount(SocialWorkOrder socialWorkOrder, List<ElectricianWorkOrder> electricianWorkOrderList) {
        Map<String, Object> result = new HashMap<>();
        if (CollectionUtils.isEmpty(electricianWorkOrderList)) {
            return result;
        }

        Double paymentAmount = 0D;


        List<Long> electricianIds = new ArrayList<>();
        Map<Long, ElectricianWorkOrder> electricianWorkOrderMap = new HashMap<>();
        for (ElectricianWorkOrder electricianWorkOrder : electricianWorkOrderList) {
            electricianWorkOrderMap.put(electricianWorkOrder.getElectricianId(), electricianWorkOrder);

            if (!electricianIds.contains(electricianWorkOrder.getElectricianId())) {
                electricianIds.add(electricianWorkOrder.getElectricianId());
            }

            paymentAmount += BigDecimalUtil.mul(electricianWorkOrder.getActualWorkTime(), socialWorkOrder.getFee()).doubleValue();
        }

        Double freezingBalance = calculationFreezingBalance(socialWorkOrder, electricianIds);
        result.put("freezingAmount", MoneyUtils.format(freezingBalance));

        // 需要额外支付的工单
        StringBuilder supplementaryPaymentsOrderIdStr = new StringBuilder("");

        // 按工作天数升序排序
        Collections.sort(electricianWorkOrderList, new Comparator<ElectricianWorkOrder>() {
            @Override
            public int compare(ElectricianWorkOrder o1, ElectricianWorkOrder o2) {
                return o1.getActualWorkTime().compareTo(o2.getActualWorkTime());
            }
        });

        for (ElectricianWorkOrder electricianWorkOrder : electricianWorkOrderList) {
            electricianWorkOrder.setStatus(ElectricianWorkOrderStatus.FEE_CONFIRM.getValue());

            BigDecimal payment = BigDecimalUtil.mul(socialWorkOrder.getFee(), electricianWorkOrder.getActualWorkTime());
            freezingBalance = BigDecimalUtil.sub(freezingBalance, payment.doubleValue()).doubleValue();
            if (freezingBalance >= 0) {
                electricianWorkOrder.setSettlementPayStatus(Constants.TAG_YES);
            } else {
                electricianWorkOrder.setSettlementPayStatus(Constants.TAG_NO);
                if (StringUtils.isNoneBlank(supplementaryPaymentsOrderIdStr)) {
                    supplementaryPaymentsOrderIdStr.append(",");
                }
                supplementaryPaymentsOrderIdStr.append(electricianWorkOrder.getOrderId());
            }
        }

        result.put("status", "success");
        if (freezingBalance < 0) {
            result.put("status", "failure");
        }

        result.put("data", electricianWorkOrderList);
        result.put("supplementaryPayments", supplementaryPaymentsOrderIdStr);
        result.put("paymentAmount", MoneyUtils.format(paymentAmount));
        return result;
    }

    /**
     * 计算冻结金额
     *
     * @param socialWorkOrder
     * @param electricianIds
     * @return
     */
    private Double calculationFreezingBalance(SocialWorkOrder socialWorkOrder, List<Long> electricianIds) {
        Double freezingAmount = 0D, settlementFee = 0D;

        // 招募预估费用（每个社会电工）
        Double workTime = Double.valueOf(DateUtils.daysBetween(socialWorkOrder.getBeginWorkTime(), socialWorkOrder.getEndWorkTime()));
        BigDecimal feeApiece = BigDecimalUtil.mul(socialWorkOrder.getFee(), workTime.doubleValue());

        // 获取社会工单冻结余额信息
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("type", 2);
        queryMap.put("orderId", socialWorkOrder.getOrderId());
        List<MemberWalletDetail> memberWalletDetails = memberWalletDetailService.queryList(queryMap);
        if (CollectionUtils.isNotEmpty(memberWalletDetails)) {
            for (MemberWalletDetail memberWalletDetail : memberWalletDetails) {
                freezingAmount += memberWalletDetail.getAmount();
            }
        }

        List<Map<String, Object>> mapList = electricianWorkOrderService.queryMapListSocialElectrician(socialWorkOrder.getWorkOrderId(), socialWorkOrder.getId());
        if (CollectionUtils.isNotEmpty(mapList)) {
            for (Map<String, Object> map : mapList) {
                Integer settlementPayStatus = 0;
                Long electricianId = Long.valueOf(map.get("electricianId").toString());

                // 取消、不符合，不计入费用结算
                if (ElectricianWorkOrderStatus.CANCELED.getValue().equals(Integer.valueOf(map.get("status").toString())) ||
                        ElectricianWorkOrderStatus.FAIL.getValue().equals(Integer.valueOf(map.get("status").toString()))) {
                    continue;
                }

                // 当前结算电工，不计入费用结算
                if (electricianIds.contains(electricianId)) {
                    continue;
                }

                // 结算支付状态(0:未支付,1:已支付)
                if (map.get("settlementPayStatus") != null) {
                    settlementPayStatus = Integer.valueOf(map.get("settlementPayStatus").toString());
                }

                if (map.get("actualWorkTime") == null ||
                        settlementPayStatus.equals(SettlementPayStatus.UNPAY.getValue())) {
                    settlementFee += feeApiece.doubleValue();
                }
                if (settlementPayStatus.equals(SettlementPayStatus.PAIED.getValue())) {
                    BigDecimal bigDecimal = BigDecimalUtil.mul(socialWorkOrder.getFee(), Double.valueOf(map.get("actualWorkTime").toString()));
                    settlementFee += bigDecimal.doubleValue();
                }
            }
        }

        // 冻结总额-社会电工工单费用=冻结余额
        return BigDecimalUtil.sub(freezingAmount, settlementFee).doubleValue();
    }

    private LiquidateResult liquidateHelper(LiquidateReqData liquidateReqData,
                                            SocialWorkOrder socialWorkOrder,
                                            UserInfo userInfo) {
        LiquidateResult result = new LiquidateResult();

        // 数据构建
        List<LiquidateInfo> liquidateInfoList = liquidateReqData.getLiquidateInfoList();
        if (CollectionUtils.isEmpty(liquidateInfoList)) {
            result.setResultCode(ResultCode.ERROR_401);
            result.setResultMessage("支付信息不完整");
            return result;
        }

        Company company = spsCompanyService.getCompanyById(userInfo.getCompanyId());
        if (company == null) {
            result.setResultCode(ResultCode.ERROR_401);
            result.setResultMessage("未匹配到公司信息");
            return result;
        }

        ElectricianEvaluate evaluate = null;
        Double fee = socialWorkOrder.getFee();// 费用
        BigDecimal actualFee = new BigDecimal(0);
        List<ElectricianEvaluate> saveEvaluateList = new ArrayList<>();
        List<ElectricianEvaluate> updateEvaluateList = new ArrayList<>();
        List<ElectricianEvaluateAttachment> attachmentList = new ArrayList<>();
        List<ElectricianWorkOrder> electricianWorkOrderList = new ArrayList<>();
        for (LiquidateInfo liquidateInfo : liquidateInfoList) {
            // 实际工作时间
            Double actualWorkTime = liquidateInfo.getActualWorkTime();
            if (actualWorkTime == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("实际工作时间不能为空");
                return result;
            }

            if (liquidateInfo.getElectricianId() == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("电工信息不能为空");
                return result;
            }

            ElectricianEvaluate electricianEvaluate = electricianEvaluateService
                    .queryEntityByElectricianWorkOrderId(
                            liquidateInfo.getElectricianId(),
                            liquidateInfo.getElectricianWorkOrderId());
            if (liquidateInfo.getEvaluateInfo() == null
                    && electricianEvaluate == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("评价信息不能为空");
                return result;
            }

            ElectricianWorkOrder electricianWorkOrder = electricianWorkOrderService
                    .queryObject(liquidateInfo.getElectricianWorkOrderId());
            if (electricianWorkOrder == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("电工工单不存在");
                return result;
            }

            if (!electricianWorkOrder.getStatus().equals(
                    ElectricianWorkOrderStatus.FINISHED.getValue())
                    && !electricianWorkOrder.getStatus().equals(
                    ElectricianWorkOrderStatus.FEE_CONFIRM.getValue())) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("电工工单不能结算");
                return result;
            }

            // 计算费用
            BigDecimal bigDecimal = BigDecimalUtil.mul(fee, actualWorkTime);
            actualFee = BigDecimalUtil.add(actualFee.doubleValue(), bigDecimal.doubleValue());

            // 更新电工工单确认支付信息
            ElectricianWorkOrder updateEntity = new ElectricianWorkOrder();
            updateEntity.setId(electricianWorkOrder.getId());
            updateEntity.setSocialWorkOrderId(socialWorkOrder.getId());
            updateEntity.setElectricianId(liquidateInfo.getElectricianId());
            updateEntity.setActualFee(bigDecimal.doubleValue());
            updateEntity.setActualWorkTime(actualWorkTime);
            electricianWorkOrderList.add(updateEntity);

            // 1.未评价（新增）
            if (liquidateInfo.getEvaluateInfo() != null && electricianEvaluate == null) {
                EvaluateVO evaluateVO = liquidateInfo.getEvaluateInfo();
                // 获取数据库自增主键ID
                Long id = electricianEvaluateService.getId();

                // 构建评价信息
                evaluate = structureEvaluateInfo(id, socialWorkOrder.getCompanyId(),
                        liquidateInfo.getElectricianId(), liquidateInfo.getElectricianWorkOrderId(),
                        evaluateVO);

                // 评价图片信息
                LiquidateResult attachmentResult = structureAttachmentList(id, evaluateVO, attachmentList);
                if (attachmentResult.getResultCode() != ResultCode.SUCCESS) {
                    return attachmentResult;
                }
                // 新增评价信息
                saveEvaluateList.add(evaluate);
            }
            // 2.已评价，电工工单状态在已完成、费用待确认（修改）
            else if (liquidateInfo.getEvaluateInfo() != null && electricianEvaluate != null) {
                Long id = electricianEvaluate.getId();
                EvaluateVO evaluateVO = liquidateInfo.getEvaluateInfo();

                // 构建评价信息
                evaluate = structureEvaluateInfo(id, socialWorkOrder.getCompanyId(),
                        liquidateInfo.getElectricianId(), liquidateInfo.getElectricianWorkOrderId(),
                        evaluateVO);

                // 构建评价图片信息
                LiquidateResult attachmentResult = structureAttachmentList(id, evaluateVO, attachmentList);
                if (attachmentResult.getResultCode() != ResultCode.SUCCESS) {
                    return attachmentResult;
                }
                // 更新评价信息
                updateEvaluateList.add(evaluate);

                // 删除附件图片信息
                if (StringUtils.isNotBlank(evaluateVO.getAttachmentIds())) {
                    String[] attachmentIdArray = evaluateVO.getAttachmentIds().trim().split(",");
                    Set<Long> attachmentIdSet = new HashSet<>();
                    for (String str : attachmentIdArray) {
                        attachmentIdSet.add(Long.valueOf(str));
                    }

                    if (attachmentIdArray.length > 0) {
                        try {
                            electricianEvaluateAttachmentService.deleteBatch(attachmentIdSet.toArray(new Long[]{}));
                        } catch (Exception e) {
                            logger.error("结算评价图片删除异常", e);
                        }
                    }
                }
            }
        }

        // 这里处理循环数据校验
        if (result.getResultCode() != ResultCode.SUCCESS) {
            return result;
        }

        Map<String, Object> freezingMap = obtainFreezingAmount(socialWorkOrder, electricianWorkOrderList);
        // 结算数据
        List<ElectricianWorkOrder> electricianWorkOrders = (List<ElectricianWorkOrder>) freezingMap.get("data");

        // 更新工单信息
        int c = electricianWorkOrderService.updateBatch(electricianWorkOrders, saveEvaluateList, updateEvaluateList, attachmentList);
        if (c <= 0) {
            result.setResultCode(ResultCode.ERROR_401);
            result.setResultMessage("系统异常，更新数据失败");
            return result;
        }

        try {
            // 更新工单修改人信息
            WorkOrder updateWorkOrder = new WorkOrder();
            updateWorkOrder.setId(socialWorkOrder.getWorkOrderId());
            updateWorkOrder.setModifiedTime(new Date());
            updateWorkOrder.setModifiedUser(userInfo.getLoginName());
            workOrderService.updateModifiedUserInfo(updateWorkOrder);
        } catch (Exception e) {
            logger.error(String.format("工单【%s】更新修改者信息失败", socialWorkOrder.getWorkOrderId()), e);
            e.printStackTrace();
        }

        if ("failure".equals(freezingMap.get("status").toString())) {
            result.setStatus(freezingMap.get("status").toString());
            result.setResultMessage("冻结余额不足，请选择其他方式支付");

            result.setOrderId(freezingMap.get("supplementaryPayments").toString());
            return result;
        }

        result.setStatus(freezingMap.get("status").toString());
        result.setFreezingAmount(freezingMap.get("freezingAmount").toString());
        result.setPaymentAmount(freezingMap.get("paymentAmount").toString());
        return result;
    }

    private LiquidateResult structureAttachmentList(Long evaluateId, EvaluateVO evaluateVO, List<ElectricianEvaluateAttachment> attachmentList) {
        LiquidateResult result = new LiquidateResult();

        // 评价图片已上传处理
        if (evaluateVO.getEvaluateImageArray() != null && evaluateVO.getEvaluateImageArray().length > 0) {
            for (String fid : evaluateVO.getEvaluateImageArray()) {
                ElectricianEvaluateAttachment attachment = new ElectricianEvaluateAttachment();
                attachment.setType(1L);
                attachment.setFid(fid);
                attachment.setElectricianEvaluateId(evaluateId);
                attachmentList.add(attachment);
            }
        }

        // 评价图片未上传处理
        if (CollectionUtils.isNotEmpty(evaluateVO.getAttachment())) {
            for (EvaluateAttachmentVO attachmentVO : evaluateVO.getAttachment()) {
                if (attachmentVO.getId() != null || StringUtils.isBlank(attachmentVO.getFid())) {
                    continue;
                }

                byte[] content = ImageUtils.getBase64ImageBytes(attachmentVO.getFid());
                // 图片压缩
                if (content != null) {
                    if (content.length > 8 * 1024 * 1024) {// 压缩后的图片或者原图图片大于8M，则提示片太大了，请重新选择图片。
                        result.setResultMessage("图片大小太大了，请重新选择图片");
                        result.setResultCode(ResultCode.ERROR_401);
                        return result;
                    }
                }

                FileUploadResult fileUploadResult = fileUploadService
                        .upload("upload_" + System.nanoTime() + ".jpg", content, true, null);
                if (!fileUploadResult.isSuccess()) {
                    result.setResultMessage(fileUploadResult.getResultMessage());
                    result.setResultCode(fileUploadResult.getResultCode());
                    return result;
                }

                ElectricianEvaluateAttachment attachment = new ElectricianEvaluateAttachment();
                attachment.setType(1L);
                attachment.setElectricianEvaluateId(evaluateId);
                attachment.setFid(fileUploadResult.getFileId());
                attachmentList.add(attachment);
            }
        }
        return result;
    }

    /**
     * 构建评价信息
     *
     * @param evaluateId
     * @param companyId
     * @param electricianId
     * @param electricianWorkOrderId
     * @param evaluateVO
     * @return
     */
    private ElectricianEvaluate structureEvaluateInfo(Long evaluateId, Long companyId, Long electricianId,
                                                      Long electricianWorkOrderId, EvaluateVO evaluateVO) {
        ElectricianEvaluate evaluate = new ElectricianEvaluate();
        evaluate.setId(evaluateId);
        evaluate.setContent(evaluateVO.getContent());
        evaluate.setServiceSpeed(evaluateVO.getServiceSpeed());
        evaluate.setServiceQuality(evaluateVO.getServiceQuality());
        if (companyId != null) {
            evaluate.setCompanyId(companyId);
        }
        if (electricianId != null) {
            evaluate.setElectricianId(electricianId);
        }
        if (electricianWorkOrderId != null) {
            evaluate.setElectricianWorkOrderId(electricianWorkOrderId);
        }
        return evaluate;
    }

    @Override
    public ElectricianResult electricianWorkOrder(ElectricianReqData electricianReqData) {
        ElectricianResult result = new ElectricianResult();
        try {
            if (StringUtils.isBlank(electricianReqData.getOrderId())) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("请求参数不合法");
                return result;
            }

            result = socialWorkOrderService.electricianWorkOrder(electricianReqData);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("Social WorkOrder electricianWorkOrder:{}", e);
        }
        return result;
    }

    @Override
    public LiquidateDetailsResult liquidateDetailsForElectrician(LiquidateDetailsReqData liquidateDetailsReqData) {
        LiquidateDetailsResult result = new LiquidateDetailsResult();
        try {
            Long uid = liquidateDetailsReqData.getUid();
            GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(uid);
            if (!getUserInfoResult.isSuccess()) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("登录信息异常");
                return result;
            }
            UserInfo userInfo = getUserInfoResult.getMemberInfo();
            if (userInfo == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("登录信息异常");
                return result;
            }

            if (StringUtils.isBlank(liquidateDetailsReqData.getOrderId())) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("请求参数不合法");
                return result;
            }
            if (liquidateDetailsReqData.getElectricianId() == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("请求参数不合法");
                return result;
            }

            SocialWorkOrder socialWorkOrder = socialWorkOrderService.getById(null, liquidateDetailsReqData.getOrderId(), userInfo.getCompanyId());
            if (socialWorkOrder == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("未匹配到结算信息");
                return result;
            }

            Map<String, Object> queryMap = new HashMap<>();
            queryMap.put("socialWorkOrderId", socialWorkOrder.getId());
            queryMap.put("workOrderId", socialWorkOrder.getWorkOrderId());
            queryMap.put("electricianId", liquidateDetailsReqData.getElectricianId());
            Map<String, Object> electricianWorkOrderMap = electricianWorkOrderService.queryMapByCondition(queryMap);
            if (MapUtils.isEmpty(electricianWorkOrderMap)) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("未匹配到结算信息");
                return result;
            }

            if (Integer.valueOf(electricianWorkOrderMap.get("status").toString()) < ElectricianWorkOrderStatus.FINISHED.getValue()) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("工单不能被结算，用户取消或未完成工作！");
                return result;
            }

            Long socialWorkOrderId = socialWorkOrder.getId();
            Date beginWorkTime = socialWorkOrder.getBeginWorkTime();
            Date endWorkTime = socialWorkOrder.getEndWorkTime();

            // 电工资质名称转换
            String certificateName = socialWorkOrderService.getCertificateName(socialWorkOrder.getQualifications());
            result.setCertificate(certificateName);
            // 电工资质
            List<Map<String, Object>> listCertificateIdAndName = socialWorkOrderService.structureCertificateIdAndName(socialWorkOrder.getQualifications());
            result.setQualifications(JSON.toJSONString(listCertificateIdAndName));
            result.setElectricianId(liquidateDetailsReqData.getElectricianId());
            result.setElectricianName(electricianWorkOrderMap.get("electricianName").toString());
            Long electricianWorkOrderId = Long.valueOf(electricianWorkOrderMap.get("electricianWorkOrderId").toString());
            result.setElectricianWorkOrderId(electricianWorkOrderId);
            result.setElectricianWorkOrderNo(electricianWorkOrderMap.get("orderId").toString());
            result.setSocialWorkOrderId(socialWorkOrderId);
            // 招募时间
            result.setBeginWorkTime(DateUtils.format(beginWorkTime, DateUtils.DATE_PATTERN));
            result.setEndWorkTime(DateUtils.format(endWorkTime, DateUtils.DATE_PATTERN));
            // 费用
            result.setFee(MoneyUtils.format(socialWorkOrder.getFee()));
            // 实际工作时间(天)
            Double actualWorkTime = 0D;
            if (electricianWorkOrderMap.get("actualWorkTime") != null &&
                    Double.valueOf(electricianWorkOrderMap.get("actualWorkTime").toString()) > 0D) {
                actualWorkTime = Double.valueOf(electricianWorkOrderMap.get("actualWorkTime").toString());
            } else {
                actualWorkTime = Double.valueOf(DateUtils.daysBetween(beginWorkTime, endWorkTime)) + 1;
            }
            result.setActualWorkTime(actualWorkTime);
            // 费用小计
            BigDecimal totalFee = BigDecimalUtil.mul(socialWorkOrder.getFee(), actualWorkTime);
            result.setTotalFee(MoneyUtils.format(totalFee.doubleValue()));
            result.setSocialWorkOrderId(socialWorkOrderId);
            result.setProcessed(0);

            // 评价信息
            EvaluateVO evaluateVO = new EvaluateVO();
            ElectricianEvaluate evaluate = electricianEvaluateService.queryEntityByElectricianWorkOrderId(liquidateDetailsReqData.getElectricianId(), electricianWorkOrderId);
            if (evaluate != null) {
                result.setProcessed(1);

                evaluateVO.setEvaluateId(evaluate.getId());
                evaluateVO.setContent(evaluate.getContent());
                evaluateVO.setServiceSpeed(evaluate.getServiceSpeed());
                evaluateVO.setServiceQuality(evaluate.getServiceQuality());
                evaluateVO.setCreateTime(DateUtils.format(evaluate.getCreateTime(), DateUtils.DATE_PATTERN));

                Set<String> evaluateImageSet = new HashSet<>();
                List<EvaluateAttachmentVO> attachmentVOList = new ArrayList<>();
                List<ElectricianEvaluateAttachment> attachmentList = electricianEvaluateAttachmentService.queryListByEvaluateId(evaluate.getId());
                if (CollectionUtils.isNotEmpty(attachmentList)) {
                    for (ElectricianEvaluateAttachment attachment : attachmentList) {
                        EvaluateAttachmentVO attachmentVO = new EvaluateAttachmentVO();
                        attachmentVO.setId(attachment.getId());
                        attachmentVO.setFid(attachment.getFid());
                        attachmentVOList.add(attachmentVO);

                        evaluateImageSet.add(picUrl + attachment.getFid());
                    }
                }
                evaluateVO.setAttachment(attachmentVOList);
                evaluateVO.setEvaluateImageArray(evaluateImageSet.toArray(new String[]{}));
            }
            result.setEvaluate(evaluateVO);

            // 结算电工ID
            List<Long> electricianIds = new ArrayList<>();
            electricianIds.add(liquidateDetailsReqData.getElectricianId());

            // 冻结余额
            Double freezingAmount = calculationFreezingBalance(socialWorkOrder, electricianIds);
            result.setFreezingAmount(MoneyUtils.format(freezingAmount));
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("Social WorkOrder details:{}", e);
        }
        return result;
    }

    @Override
    public LiquidateDetailsBackgroundResult liquidateDetailsBackground(LiquidateDetailsBackgroundReqData reqData) {
        LiquidateDetailsBackgroundResult result = new LiquidateDetailsBackgroundResult();
        try {
            Long uid = reqData.getUid();
            GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(uid);
            if (!getUserInfoResult.isSuccess()) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("登录信息异常");
                return result;
            }
            UserInfo userInfo = getUserInfoResult.getMemberInfo();
            if (userInfo == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("登录信息异常");
                return result;
            }

            if (StringUtils.isBlank(reqData.getOrderId())) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("社会工单标识不能为空");
                return result;
            }

            SocialWorkOrder socialWorkOrder = socialWorkOrderService.getById(
                    null, reqData.getOrderId(), userInfo.getCompanyId());
            if (socialWorkOrder == null) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("未匹配到社会工单");
                return result;
            }

            if (!socialWorkOrder.getStatus().equals(
                    SocialWorkOrderStatus.FINISHED.getValue())) {
                result.setResultCode(ResultCode.ERROR_403);
                result.setResultMessage("社会工单只在已完成才能结算");
                return result;
            }

            // 工单信息
            WorkOrder workOrder = workOrderService.queryObject(
                    socialWorkOrder.getWorkOrderId(), null,
                    userInfo.getCompanyId());
            if (workOrder == null) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("未匹配到工单信息");
                return result;
            }
            WorkOrderVO workOrderVO = new WorkOrderVO();
            workOrderVO.setOrderId(workOrder.getOrderId());
            workOrderVO.setName(workOrder.getName());
            workOrderVO.setAddress(workOrder.getAddress());
            workOrderVO.setContent(workOrder.getContent());
            String startTime = DateUtils.format(workOrder.getStartTime(), DateUtils.DATE_PATTERN);
            String endTime = DateUtils.format(workOrder.getEndTime(), DateUtils.DATE_PATTERN);
            workOrderVO.setStartTime(String.format("%s-%s", startTime, endTime));
            result.setWorkOrder(workOrderVO);

            // 项目信息
            ProjectInfo project = engineeringProjectService.getById(workOrder.getEngineeringProjectId());
            ProjectVO projectVO = new ProjectVO();

            // 客户信息
            CompanyVO customerInfo = new CompanyVO();
            if (project != null) {
                projectVO.setId(project.getId());
                projectVO.setName(project.getName());

                Company customer = spsCompanyService.getCompanyById(project.getCompanyId());
                if (customer != null) {
                    customerInfo.setName(customer.getName());// 单位名称
                    customerInfo.setContacts(customer.getContacts());// 单位联系人
                    String contactNumber = customer.getMobile() == null ? "" : customer.getMobile();
                    if (StringUtils.isBlank(contactNumber)) {
                        contactNumber = customer.getPhone() == null ? "" : customer.getPhone();
                    }
                    customerInfo.setContactNumber(contactNumber);// 单位联系电话
                    customerInfo.setAddress(customer.getAddress());// 单位联系地址
                }
            }
            result.setCustomerInfo(customerInfo);
            result.setProject(projectVO);

            // 服务商信息
            CompanyVO facilitatorInfo = new CompanyVO();
            Company facilitator = spsCompanyService.getCompanyById(workOrder.getCompanyId());
            if (facilitator != null) {
                facilitatorInfo.setName(facilitator.getName());// 单位名称
                facilitatorInfo.setAddress(facilitator.getAddress());// 单位联系地址
                facilitatorInfo.setContacts(facilitator.getContacts());// 单位联系人
                String contactNumber = facilitator.getMobile() == null ? "" : facilitator.getMobile();
                if (StringUtils.isBlank(contactNumber)) {
                    contactNumber = facilitator.getPhone() == null ? "" : facilitator.getPhone();
                }
                facilitatorInfo.setContactNumber(contactNumber);// 单位联系电话
            }
            result.setCompanyInfo(facilitatorInfo);

            // 项目负责人
            ElectricianVO workOrderLeader = new ElectricianVO();
            Map<String, Object> leaderMap = electricianWorkOrderService
                    .getWorkOrderLeader(workOrder.getId());
            if (MapUtils.isNotEmpty(leaderMap)) {
                workOrderLeader.setUid(Long.valueOf(leaderMap.get("id")
                        .toString()));
                workOrderLeader.setName(leaderMap.get("name").toString());
            }
            result.setWorkOrderLeader(workOrderLeader);

            // 电工工单信息
            List<Map<String, Object>> mapList = electricianWorkOrderService
                    .queryMapListSocialElectrician(
                            socialWorkOrder.getWorkOrderId(),
                            socialWorkOrder.getId());
            List<Map<String, Object>> electricianWorkOrderList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(mapList)) {
                Long socialWorkOrderId = socialWorkOrder.getId();
                Date beginWorkTime = socialWorkOrder.getBeginWorkTime();
                Date endWorkTime = socialWorkOrder.getEndWorkTime();
                // 电工资质名称转换
                String certificateName = socialWorkOrderService
                        .getCertificateName(socialWorkOrder.getQualifications());

                for (Map<String, Object> map : mapList) {
                    Integer status = Integer.valueOf(map.get("status").toString());
                    if (!ElectricianWorkOrderStatus.FEE_CONFIRM.getValue().equals(status) &&
                            !ElectricianWorkOrderStatus.FINISHED.getValue().equals(status)) {
                        continue;
                    }

                    // 电工资质
                    map.put("certificate", certificateName);
                    map.put("statusText",
                            ElectricianWorkOrderStatus.getNameByKey(map.get(
                                    "status").toString()));
                    // 招募时间
                    map.put("beginWorkTime", DateUtils.format(beginWorkTime,
                            DateUtils.DATE_PATTERN));
                    map.put("endWorkTime", DateUtils.format(endWorkTime,
                            DateUtils.DATE_PATTERN));
                    // 费用
                    map.put("fee", socialWorkOrder.getFee());
                    // 实际工作时间(天)
                    Double actualWorkTime = 0D;
                    if (map.get("actualWorkTime") != null
                            && Double.valueOf(map.get("actualWorkTime")
                            .toString()) > 0D) {
                        actualWorkTime = Double.valueOf(
                                map.get("actualWorkTime").toString());
                    } else {
                        actualWorkTime = Double.valueOf(DateUtils.daysBetween(beginWorkTime,
                                endWorkTime)) + 1;
                    }
                    map.put("actualWorkTime", actualWorkTime);
                    // 费用小计
                    map.put("totalFee", BigDecimalUtil.mul(
                            socialWorkOrder.getFee(), actualWorkTime));
                    map.put("socialWorkOrderId", socialWorkOrderId);

                    // 评价信息
                    EvaluateVO evaluateVO = new EvaluateVO();
                    evaluateVO.setProcessed(0);
                    Long electricianId = Long.valueOf(map.get("electricianId")
                            .toString());
                    Long electricianWorkOrderId = Long.valueOf(map.get(
                            "electricianWorkOrderId").toString());
                    ElectricianEvaluate evaluate = electricianEvaluateService
                            .queryEntityByElectricianWorkOrderId(electricianId,
                                    electricianWorkOrderId);
                    if (evaluate != null) {
                        evaluateVO.setProcessed(1);
                        evaluateVO.setEvaluateId(evaluate.getId());
                        evaluateVO.setContent(evaluate.getContent());
                        evaluateVO.setServiceSpeed(evaluate.getServiceSpeed());
                        evaluateVO.setServiceQuality(evaluate
                                .getServiceQuality());

                        List<EvaluateAttachmentVO> attachmentVOList = new ArrayList<>();
                        List<ElectricianEvaluateAttachment> attachmentList = electricianEvaluateAttachmentService
                                .queryListByEvaluateId(evaluate.getId());
                        if (CollectionUtils.isNotEmpty(attachmentList)) {
                            for (ElectricianEvaluateAttachment attachment : attachmentList) {
                                EvaluateAttachmentVO attachmentVO = new EvaluateAttachmentVO();
                                attachmentVO.setId(attachment.getId());
                                attachmentVO.setFid(picUrl + attachment.getFid());
                                attachmentVOList.add(attachmentVO);
                            }
                        }
                        evaluateVO.setAttachment(attachmentVOList);
                    }

                    map.put("evaluate", evaluateVO);
                    electricianWorkOrderList.add(map);
                }
            }
            result.setElectricianWorkOrderList(electricianWorkOrderList);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("Social WorkOrder confirm:{}", e);
        }
        return result;
    }

    @Override
    public Map<Integer, String> getAllSocialWorkOrderStatus() {
        return SocialWorkOrderStatus.getAllStatus();
    }

    /**
     * 电工工单评价信息
     *
     * @param liquidateEvaluateReqData
     * @return
     */
    @Override
    public LiquidateEvaluateResult liquidateEvaluateForElectrician(LiquidateEvaluateReqData liquidateEvaluateReqData) {
        LiquidateEvaluateResult result = new LiquidateEvaluateResult();
        try {
            GetUserInfoResult userInfoResult = userInfoService.getUserInfo(liquidateEvaluateReqData.getUid());
            if (!userInfoResult.isSuccess()) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("登录用户信息错误");
                return result;
            }
            // 用户信息
            UserInfo userInfo = userInfoResult.getMemberInfo();

            ElectricianWorkOrder electricianWorkOrder = electricianWorkOrderService.getByOrderId(liquidateEvaluateReqData.getOrderId());
            if (electricianWorkOrder == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("电工工单不存在");
                return result;
            }

            if (!liquidateEvaluateReqData.getElectricianId().equals(electricianWorkOrder.getElectricianId())) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("电工工单不存在");
                return result;
            }

            SocialWorkOrder socialWorkOrder = socialWorkOrderService.getById(electricianWorkOrder.getSocialWorkOrderId(), null, userInfo.getCompanyId());
            if (socialWorkOrder == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("社会工单不存在");
                return result;
            }

            result.setFee(MoneyUtils.format(socialWorkOrder.getFee()));
            result.setTotalFee(MoneyUtils.format(electricianWorkOrder.getActualFee()));
            if (null != electricianWorkOrder.getActualWorkTime()) {
            	result.setActualWorkTime(electricianWorkOrder.getActualWorkTime().toString());
			} else {
				result.setActualWorkTime(String.valueOf(0));
			}

            // 评价信息
            EvaluateVO evaluateVO = new EvaluateVO();
            evaluateVO.setProcessed(0);
            Long electricianId = liquidateEvaluateReqData.getElectricianId();
            Long electricianWorkOrderId = electricianWorkOrder.getId();
            ElectricianEvaluate evaluate = electricianEvaluateService.queryEntityByElectricianWorkOrderId(electricianId, electricianWorkOrderId);
            if (evaluate != null) {
                evaluateVO.setProcessed(1);
                evaluateVO.setEvaluateId(evaluate.getId());
                evaluateVO.setContent(evaluate.getContent());
                evaluateVO.setServiceSpeed(evaluate.getServiceSpeed());
                evaluateVO.setServiceQuality(evaluate.getServiceQuality());

                List<EvaluateAttachmentVO> attachmentVOList = new ArrayList<>();
                List<ElectricianEvaluateAttachment> attachmentList = electricianEvaluateAttachmentService
                        .queryListByEvaluateId(evaluate.getId());
                if (CollectionUtils.isNotEmpty(attachmentList)) {
                    for (ElectricianEvaluateAttachment attachment : attachmentList) {
                        EvaluateAttachmentVO attachmentVO = new EvaluateAttachmentVO();
                        attachmentVO.setId(attachment.getId());
                        attachmentVO.setFid(picUrl + attachment.getFid());
                        attachmentVOList.add(attachmentVO);
                    }
                }
                evaluateVO.setAttachment(attachmentVOList);
            }
            result.setEvaluateInfo(evaluateVO);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("Electrician WorkOrder liquidate evaluate:{}", e);
        }
        return result;
    }

    /**
     * 获取预支付信息
     *
     * @param paymentReqData
     * @param company
     * @param totalMoney
     * @param orderIds
     * @return
     */
    private PreparePayResult getPreparePayResult(PaymentReqData paymentReqData, int orderType,
                                                 Company company, Double totalMoney, String orderIds) {
        // 预支付请求参数
        PreparePayReq req = new PreparePayReq();
        req.setPayMethod("PC");
        req.setUid(company.getMemberId());
        req.setIp(paymentReqData.getIp());
        req.setPayType(paymentReqData.getPayType());
        req.setOrderType(orderType);
        req.setAmount(MoneyUtils.format(totalMoney));
        req.setOrderIds(orderIds);
        req.setExtendParams(paymentReqData.getReturnUrl());

        // 网站支付返回的url(余额和微信支付暂时没有用)
        String callback = "";
        if (paymentReqData.getPayType().equals(PayType.ALIPAY.getValue())) {
            callback = String.format("%s%s", platformDomain,
                    "cp/payment/alipay/callback");
        } else if (paymentReqData.getPayType().equals(
                PayType.UNIONPAY.getValue())) {
            callback = String.format("%s%s", platformDomain,
                    "cp/payment/unionpay/callback");
        }
        req.setReturnUrl(callback);
        return payInfoService.preparePay(req);
    }

    /**
     * 构建其他支付方式(除余额外)
     *
     * @param result
     * @param preparePayResult
     * @param paymentReqData
     * @param totalMoney
     * @return
     */
    private PaymentResult buildPaymentInfo(PaymentResult result, PreparePayResult preparePayResult,
                                           PaymentReqData paymentReqData, Double totalMoney) {

        if (paymentReqData.getPayType().equals(PayType.ALIPAY.getValue())) {
            AlipayPrepayInfo alipayPrepayInfo = preparePayResult.getAlipay();
            result.setRedirectUrl(String.format("%s%s", platformDomain, "cp/payment/alipay"));
            // 用Base64加密支付宝请求数据，用到的地方在解密
            String thirdPartyPaymentInfo = new String(
                    Base64.encodeBase64(alipayPrepayInfo.getParams().getBytes()));
            result.setThirdPartyPaymentInfo(thirdPartyPaymentInfo);
            result.setOrderId(preparePayResult.getOrderId());
            result.setPayType(paymentReqData.getPayType());
            result.setOrderType("工单支付");
        } else if (paymentReqData.getPayType().equals(PayType.WEIXIN.getValue())) {
            WxpayPrepayInfo wxpayPrepayInfo = preparePayResult.getWeixinpay();
            result.setRedirectUrl(String.format("%s%s", platformDomain, "cp/payment/wxpay"));
            result.setThirdPartyPaymentInfo(wxpayPrepayInfo.getCodeUrl());
            result.setAmount(MoneyUtils.format(totalMoney));
            result.setOrderId(preparePayResult.getOrderId());
            result.setPayType(paymentReqData.getPayType());
            result.setOrderType("工单支付");
        } else if (paymentReqData.getPayType().equals(PayType.UNIONPAY.getValue())) {
            UnionPayInfo unionPayInfo = preparePayResult.getUnionpay();
            result.setRedirectUrl(String.format("%s%s", platformDomain, "cp/payment/unionpay"));
            result.setThirdPartyPaymentInfo(unionPayInfo.getTn());
            result.setAmount(MoneyUtils.format(totalMoney));
            result.setOrderId(preparePayResult.getOrderId());
            result.setPayType(paymentReqData.getPayType());
            result.setOrderType("工单支付");
        }

        return result;
    }

    @Override
    public List<Long> getAfterExpiryTimeAndUnPayOrders() {
        return socialWorkOrderService.getAfterExpiryTimeAndUnPayOrders();
    }

    @Override
    public List<Long> getBeforeExpiryTimeAndUnPayOrders(int hour) {
        return socialWorkOrderService.getBeforeExpiryTimeAndUnPayOrders(hour);
    }

    @Override
    public List<Long> getAfterExpiryTimeAndPaiedOrders() {
        return socialWorkOrderService.getAfterExpiryTimeAndPaiedOrders();
    }

    @Override
    public List<Long> getFinishOrders() {
        return socialWorkOrderService.getFinishOrders();
    }

    private String picUrl;
    private String platformDomain;

    @Value(value = "${file.download.url}")
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    @Value(value = "${portal.platform.domain}")
    public void setPlatformDomain(String platformDomain) {
        this.platformDomain = platformDomain;
    }

    @Override
    public Result handleBeforeExpiryTimeAndUnPayOrder(Long id, int hour) {
        Result result = new DefaultResult();
        try {
            if (id == null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("id不能为空");
                return result;
            }
            if (hour <= 0) {
                result.setResultCode(ResultCode.ERROR_202);
                result.setResultMessage("hour必须大于0");
                return result;
            }
            SocialWorkOrder socialWorkOrder = socialWorkOrderService
                    .getById(id);
            if (socialWorkOrder == null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("社会工单不存在");
                return result;
            }
            //是否已经提醒过了，进行判断 缓存存储 key 为 swd+orderId+hour 缓存过期时间为hour
            //TODO 
            //社会工单支付提醒
            Date nowDate = new Date();
            if (socialWorkOrder.getExpiryTime().after(nowDate)) {//
                long time = socialWorkOrder.getExpiryTime().getTime() - nowDate.getTime();
                if (time >= hour * 60 * 60 * 1000) {//xxx小时提醒
                    GetUserInfoResult getUserInfoResult = userInfoService.getUserInfoByLoginName(socialWorkOrder.getCreateUser());
                    if (!getUserInfoResult.isSuccess()) {
                        result.setResultCode(getUserInfoResult.getResultCode());
                        result.setResultMessage(getUserInfoResult.getResultMessage());
                        return result;
                    }
                    String cacheKey = CacheKey.CACHE_KEY_SOCIAL_WORK_ORDER_PAY_REMIND + socialWorkOrder.getOrderId() + CacheKey.SPLIT + hour;
                    if (!jedisUtil.exists(cacheKey)) {
                        //支付提醒了...
                        Long uid = getUserInfoResult.getMemberInfo().getUid();
                        String mobile = getUserInfoResult.getMemberInfo().getMobile();
                        Map<String, String> params = new HashMap<>();
                        params.put("orderId", socialWorkOrder.getOrderId());
                        params.put("title", socialWorkOrder.getTitle());
                        params.put("expiryTime", time / (1000 * 60 * 60) + "");
                        params.put("userName", getUserInfoResult.getCompanyInfo().getUserName());
                        messageInfoService.sendSmsAndPushMessage(uid, mobile, MessageId.SOCIAL_ORDER_PAY_REMIND, params);
                        jedisUtil.setex(cacheKey, (int) (time / 1000), socialWorkOrder.getOrderId() + CacheKey.SPLIT + hour);
                    }

                }
            }

        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("handleBeforeExpiryTimeAndUnPayOrder:{}", e);
        }
        return result;
    }

    @Override
    public Result handleAfterExpiryTimeAndPaiedOrder(Long id) {
        Result result = new DefaultResult();
        try {
            if (id == null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("id不能为空");
                return result;
            }
            SocialWorkOrder socialWorkOrder = socialWorkOrderService
                    .getById(id);
            if (socialWorkOrder == null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("社会工单不存在");
                return result;
            }
            Date nowDate = new Date();
            if (!socialWorkOrder.getExpiryTime().before(nowDate)) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("社会工单未过期，无法操作");
                return result;
            }
            if (socialWorkOrder.getStatus() != SocialWorkOrderStatus.RECRUITING.getValue()) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("社会工单不是招募中，无法操作");
                return result;
            }
            //获取审核通过（包括已确认，已开始，已完成，费用确认，已结算）的社会电工工单数量
            int count = electricianWorkOrderService.getConfirmedSocialElectricianWorkOrderCount(socialWorkOrder.getId());
            if (count > 0) {//有审核通过社会电工工单，社会工单状态变为招募结束
                SocialWorkOrder updateEntity = new SocialWorkOrder();
                updateEntity.setId(id);
                updateEntity.setModifiedTime(new Date());
                updateEntity.setModifiedUser("task");
                updateEntity.setStatus(SocialWorkOrderStatus.RECRUIT_END.getValue());
                int u = socialWorkOrderService.update(updateEntity);
                if (u < 1) {
                    result.setResultCode(ResultCode.ERROR_403);
                    result.setResultMessage("修改社会工单状态为招募结束失败");
                    return result;
                }
            } else {
                SocialWorkOrder updateEntity = new SocialWorkOrder();
                updateEntity.setId(id);
                updateEntity.setModifiedTime(new Date());
                updateEntity.setModifiedUser("task");
                updateEntity.setStatus(SocialWorkOrderStatus.CANCEL.getValue());
                int u = socialWorkOrderService.update(updateEntity);
                if (u < 1) {
                    result.setResultCode(ResultCode.ERROR_403);
                    result.setResultMessage("修改社会工单状态为取消失败");
                    return result;
                }
            }
            List<Long> ids = electricianWorkOrderService.getUnConfirmSocialElectricianWorkOrderIds(socialWorkOrder.getId());
            if (ids.size() == 0 && count == 0) {//没有人接单过期了，冻结金额要解冻到余额或者自动退款
                //TODO
            }
            if (ids.size() > 0) {//未审核的，默认改为不符合，并且发短信通知服务商和社会电工
                for (Long ewoId : ids) {
                    ElectricianWorkOrder electricianWorkOrder = electricianWorkOrderService.queryObject(ewoId);
                    if (electricianWorkOrder != null) {
                        electricianWorkOrder.setStatus(ElectricianWorkOrderStatus.FAIL.getValue());
                        electricianWorkOrder.setModifiedTime(new Date());
                        electricianWorkOrder.setModifiedUser("task");
                        int u = electricianWorkOrderService.update(electricianWorkOrder);
                        if (u > 0) {
                            GetUserInfoResult result1 = this.userInfoService.getUserInfo(electricianWorkOrder.getElectricianId());
                            if (result1.isSuccess()) {
                                Long uid = result1.getMemberInfo().getUid();
                                String mobile = result1.getMemberInfo().getMobile();
                                Map<String, String> params = new HashMap<>();
                                params.put("orderId", socialWorkOrder.getOrderId());
                                messageInfoService.sendSmsAndPushMessage(uid, mobile, MessageId.ORDER_FAIL, params);
                            }

                        }
                    }

                }

            }


        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("handleAfterExpiryTimeAndPaiedOrder:{}", e);
        }
        return result;
    }

    @Override
    public Result handleFinishOrder(Long id) {
        Result result = new DefaultResult();
        try {
            if (id == null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("id不能为空");
                return result;
            }
            SocialWorkOrder socialWorkOrder = socialWorkOrderService
                    .getById(id);
            if (socialWorkOrder == null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("社会工单不存在");
                return result;
            }
            //判断社会工单是否已经是结算状态
            if (socialWorkOrder.getStatus() == SocialWorkOrderStatus.LIQUIDATED.getValue()) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("社会工单已结算状态，无需重复操作");
                return result;
            }
            if (socialWorkOrder.getStatus() != SocialWorkOrderStatus.FINISHED.getValue()) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("社会工单不是已完成状态");
                return result;
            }
            //判断所有社会电工工单是否已经结算
            int count = electricianWorkOrderService.getUnLiquidatedSocialElectricianWorkOrderCount(socialWorkOrder.getId());
            //修改社会工单状态为已结算
            if (count == 0) {
                SocialWorkOrder updateEntity = new SocialWorkOrder();
                updateEntity.setId(id);
                updateEntity.setModifiedTime(new Date());
                updateEntity.setModifiedUser("task");
                updateEntity.setStatus(SocialWorkOrderStatus.LIQUIDATED.getValue());
                int u = socialWorkOrderService.update(updateEntity);
                if (u < 1) {
                    result.setResultCode(ResultCode.ERROR_402);
                    result.setResultMessage("修改社会工单状态为已结算失败");
                    return result;
                }
                //工单已结算 短信和消息push TODO 
            } else {//未结算，通知
                //TODO
            }

        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("handleFinishOrder:{}", e);
        }
        return result;
    }
    
    /**
     * 扫描需要退款（已取消或已结算，支付成功且未退款）的社会工单
     * @return
     */
    @Override
    public List<String> scanNeedRefundOrders(int limit){
    	return socialWorkOrderService.scanNeedRefundOrders(limit);
    }
}
