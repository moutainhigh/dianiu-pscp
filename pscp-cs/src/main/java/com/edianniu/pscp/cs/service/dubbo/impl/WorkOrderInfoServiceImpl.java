package com.edianniu.pscp.cs.service.dubbo.impl;

import com.edianniu.pscp.cs.bean.workorder.*;
import com.edianniu.pscp.cs.bean.workorder.vo.WorkOrderVO;
import com.edianniu.pscp.cs.commons.PageResult;
import com.edianniu.pscp.cs.commons.ResultCode;
import com.edianniu.pscp.cs.domain.Company;
import com.edianniu.pscp.cs.domain.WorkOrder;
import com.edianniu.pscp.cs.domain.WorkOrderEvaluate;
import com.edianniu.pscp.cs.domain.WorkOrderEvaluateAttachment;
import com.edianniu.pscp.cs.service.CompanyService;
import com.edianniu.pscp.cs.service.WorkOrderService;
import com.edianniu.pscp.cs.service.dubbo.WorkOrderInfoService;
import com.edianniu.pscp.cs.util.BizUtils;
import com.edianniu.pscp.cs.util.ThreadUtil;
import com.edianniu.pscp.message.bean.MessageInfo;
import com.edianniu.pscp.message.commons.MessageId;
import com.edianniu.pscp.message.service.dubbo.MessageInfoService;
import com.edianniu.pscp.mis.bean.GetUserInfoResult;
import com.edianniu.pscp.mis.bean.company.GetCompanyInfoReqData;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.bean.workorder.WorkOrderType;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * ClassName: WorkOrderInfoServiceImpl
 * Author: tandingbo
 * CreateTime: 2017-05-12 16:47
 */
@Service
@Repository("workOrderInfoService")
public class WorkOrderInfoServiceImpl implements WorkOrderInfoService {
    private static final Logger logger = LoggerFactory.getLogger(WorkOrderInfoServiceImpl.class);

    @Autowired
    @Qualifier("companyService")
    private CompanyService companyService;
    @Autowired
    @Qualifier("workOrderService")
    private WorkOrderService workOrderService;
    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;
    @Autowired
    @Qualifier("messageInfoService")
    private MessageInfoService messageInfoService;

    /**
     * 列表
     *
     * @param listReqData
     * @return
     */
    @Override
    public ListResult listWorkOrder(ListReqData listReqData) {
        ListResult result = new ListResult();
        try {
            ListQuery listQuery = new ListQuery();
            BeanUtils.copyProperties(listReqData, listQuery);

            GetUserInfoResult userInfoResult = userInfoService.getUserInfo(listReqData.getUid());
            if (!userInfoResult.isSuccess()) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("用户不存在");
                return result;
            }

            if (StringUtils.isBlank(listReqData.getStatus())) {
                listReqData.setStatus("underway");
            }

            if (!WorkOrderRequestType.isExist(listReqData.getStatus())) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("工单状态不存在");
                return result;
            }
            WorkOrderRequestType workOrderRequestType = WorkOrderRequestType.get(listReqData.getStatus());
            if (workOrderRequestType == null) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("工单状态不存在");
                return result;
            }
            listQuery.setStatus(workOrderRequestType.getStatus());

            if (StringUtils.isNotBlank(listReqData.getName())) {
                listQuery.setName(listReqData.getName().trim());
            }

            PageResult<WorkOrderVO> pageResult = workOrderService.selectPageWorkOrderInfo(listQuery);
            result.setWorkOrderList(pageResult.getData());
            result.setHasNext(pageResult.isHasNext());
            result.setNextOffset(pageResult.getNextOffset());
            result.setTotalCount(pageResult.getTotal());
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("WorkOrder list:{}", e);
        }
        return result;
    }

    @Override
    public DetailsResult workOrderDetails(DetailsReqData detailsReqData) {
        DetailsResult result = new DetailsResult();
        try {
            result = workOrderService.getWorkOrderDetails(detailsReqData);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("WorkOrder details:{}", e);
        }
        return result;
    }

    /**
     * 工单评价
     *
     * @param evaluateReqData
     * @return
     */
    @Override
    public EvaluateResult workOrderEvaluate(EvaluateReqData evaluateReqData) {
        EvaluateResult result = new EvaluateResult();
        try {
            GetUserInfoResult userInfoResult = userInfoService.getUserInfo(evaluateReqData.getUid());
            if (!userInfoResult.isSuccess()) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("用户不存在");
                return result;
            }
            final UserInfo userInfo = userInfoResult.getMemberInfo();

            WorkOrder workOrder = workOrderService.getWorkOrderByOrderId(evaluateReqData.getOrderId());
            if (workOrder == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("工单不存在");
                return result;
            }

            if (workOrder.getType().equals(WorkOrderType.PROSPECTING.getValue())) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("勘察工单不能进行评价");
                return result;
            }

            if (workOrder.getStatus().equals(WorkOrderStatus.EVALUATED.getValue())) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("工单不能重复评价");
                return result;
            }

            if (!workOrder.getStatus().equals(WorkOrderStatus.UN_EVALUATE.getValue())) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("工单未结束，不能评价");
                return result;
            }

            EvaluateInfo evaluateInfo = evaluateReqData.getEvaluateInfo();
            if (evaluateInfo == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("评价信息不能为空");
                return result;
            }

            if (StringUtils.isNotBlank(evaluateInfo.getContent())) {
            	if (! BizUtils.checkLength(evaluateInfo.getContent(), 1000)) {
                	result.setResultCode(ResultCode.ERROR_401);
                    result.setResultMessage("评价内容不能超过1000字");
                    return result;
    			}
			}
            
            if (evaluateInfo.getServiceQuality() == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("服务质量不能为空");
                return result;
            }
            if (evaluateInfo.getServiceSpeed() == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("服务速度不能为空");
                return result;
            }

            // 评价信息
            WorkOrderEvaluate workOrderEvaluate = new WorkOrderEvaluate();
            workOrderEvaluate.setWorkOrderId(workOrder.getId());
            workOrderEvaluate.setCustomerId(userInfo.getUid());
            workOrderEvaluate.setContent(evaluateInfo.getContent());
            workOrderEvaluate.setServiceQuality(evaluateInfo.getServiceQuality());
            workOrderEvaluate.setServiceSpeed(evaluateInfo.getServiceSpeed());

            // 评价图片信息
            List<WorkOrderEvaluateAttachment> attachmentList = new ArrayList<>();
            if (evaluateInfo.getEvaluateImageArray() != null &&
                    evaluateInfo.getEvaluateImageArray().length > 0) {
                for (String evaluateImage : evaluateInfo.getEvaluateImageArray()) {
                    WorkOrderEvaluateAttachment attachment = new WorkOrderEvaluateAttachment();
                    attachment.setType(1L);
                    attachment.setFid(evaluateImage);
                    attachmentList.add(attachment);
                }
            }

            // 评价人
            workOrder.setAppraiser(userInfo.getLoginName());
            workOrderService.updateEvaluateInfo(workOrder, workOrderEvaluate, attachmentList);

            // 异步发送推送消息
            final Long memberId = workOrder.getMemberId();// 派单人
            final String workOrderName = workOrder.getName();// 工单名称
            ThreadUtil.getSortTimeOutThread(new Runnable() {
                @Override
                public void run() {
                    // 获取客户公司信息
                    String customer_name = "";// 客户名称
                    GetCompanyInfoReqData reqData = new GetCompanyInfoReqData();
                    reqData.setUid(userInfo.getUid());
                    Company comapny = companyService.getCompanyByMemberId(userInfo.getUid());
                    if (comapny != null) {
                        customer_name = comapny.getName();
                    }

                    // 发送推送消息
                    Map<String, String> params = new HashMap<>();
                    params.put("name", workOrderName);
                    params.put("customer_name", customer_name);

                    // 派单人->sms+push
                    GetUserInfoResult dispatchPersonResult = userInfoService.getUserInfo(memberId);
                    if (dispatchPersonResult.isSuccess()) {
                        UserInfo dispatchPerson = dispatchPersonResult.getMemberInfo();
                        messageInfoService.sendSmsAndPushMessage(dispatchPerson.getUid(), dispatchPerson.getMobile(), MessageId.ORDER_EVALUATE_FACILITATOR, params);
                    }

                    //1)自己->push
                    MessageInfo messageInfo = new MessageInfo();
                    messageInfo.setMsgId(MessageId.ORDER_EVALUATE_CUSTOMER.getValue());
                    messageInfo.setUid(userInfo.getUid());
                    messageInfo.setPushTime(new Date());
                    messageInfo.setParams(params);
                    messageInfoService.sendPushMessage(messageInfo);
                }
            });
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("WorkOrder Evaluate:{}", e);
        }
        return result;
    }
}
