package com.edianniu.pscp.mis.service.dubbo.impl;

import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.message.bean.MessageInfo;
import com.edianniu.pscp.message.commons.MessageId;
import com.edianniu.pscp.message.service.dubbo.MessageInfoService;
import com.edianniu.pscp.mis.bean.GetUserInfoResult;
import com.edianniu.pscp.mis.bean.WalletDealType;
import com.edianniu.pscp.mis.bean.WalleFundType;
import com.edianniu.pscp.mis.bean.WalletType;
import com.edianniu.pscp.mis.bean.defectrecord.vo.DefectRecordVO;
import com.edianniu.pscp.mis.bean.electricianworkorder.*;
import com.edianniu.pscp.mis.bean.history.vo.FacilitatorHistoryVO;
import com.edianniu.pscp.mis.bean.query.electricianworkorder.ListQuery;
import com.edianniu.pscp.mis.bean.query.history.FacilitatorHistoryQuery;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.bean.workorder.WorkOrderType;
import com.edianniu.pscp.mis.commons.Constants;
import com.edianniu.pscp.mis.commons.PageResult;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.domain.*;
import com.edianniu.pscp.mis.service.*;
import com.edianniu.pscp.mis.service.dubbo.ElectricianWorkOrderInfoService;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.mis.util.BigDecimalUtil;
import com.edianniu.pscp.mis.util.BizUtils;
import com.edianniu.pscp.mis.util.DateUtils;
import com.edianniu.pscp.mis.util.MoneyUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * ClassName: WorkOrderInfoServiceImpl
 * Author: tandingbo
 * CreateTime: 2017-04-13 10:04
 */
@Service
@Repository("electricianWorkOrderInfoService")
public class ElectricianWorkOrderInfoServiceImpl implements ElectricianWorkOrderInfoService {
    private static final Logger logger = LoggerFactory.getLogger(ElectricianWorkOrderInfoServiceImpl.class);

    @Autowired
    @Qualifier("messageInfoService")
    private MessageInfoService messageInfoService;

    @Autowired
    @Qualifier("companyService")
    private CompanyService companyService;
    
    @Autowired
    @Qualifier("companyElectricianService")
    private CompanyElectricianService companyElectricianService;

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;

    @Autowired
    @Qualifier("workOrderService")
    private WorkOrderService workOrderService;

    @Autowired
    @Qualifier("electricianService")
    private ElectricianService electricianService;

    @Autowired
    @Qualifier("engineeringProjectService")
    private EngineeringProjectService engineeringProjectService;

    @Autowired
    @Qualifier("electricianWorkLogService")
    private ElectricianWorkLogService electricianWorkLogService;

    @Autowired
    @Qualifier("electricianWorkOrderService")
    private ElectricianWorkOrderService electricianWorkOrderService;

    @Autowired
    @Qualifier("electricianWorkLogAttachmentService")
    private ElectricianWorkLogAttachmentService electricianWorkLogAttachmentService;

    @Autowired
    @Qualifier("socialWorkOrderService")
    private SocialWorkOrderService socialWorkOrderService;

    @Autowired
    @Qualifier("electricianEvaluateService")
    private ElectricianEvaluateService electricianEvaluateService;
    @Autowired
    @Qualifier("electricianEvaluateAttachmentService")
    private ElectricianEvaluateAttachmentService electricianEvaluateAttachmentService;

    @Autowired
    @Qualifier("companyCustomerService")
    private CompanyCustomerService companyCustomerService;

    @Autowired
    @Qualifier("workOrderDefectRecordService")
    private WorkOrderDefectRecordService workOrderDefectRecordService;

    @Override
    public ListResult listWorkOrders(ListReqData listReqData) {
        ListResult result = new ListResult();
        try {
            if (listReqData.getOffset() == null || listReqData.getOffset() < 0) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("offset 参数必须大于等于0");
                return result;
            }
            if (StringUtils.isBlank(listReqData.getLatitude())) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("latitude 不能为空");
                return result;
            }
            if (!BizUtils.isNumber(listReqData.getLatitude())) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("latitude 必须为数字");
                return result;
            }
            if (StringUtils.isBlank(listReqData.getLongitude())) {
                result.setResultCode(ResultCode.ERROR_403);
                result.setResultMessage("longitude 不能为空");
                return result;
            }
            if (!BizUtils.isNumber(listReqData.getLongitude())) {
                result.setResultCode(ResultCode.ERROR_403);
                result.setResultMessage("longitude 必须为数字");
                return result;
            }

            if (StringUtils.isBlank(listReqData.getStatus())) {
                result.setResultCode(ResultCode.ERROR_406);
                result.setResultMessage("status 不能为空");
                return result;
            }

            // 工单查询状态检查
            Boolean isExist = ElectricianWorkOrderRequestType.isExist(listReqData.getStatus());
            if (!isExist) {
                result.setResultCode(ResultCode.ERROR_407);
                result.setResultMessage("工单查询状态错误");
                return result;
            }

            // 根据电工类型及工单状态获取电工工单查询状态
            ElectricianWorkOrderRequestType workOrderRequestType = ElectricianWorkOrderRequestType.get(listReqData.getStatus());
            if (workOrderRequestType == null) {
                result.setResultCode(ResultCode.ERROR_407);
                result.setResultMessage("工单查询状态错误");
                return result;
            }

            UserInfo userInfo = new UserInfo();
            GetUserInfoResult userInfoResult = userInfoService.getUserInfo(listReqData.getUid());
            if (userInfoResult.getResultCode() == ResultCode.SUCCESS) {
                userInfo = userInfoResult.getMemberInfo();
            }

            // 服务商公司ID
            Long companyId = listReqData.getCompanyId();
            if (companyId == null && userInfo.getCompanyId() != null && !userInfo.getCompanyId().equals(0L)) {
                companyId = userInfo.getCompanyId();
            }
            
            // TODO 待测试
            //企业电工 -1 -2 5 3
            //社会电工 -1 -2 5 
            //企业电工-->解绑-->普通电工，查询已完成 -1 -2 3
            //companyId 根据判断 用户历史服务商，这个用户确定 
            Integer type = 0; 
            if (userInfo.isCompanyElectrician()) {
            	type = Constants.ELECTRICIAN_TYPE_COMPANY_ELECTRICIAN;
			} else {
				type = Constants.ELECTRICIAN_TYPE_SOCIAL_ELECTRICIAN;
				//获取历史服务商
				FacilitatorHistoryQuery listQuery = new FacilitatorHistoryQuery();
				listQuery.setUid(listReqData.getUid());
				PageResult<FacilitatorHistoryVO> facilitatorResult = companyElectricianService.queryFacilitatorHistory(listQuery);
				List<FacilitatorHistoryVO> facilitatorList = facilitatorResult.getData();
				for (FacilitatorHistoryVO facilitatorHistoryVO : facilitatorList) {
					Long id = facilitatorHistoryVO.getId();
					if (id.equals(companyId)) {
						type = Constants.ELECTRICIAN_TYPE_UNBUNG_ELECTRICIAN;
						break;
					}
				}
			}
            Integer[] statusArray = null;
            if (workOrderRequestType.equals(ElectricianWorkOrderRequestType.FINISHED)) {
            	statusArray = workOrderRequestType.getStatusByType(type);
			} else {
				statusArray = workOrderRequestType.getStatus(userInfo.isCompanyElectrician());
			}
            if (statusArray == null || statusArray.length < 1) {
                result.setResultCode(ResultCode.ERROR_407);
                result.setResultMessage("工单查询状态错误");
                return result;
            }

            ListQuery listQuery = new ListQuery();
            listQuery.setLatitude(Double.parseDouble(listReqData.getLatitude().trim()));
            listQuery.setLongitude(Double.parseDouble(listReqData.getLongitude().trim()));
            listQuery.setOffset(listReqData.getOffset());
            listQuery.setStatus(statusArray);
            listQuery.setCompanyId(companyId);

            listQuery.setUid(listReqData.getUid());
            if (StringUtils.isNotBlank(listReqData.getType()) && "chief".equals(listReqData.getType())) {
                listQuery.setCheckOptionId(Constant.LEADER_ELECTRICIAN_CHECK_OPTION_ID);
                listQuery.setWorkOrderLeader(1);
            } else {
                listQuery.setWorkOrderLeader(0);
            }

            PageResult<ListQueryResultInfo> pageResult = electricianWorkOrderService.queryList(listQuery);
            if (pageResult.getData() != null) {
                result.setWorkOrders(pageResult.getData());
            }
            result.setHasNext(pageResult.isHasNext());
            result.setNextOffset(pageResult.getNextOffset());
            result.setTotalCount(pageResult.getTotal());
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("nearbyBuss:{}", e);
        }
        return result;
    }

    @Override
    public DetailResult workOrdersDetail(DetailReqData detailReqData) {
        DetailResult result = new DetailResult();
        try {
            // 获取电工工单信息
            ElectricianWorkOrder electricianWorkOrder = electricianWorkOrderService.getEntityByOrderId(detailReqData.getUid(), detailReqData.getOrderId());
            if (electricianWorkOrder != null) {
                // 工单ID
                Long workOrderId = electricianWorkOrder.getWorkOrderId();
                // 工单信息
                WorkOrder workOrder = workOrderService.getEntityById(workOrderId);

                // 订单详情
                OrderDetailInfo orderDetail = new OrderDetailInfo();
                orderDetail.setId(electricianWorkOrder.getId());
                orderDetail.setStatus(electricianWorkOrder.getStatus());
                orderDetail.setTitle(electricianWorkOrder.getCheckOption());
                // 电工工单编号
                orderDetail.setOrderId(electricianWorkOrder.getOrderId());
                if (electricianWorkOrder.getWorkOrderLeader().equals(1)) {
                    // 负责人工单——总工单编号
                    orderDetail.setOrderId(workOrder.getOrderId());
                }

                orderDetail.setName(workOrder.getName());
                orderDetail.setContent(workOrder.getContent());
                orderDetail.setAddress(workOrder.getAddress());
                orderDetail.setLatitude(workOrder.getLatitude() == null ? "0" : workOrder.getLatitude().toString());
                orderDetail.setLongitude(workOrder.getLongitude() == null ? "0" : workOrder.getLongitude().toString());
                orderDetail.setPubTime(DateUtils.format(workOrder.getCreateTime(), DateUtils.DATE_TIME_PATTERN));

                // 总工单工作时间
                String startDate = DateUtils.format(workOrder.getStartTime(), DateUtils.DATE_PATTERN);
                String endDate = DateUtils.format(workOrder.getEndTime(), DateUtils.DATE_PATTERN);
                orderDetail.setWorkTime(String.format("%s~%s", startDate, endDate));// 格式：2017-04-01~2017-04-15

                orderDetail.setActualStartTime(DateUtils.format(workOrder.getActualStartTime(), DateUtils.DATE_PATTERN));
                orderDetail.setActualEndTime(DateUtils.format(workOrder.getActualEndTime(), DateUtils.DATE_PATTERN));

                // jsonArray数据结构：[{id:10001,name:'xxx',checked:1}，{id:1002,name:'xxx',checked:0},{id:-1,name:'自定义',checked:0}]
                // 危险有害因数
                orderDetail.setHazardFactor(workOrder.getHazardFactorIdentifications());
                orderDetail.setHazardFactorOther(workOrder.getIdentificationOther());

                // 安全措施
                orderDetail.setSafetyMeasures(workOrder.getSafetyMeasures());
                orderDetail.setSafetyMeasuresOther(workOrder.getSafetyMeasuresOther());

                // 携带机械或设备
                orderDetail.setCarryingTools(workOrder.getCarryingTools());

                // 获取检修项目及人员：[{name:'xxxx1',personnel:['xx1','xx2']},{name:'xxxx2',personnel:['xx3','xx4','xx5']}]
                String checkOption = structureCheckOptionInfo(workOrderId);
                orderDetail.setCheckOption(checkOption);

                // 获取工单项目负责人信息
                Map<String, Object> mapProjectLeader = electricianWorkOrderService.getProjectLeader(workOrderId);
                if (MapUtils.isEmpty(mapProjectLeader)) {
                    mapProjectLeader = new HashMap<String, Object>();
                }
                String projectLeader = mapProjectLeader.get("projectLeader") == null ? "" : mapProjectLeader.get("projectLeader").toString();
                orderDetail.setProjectLeader(projectLeader);// 项目负责人
                String contactTel = mapProjectLeader.get("contactTel") == null ? "" : mapProjectLeader.get("contactTel").toString();
                orderDetail.setContactTel(contactTel);// 项目负责人联系电话

                // 扩展信息
                Map<String, Object> extendInfoMap = new HashMap<>();
                // 工单负责人标识
                extendInfoMap.put("isLeader", Constants.TAG_NO);
                Long leaderUid = mapProjectLeader.get("uid") == null ? 0L : (Long) mapProjectLeader.get("uid");
                if (leaderUid.equals(detailReqData.getUid())) {
                    extendInfoMap.put("isLeader", Constants.TAG_YES);// 是工单负责人
                }
                // 工单负责人开始工作标识
                extendInfoMap.put("isLeaderStart", Constants.TAG_NO);
                if (workOrder.getStatus() >= WorkOrderStatus.EXECUTING.getValue()) {
                    extendInfoMap.put("isLeaderStart", Constants.TAG_YES);
                }
                // 电工工单编号
                extendInfoMap.put("eOrderId", electricianWorkOrder.getOrderId());
                orderDetail.setExtendInfo(JSON.toJSONString(extendInfoMap));

                // 工单类型名称
                orderDetail.setTypeName(WorkOrderType.getDesc(workOrder.getType()));
                orderDetail.setType(workOrder.getType());

                orderDetail.setFee("0.00元");// 费用(元/天)
                Double fee = 0D;// 电工费用
                if (electricianWorkOrder.getType().equals(ElectricianType.SOCIETY.getValue())
                        && !electricianWorkOrder.getSocialWorkOrderId().equals(0L)) {
                    // 社会电工费用
                    SocialWorkOrder socialWorkOrder = socialWorkOrderService.getEntityById(electricianWorkOrder.getSocialWorkOrderId());
                    if (socialWorkOrder != null) {
                        fee = socialWorkOrder.getFee();
                        orderDetail.setFee(String.format("%s元/天", MoneyUtils.format(fee)));// 费用(元/天)
                        orderDetail.setDemandContent(socialWorkOrder.getContent());// 需求描述
                        orderDetail.setTitle(socialWorkOrder.getTitle());// 标题

                        // 社会工单工作时间
                        String beginWorkTime = DateUtils.format(socialWorkOrder.getBeginWorkTime(), DateUtils.DATE_PATTERN);
                        String endWorkTime = DateUtils.format(socialWorkOrder.getEndWorkTime(), DateUtils.DATE_PATTERN);
                        orderDetail.setWorkTime(String.format("%s~%s", beginWorkTime, endWorkTime));// 格式：2017-04-01~2017-04-15
                    }
                }
                result.setOrderDetail(orderDetail);

                // 服务商信息
                CompanyInfo companyInfo = new CompanyInfo();
                Company serviceCompany = companyService.getById(workOrder.getCompanyId());
                if (serviceCompany == null) {
                    serviceCompany = new Company();
                }
                companyInfo.setId(serviceCompany.getId());
                companyInfo.setName(serviceCompany.getName());
                companyInfo.setLeader(serviceCompany.getContacts());
                companyInfo.setContactTel(serviceCompany.getPhone());
                result.setCompayInfo(companyInfo);

                // 客户信息
                CustmerInfo custmerInfo = new CustmerInfo();

                // 获取项目信息
                EngineeringProject project = engineeringProjectService.getById(workOrder.getEngineeringProjectId());
                if (project != null) {
                    CompanyCustomer companyCustomer = companyCustomerService.getById(project.getCustomerId());
                    if (companyCustomer == null) {
                        companyCustomer = new CompanyCustomer();
                    }
                    custmerInfo.setId(companyCustomer.getId());
                    custmerInfo.setName(companyCustomer.getCpName());
                    custmerInfo.setLeader(companyCustomer.getCpContact());
                    custmerInfo.setContactTel(companyCustomer.getContactTel());
                }
                result.setCustmerInfo(custmerInfo);

                // 结算信息
                SettlementInfo settlementInfo = new SettlementInfo();
                // 费用确认、结算后获取结算信息
                if (electricianWorkOrder.getType().equals(ElectricianType.SOCIETY.getValue()) &&
                        electricianWorkOrder.getStatus() >= ElectricianWorkOrderStatus.FEE_CONFIRM.getValue()) {
                    settlementInfo.setWorkTime(String.valueOf(electricianWorkOrder.getActualWorkTime()));
                    settlementInfo.setAmount(String.valueOf(electricianWorkOrder.getActualFee()));
                    settlementInfo.setSettlementPayStatus(electricianWorkOrder.getSettlementPayStatus());
                }
                result.setSettlementInfo(settlementInfo);

                //评价信息
                EvaluateInfo evaluateInfo = new EvaluateInfo();
                // 结算后获取电工评价信息
                if (electricianWorkOrder.getStatus().equals(ElectricianWorkOrderStatus.LIQUIDATED.getValue())) {
                    ElectricianEvaluate electricianEvaluate = electricianEvaluateService.getEntityByElectricianId(detailReqData.getUid(), electricianWorkOrder.getId());
                    if (electricianEvaluate != null) {
                        evaluateInfo.setContent(electricianEvaluate.getContent());
                        evaluateInfo.setServiceSpeed(String.valueOf(electricianEvaluate.getServiceSpeed()));
                        evaluateInfo.setServiceQuality(String.valueOf(electricianEvaluate.getServiceQuality()));
                        // 附件信息
                        StringBuilder attachment = new StringBuilder();
                        List<ElectricianEvaluateAttachment> attachmentList = electricianEvaluateAttachmentService.selectAllByEvaluateId(electricianEvaluate.getId());
                        if (CollectionUtils.isNotEmpty(attachmentList)) {
                            for (ElectricianEvaluateAttachment evaluateAttachment : attachmentList) {
                                if (StringUtils.isNoneBlank(attachment)) {
                                    attachment.append(",");
                                }
                                attachment.append(evaluateAttachment.getFid());
                            }
                        }
                        evaluateInfo.setAttachment(attachment.toString());
                    }
                }
                result.setEvaluateInfo(evaluateInfo);

                // 修复缺陷记录
                if (StringUtils.isNotBlank(workOrder.getDefectRecords())) {
                    String[] strArray = workOrder.getDefectRecords().trim().split(",");
                    List<Long> ids = new ArrayList<>();
                    if (strArray.length > 0) {
                        for (String str : strArray) {
                            ids.add(Long.valueOf(str));
                        }
                    }

                    List<DefectRecordVO> defectRepairList = workOrderDefectRecordService.getRepairDefectRecord(ids);
                    result.setDefectRepairList(defectRepairList);
                }

                // 派单人信息
                DispatchUserInfo dispatchUserInfo = null;
                GetUserInfoResult userInfoResult = userInfoService.getUserInfoByLoginName(workOrder.getCreateUser());
                if (userInfoResult.isSuccess()) {
                    dispatchUserInfo = new DispatchUserInfo();
                    UserInfo userInfo = userInfoResult.getMemberInfo();
                    dispatchUserInfo.setContactTel(userInfo.getMobile());
                    dispatchUserInfo.setUserName(userInfo.getRealName());
                }
                result.setDispatchUserInfo(dispatchUserInfo);
            }
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("workOrdersDetail:{}", e);
        }
        return result;
    }

    /**
     * 构建检修项目信息
     *
     * @param workOrderId
     * @return
     */
    private String structureCheckOptionInfo(Long workOrderId) {
        Map<String, Object> selectAllMap = new HashMap<>();
        List<Map<String, Object>> checkOptionList = new ArrayList<>();

        selectAllMap.put("workOrderId", workOrderId);
        selectAllMap.put("type", ElectricianWorkOrderType.COMPANY_ELECTRICIAN_WD.getValue());
        List<Map<String, Object>> electricianWorkOrderList = electricianWorkOrderService.selectAllCheckOption(selectAllMap);
        if (CollectionUtils.isNotEmpty(electricianWorkOrderList)) {
            Map<String, List<String>> checkOptionMap = new HashMap<>();
            for (Map<String, Object> map : electricianWorkOrderList) {
                if (map.get("checkOption") == null || map.get("userName") == null ||
                        map.get("workOrderLeader").toString().equals("1")) {
                    continue;
                }

                String checkOption = map.get("checkOption").toString();
                List<String> personnelList = new ArrayList<>();
                if (checkOptionMap.containsKey(checkOption)) {
                    personnelList = checkOptionMap.get(checkOption);
                }
                personnelList.add(map.get("userName").toString());
                checkOptionMap.put(checkOption, personnelList);

            }
            if (MapUtils.isNotEmpty(checkOptionMap)) {
                for (Map.Entry<String, List<String>> entry : checkOptionMap.entrySet()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", entry.getKey());
                    map.put("personnel", JSON.toJSONString(entry.getValue()));
                    checkOptionList.add(map);
                }
            }
        }

        return JSON.toJSONString(checkOptionList);
    }

    /**
     * 电工工单确认
     *
     * @param confirmReqData
     * @return
     */
    @Override
    public ConfirmResult workOrdersConfirm(ConfirmReqData confirmReqData) {
        // 是否是工单负责人(默认否)
        Boolean leader = false;
        ConfirmResult result = new ConfirmResult();

        try {
            UserInfo userInfo = new UserInfo();
            GetUserInfoResult userInfoResult = userInfoService.getUserInfo(confirmReqData.getUid());
            if (userInfoResult.getResultCode() == ResultCode.SUCCESS) {
                userInfo = userInfoResult.getMemberInfo();
            }
            if (!userInfo.getIsElectrician().equals(Constants.TAG_YES)) {
                result.setResultCode(ResultCode.ERROR_405);
                result.setResultMessage("没有电工的权限");
                return result;
            }
            if (!userInfo.isCompanyElectrician()) {
                result.setResultCode(ResultCode.ERROR_405);
                result.setResultMessage("社会电工没有权限");
                return result;
            }

            if (StringUtils.isBlank(confirmReqData.getOrderId())) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("orderId不能为空");
                return result;
            }

            ElectricianWorkOrder electricianWorkOrder = electricianWorkOrderService.getEntityByOrderId(confirmReqData.getUid(), confirmReqData.getOrderId());
            if (electricianWorkOrder == null) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("订单不存在");
                return result;
            }
            if (electricianWorkOrder.getStatus() >= ElectricianWorkOrderStatus.CONFIRMED.getValue()) {
                result.setResultCode(ResultCode.ERROR_403);
                result.setResultMessage("订单已经确认过了，无须重复操作");
                return result;
            }

            // 是否是工单负责人
            if (Constants.TAG_YES == electricianWorkOrder.getWorkOrderLeader()
                    && confirmReqData.getUid().equals(electricianWorkOrder.getElectricianId())) {
                leader = true;
            }

            ElectricianWorkOrder updateEntity = new ElectricianWorkOrder();
            updateEntity.setConfirmTime(new Date());
            updateEntity.setOrderId(confirmReqData.getOrderId());
            updateEntity.setElectricianId(confirmReqData.getUid());
            updateEntity.setStatus(ElectricianWorkOrderStatus.CONFIRMED.getValue());
            updateEntity.setWorkOrderId(electricianWorkOrder.getWorkOrderId());

            // 工单确认
            electricianWorkOrderService.updateEntityByCondition(null, null, updateEntity, leader);

            // 消息推送
            WorkOrder workOrder = workOrderService.getEntityById(electricianWorkOrder.getWorkOrderId());
            if (workOrder != null) {
                Map<String, String> params = new HashMap<>();
                params.put("name", workOrder.getName());

                // 电工姓名
                Electrician electrician = electricianService.getByUid(userInfo.getUid());
                if (electrician != null) {
                    params.put("user_name", String.format("【%s】", electrician.getUserName()));
                }

                //3)工单负责人->sms+push
                Long leaderId = null;
                Map<String, Object> workOrderLeader = new HashMap<>();
                if (!electricianWorkOrder.getWorkOrderLeader().equals(Constants.TAG_YES)) {
                    workOrderLeader = electricianWorkOrderService.getProjectLeader(electricianWorkOrder.getWorkOrderId());
                }
                if (MapUtils.isNotEmpty(workOrderLeader)) {
                    if (workOrderLeader.get("uid") != null && workOrderLeader.get("contactTel") != null) {
                        leaderId = Long.valueOf(workOrderLeader.get("uid").toString());
                        messageInfoService.sendSmsAndPushMessage((Long) workOrderLeader.get("uid"), workOrderLeader.get("contactTel").toString(), MessageId.ORDER_CONFIRM, params);
                    }
                }

                //2)派单人->sms+push
                GetUserInfoResult dispatchPersonResult = userInfoService.getUserInfo(workOrder.getMemberId());
                if (dispatchPersonResult.isSuccess()) {
                    UserInfo dispatchPerson = dispatchPersonResult.getMemberInfo();
                    if (leaderId == null || !dispatchPerson.getUid().equals(leaderId)) {
                        messageInfoService.sendSmsAndPushMessage(dispatchPerson.getUid(), dispatchPerson.getMobile(), MessageId.ORDER_CONFIRM, params);
                    }
                }

                //1)自己->push
                params.put("user_name", "您");
                MessageInfo messageInfo = new MessageInfo();
                messageInfo.setMsgId(MessageId.ORDER_CONFIRM.getValue());
                messageInfo.setUid(userInfo.getUid());
                messageInfo.setPushTime(new Date());
                messageInfo.setParams(params);
                messageInfoService.sendPushMessage(messageInfo);
            }
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("nearbyBuss:{}", e);
        }

        return result;
    }

    /**
     * 电工工单取消
     *
     * @param cancelReqData
     * @return
     */
    @Override
    public CancelResult cancelWorkOrder(CancelReqData cancelReqData) {
        CancelResult result = new CancelResult();

        try {
            UserInfo userInfo = new UserInfo();
            GetUserInfoResult userInfoResult = userInfoService.getUserInfo(cancelReqData.getUid());
            if (userInfoResult.getResultCode() == ResultCode.SUCCESS) {
                userInfo = userInfoResult.getMemberInfo();
            }
            if (!userInfo.getIsElectrician().equals(Constants.TAG_YES)) {
                result.setResultCode(ResultCode.ERROR_405);
                result.setResultMessage("没有电工的权限");
                return result;
            }

            if (StringUtils.isBlank(cancelReqData.getOrderId())) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("orderId不能为空");
                return result;
            }

            ElectricianWorkOrder electricianWorkOrder = electricianWorkOrderService.getEntityByOrderId(cancelReqData.getUid(), cancelReqData.getOrderId());
            if (electricianWorkOrder == null) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("工单不存在");
                return result;
            }

            if (ElectricianWorkOrderStatus.CANCELED.getValue().equals(electricianWorkOrder.getStatus())) {
                result.setResultCode(ResultCode.ERROR_403);
                result.setResultMessage("工单已取消，无须重复操作");
                return result;
            }

            if (!ElectricianWorkOrderStatus.CANCELED.getValue().equals(electricianWorkOrder.getStatus())
                    && electricianWorkOrder.getStatus() > ElectricianWorkOrderStatus.EXECUTING.getValue()) {
                result.setResultCode(ResultCode.ERROR_404);
                result.setResultMessage("工单无法取消");
                return result;
            }

            // 工单负责人不能取消
            if (Constants.TAG_YES == electricianWorkOrder.getWorkOrderLeader()
                    && cancelReqData.getUid().equals(electricianWorkOrder.getElectricianId())) {
                result.setResultCode(ResultCode.ERROR_404);
                result.setResultMessage("工单无法取消");
                return result;
            }

            ElectricianWorkOrder updateEntity = new ElectricianWorkOrder();
            updateEntity.setCancleTime(new Date());
            updateEntity.setId(electricianWorkOrder.getId());
            updateEntity.setOrderId(cancelReqData.getOrderId());
            updateEntity.setElectricianId(cancelReqData.getUid());
            updateEntity.setStatus(ElectricianWorkOrderStatus.CANCELED.getValue());

            // 工单取消
            electricianWorkOrderService.updateEntityByCondition(null, null, updateEntity, false);

            // 消息推送
            WorkOrder workOrder = workOrderService.getEntityById(electricianWorkOrder.getWorkOrderId());
            if (workOrder != null) {
                Map<String, String> params = new HashMap<>();
                params.put("name", workOrder.getName());

                // 电工姓名
                Electrician electrician = electricianService.getByUid(userInfo.getUid());
                if (electrician != null) {
                    params.put("user_name", String.format("【%s】", electrician.getUserName()));
                }

                //3)工单负责人->sms+push
                if (!electricianWorkOrder.getWorkOrderLeader().equals(Constants.TAG_YES)) {
                    Map<String, Object> map = electricianWorkOrderService.getProjectLeader(electricianWorkOrder.getWorkOrderId());
                    if (MapUtils.isNotEmpty(map)) {
                        if (map.get("uid") != null && map.get("contactTel") != null) {
                            messageInfoService.sendSmsAndPushMessage((Long) map.get("uid"), map.get("contactTel").toString(), MessageId.ORDER_CANCEL, params);
                        }
                    }
                }

                //2)派单人->sms+push
                GetUserInfoResult dispatchPersonResult = userInfoService.getUserInfo(workOrder.getMemberId());
                if (dispatchPersonResult.isSuccess()) {
                    UserInfo dispatchPerson = dispatchPersonResult.getMemberInfo();
                    messageInfoService.sendSmsAndPushMessage(dispatchPerson.getUid(), dispatchPerson.getMobile(), MessageId.ORDER_CANCEL, params);
                }

                //1)自己->push
                params.put("user_name", "您");
                MessageInfo messageInfo = new MessageInfo();
                messageInfo.setMsgId(MessageId.ORDER_CANCEL.getValue());
                messageInfo.setUid(userInfo.getUid());
                messageInfo.setPushTime(new Date());
                messageInfo.setParams(params);
                messageInfoService.sendPushMessage(messageInfo);
            }
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("nearbyBuss:{}", e);
        }

        return result;
    }

    /**
     * 开始工作
     *
     * @param startWorkReqData
     * @return
     */
    @Override
    public StartWorkResult startWork(StartWorkReqData startWorkReqData) {
        // 是否是工单负责人(默认否)
        Boolean leader = false;
        StartWorkResult result = new StartWorkResult();

        try {
            UserInfo userInfo = new UserInfo();
            GetUserInfoResult userInfoResult = userInfoService.getUserInfo(startWorkReqData.getUid());
            if (userInfoResult.getResultCode() == ResultCode.SUCCESS) {
                userInfo = userInfoResult.getMemberInfo();
            }
            if (!userInfo.getIsElectrician().equals(Constants.TAG_YES)) {
                result.setResultCode(ResultCode.ERROR_408);
                result.setResultMessage("没有电工的权限");
                return result;
            }

            if (StringUtils.isBlank(startWorkReqData.getOrderId())) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("orderId不能为空");
                return result;
            }

            ElectricianWorkOrder electricianWorkOrder = electricianWorkOrderService.getEntityByOrderId(startWorkReqData.getUid(), startWorkReqData.getOrderId());
            if (electricianWorkOrder == null) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("订单不存在");
                return result;
            }

            if (electricianWorkOrder.getStatus() >= ElectricianWorkOrderStatus.EXECUTING.getValue()) {
                result.setResultCode(ResultCode.ERROR_403);
                result.setResultMessage("订单已开始工作了，无须重复操作");
                return result;
            }

            // 获取工单信息
            WorkOrder workOrder = workOrderService.getEntityById(electricianWorkOrder.getWorkOrderId());
            if (workOrder == null) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("订单不存在");
                return result;
            }

            // 是否是工单负责人
            if (Constants.TAG_YES == electricianWorkOrder.getWorkOrderLeader()
                    && startWorkReqData.getUid().equals(electricianWorkOrder.getElectricianId())) {
                leader = true;

                if (workOrder.getStartTime().after(new Date())) {
                    result.setResultCode(ResultCode.ERROR_403);
                    result.setResultMessage("工单未到开始工作日期，请耐心等待");
                    return result;
                }

                if (StringUtils.isBlank(startWorkReqData.getStartTime())) {
                    result.setResultCode(ResultCode.ERROR_405);
                    result.setResultMessage("startTime不能为空(负责人)");
                    return result;
                }

            }

            // 检查工单状态(是否已经开始)
            if (!leader) {
                if (workOrder.getStatus() < WorkOrderStatus.EXECUTING.getValue()) {
                    result.setResultCode(ResultCode.ERROR_407);
                    result.setResultMessage("工单未开始，操作无效");
                    return result;
                }
            }

            Date beginTime = null;
            if (StringUtils.isNoneBlank(startWorkReqData.getStartTime())) {
                beginTime = DateUtils.parse(startWorkReqData.getStartTime(), DateUtils.DATE_TIME_PATTERN);
                if (beginTime == null) {
                    result.setResultCode(ResultCode.ERROR_406);
                    result.setResultMessage("startTime格式错误");
                    return result;
                }
            }

            if (electricianWorkOrder.getType().equals(ElectricianWorkOrderType.SOCIAL_ELECTRICIAN_WD.getValue())) {
                SocialWorkOrder socialWorkOrder = socialWorkOrderService.getEntityById(electricianWorkOrder.getSocialWorkOrderId());
                if (socialWorkOrder == null) {
                    result.setResultCode(ResultCode.ERROR_407);
                    result.setResultMessage("工单异常");
                    return result;
                }

                if (socialWorkOrder.getBeginWorkTime().after(new Date())) {
                    result.setResultCode(ResultCode.ERROR_408);
                    result.setResultMessage("工单未到开始工作日期，请耐心等待");
                    return result;
                }
            }

            ElectricianWorkOrder updateEntity = new ElectricianWorkOrder();
            updateEntity.setOrderId(startWorkReqData.getOrderId());
            updateEntity.setElectricianId(startWorkReqData.getUid());
            updateEntity.setWorkOrderId(electricianWorkOrder.getWorkOrderId());
            updateEntity.setStatus(ElectricianWorkOrderStatus.EXECUTING.getValue());
            updateEntity.setBeginTime(null == beginTime ? new Date() : beginTime);

            // 开始工作
            electricianWorkOrderService.updateEntityByCondition(null, null, updateEntity, leader);

            // 消息推送
            Map<String, String> params = new HashMap<>();
            params.put("name", workOrder.getName());
            params.put("user_name", "您");

            if (leader) {
                //2)客户->sms+push
                EngineeringProject project = engineeringProjectService.getById(workOrder.getEngineeringProjectId());
                if (project != null) {
                    GetUserInfoResult dispatchPersonResult = userInfoService.getUserInfo(project.getCustomerId());
                    if (dispatchPersonResult.isSuccess()) {
                        UserInfo dispatchPerson = dispatchPersonResult.getMemberInfo();
                        messageInfoService.sendSmsAndPushMessage(dispatchPerson.getUid(), dispatchPerson.getMobile(), MessageId.ORDER_START, params);
                    }
                }

                //3)工单关联的电工群发短信及消息推送
                List<Map<String, Object>> listElectricianInfo = electricianWorkOrderService.getAllElectricianInfo(userInfo.getUid(), workOrder.getId());
                if (CollectionUtils.isNotEmpty(listElectricianInfo)) {
                    for (Map<String, Object> electricianInfo : listElectricianInfo) {
                        if (electricianInfo.get("uid") != null && electricianInfo.get("contactTel") != null) {
                            messageInfoService.sendSmsAndPushMessage((Long) electricianInfo.get("uid"), electricianInfo.get("contactTel").toString(), MessageId.ORDER_START, params);
                        }
                    }
                }
            } else {
                // 电工姓名
                Electrician electrician = electricianService.getByUid(userInfo.getUid());
                if (electrician != null) {
                    params.put("user_name", String.format("【%s】", electrician.getUserName()));
                }

                //2)工单负责人->sms+push
                Map<String, Object> map = electricianWorkOrderService.getProjectLeader(electricianWorkOrder.getWorkOrderId());
                if (MapUtils.isNotEmpty(map)) {
                    if (map.get("uid") != null && map.get("contactTel") != null) {
                        messageInfoService.sendSmsAndPushMessage((Long) map.get("uid"), map.get("contactTel").toString(), MessageId.ORDER_START, params);
                    }
                }
            }

            //1)自己->push
            params.put("user_name", "您");
            MessageInfo smsMsg = new MessageInfo();
            smsMsg.setMsgId(MessageId.ORDER_START.getValue());
            smsMsg.setUid(userInfo.getUid());
            smsMsg.setPushTime(new Date());
            smsMsg.setParams(params);
            messageInfoService.sendPushMessage(smsMsg);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("nearbyBuss:{}", e);
        }

        return result;
    }

    /**
     * 结束工作
     *
     * @param endWorkReqData
     * @return
     */
    @Override
    public EndWorkResult endWork(EndWorkReqData endWorkReqData) {
        // 是否是工单负责人(默认否)
        Boolean leader = false;
        EndWorkResult result = new EndWorkResult();

        try {
            UserInfo userInfo = new UserInfo();
            GetUserInfoResult userInfoResult = userInfoService.getUserInfo(endWorkReqData.getUid());
            if (userInfoResult.getResultCode() == ResultCode.SUCCESS) {
                userInfo = userInfoResult.getMemberInfo();
            }
            if (!userInfo.getIsElectrician().equals(Constants.TAG_YES)) {
                result.setResultCode(ResultCode.ERROR_407);
                result.setResultMessage("没有电工的权限");
                return result;
            }

            if (StringUtils.isBlank(endWorkReqData.getOrderId())) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("orderId不能为空");
                return result;
            }

            ElectricianWorkOrder electricianWorkOrder = electricianWorkOrderService.getEntityByOrderId(endWorkReqData.getUid(), endWorkReqData.getOrderId());
            if (electricianWorkOrder == null) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("订单不存在");
                return result;
            }

            // 获取工单信息
            WorkOrder workOrder = workOrderService.getEntityById(electricianWorkOrder.getWorkOrderId());
            if (workOrder == null) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("工单不存在");
                return result;
            }

            if (electricianWorkOrder.getStatus() >= ElectricianWorkOrderStatus.FINISHED.getValue()) {
                result.setResultCode(ResultCode.ERROR_403);
                result.setResultMessage("订单已结束工作，无须重复操作");
                return result;
            }

            // 是否是工单负责人
            if (Constants.TAG_YES == electricianWorkOrder.getWorkOrderLeader()
                    && endWorkReqData.getUid().equals(electricianWorkOrder.getElectricianId())) {
                leader = true;

                if (StringUtils.isBlank(endWorkReqData.getEndTime())) {
                    result.setResultCode(ResultCode.ERROR_405);
                    result.setResultMessage("endTime不能为空(负责人)");
                    return result;
                }
            }

            Date finishTime = null;
            if (StringUtils.isNoneBlank(endWorkReqData.getEndTime())) {
                finishTime = DateUtils.parse(endWorkReqData.getEndTime(), DateUtils.DATE_TIME_PATTERN);
                if (finishTime == null) {
                    result.setResultCode(ResultCode.ERROR_406);
                    result.setResultMessage("endTime格式错误");
                    return result;
                }
            }

            ElectricianWorkOrder updateEntity = new ElectricianWorkOrder();
            updateEntity.setOrderId(endWorkReqData.getOrderId());
            updateEntity.setElectricianId(endWorkReqData.getUid());
            updateEntity.setStatus(ElectricianWorkOrderStatus.FINISHED.getValue());
            updateEntity.setFinishTime(null == finishTime ? new Date() : finishTime);
            updateEntity.setWorkOrderId(electricianWorkOrder.getWorkOrderId());

            // 特殊处理：电工工单备注字段当工单类型使用
            electricianWorkOrder.setMemo(String.valueOf(workOrder.getType()));

            // 结束工作
            electricianWorkOrderService.updateEntityByCondition(electricianWorkOrder, userInfo, updateEntity, leader);

            // 发送推送消息
            sendMessagePush(workOrder, electricianWorkOrder, userInfo, leader);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("nearbyBuss:{}", e);
        }
        return result;
    }

    /**
     * 发送推送消息
     *
     * @param workOrder
     * @param electricianWorkOrder
     * @param userInfo
     * @param leader
     */
    private void sendMessagePush(WorkOrder workOrder, ElectricianWorkOrder electricianWorkOrder, UserInfo userInfo, Boolean leader) {
        Map<String, String> params = new HashMap<>();
        params.put("name", workOrder.getName());
        params.put("user_name", "您");

        if (leader) {
            //2)客户->sms+push
            EngineeringProject project = engineeringProjectService.getById(workOrder.getEngineeringProjectId());
            if (project != null) {
                GetUserInfoResult dispatchPersonResult = userInfoService.getUserInfo(project.getCustomerId());
                if (dispatchPersonResult.isSuccess()) {
                    UserInfo dispatchPerson = dispatchPersonResult.getMemberInfo();
                    messageInfoService.sendSmsAndPushMessage(dispatchPerson.getUid(), dispatchPerson.getMobile(), MessageId.ORDER_FINISH, params);
                }
            }

            //3)工单关联的电工群发短信及消息推送
            List<Map<String, Object>> listElectricianInfo = electricianWorkOrderService.getAllElectricianInfo(userInfo.getUid(), workOrder.getId());
            if (CollectionUtils.isNotEmpty(listElectricianInfo)) {
                for (Map<String, Object> electricianInfo : listElectricianInfo) {
                    if (electricianInfo.get("uid") != null && electricianInfo.get("contactTel") != null) {
                        messageInfoService.sendSmsAndPushMessage((Long) electricianInfo.get("uid"), electricianInfo.get("contactTel").toString(), MessageId.ORDER_FINISH, params);
                    }
                }
            }
        } else {
            // 电工姓名
            String userName = userInfo.getLoginName();
            Electrician electrician = electricianService.getByUid(userInfo.getUid());
            if (electrician != null && StringUtils.isNotBlank(electrician.getUserName())) {
                userName = electrician.getUserName();
            }
            params.put("user_name", userName);

            //2)工单负责人->sms+push
            Map<String, Object> map = electricianWorkOrderService.getProjectLeader(electricianWorkOrder.getWorkOrderId());
            if (MapUtils.isNotEmpty(map)) {
                if (map.get("uid") != null && map.get("contactTel") != null) {
                    messageInfoService.sendSmsAndPushMessage((Long) map.get("uid"), map.get("contactTel").toString(), MessageId.ORDER_FINISH, params);
                }
            }
        }

        //1)自己->push
        params.put("user_name", "您");
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setMsgId(MessageId.ORDER_FINISH.getValue());
        messageInfo.setUid(userInfo.getUid());
        messageInfo.setPushTime(new Date());
        messageInfo.setParams(params);
        messageInfoService.sendPushMessage(messageInfo);
    }

    /**
     * 工单新增工作记录
     *
     * @param addWorkLogReqData
     * @return
     */
    @Override
    public AddWorkLogResult addWorkLog(AddWorkLogReqData addWorkLogReqData) {
        AddWorkLogResult result = new AddWorkLogResult();

        try {
            UserInfo userInfo = new UserInfo();
            GetUserInfoResult userInfoResult = userInfoService.getUserInfo(addWorkLogReqData.getUid());
            if (userInfoResult.getResultCode() == ResultCode.SUCCESS) {
                userInfo = userInfoResult.getMemberInfo();
            }
            if (!userInfo.getIsElectrician().equals(Constants.TAG_YES)) {
                result.setResultCode(ResultCode.ERROR_405);
                result.setResultMessage("没有电工的权限");
                return result;
            }

            if (StringUtils.isBlank(addWorkLogReqData.getOrderId())) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("orderId不能为空");
                return result;
            }

            if (StringUtils.isBlank(addWorkLogReqData.getText())) {
                result.setResultCode(ResultCode.ERROR_403);
                result.setResultMessage("text不能为空");
                return result;
            }

            ElectricianWorkOrder electricianWorkOrder = electricianWorkOrderService.getEntityByOrderId(addWorkLogReqData.getUid(), addWorkLogReqData.getOrderId());
            if (electricianWorkOrder == null) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("订单不存在");
                return result;
            }

            if (!ElectricianWorkOrderStatus.EXECUTING.getValue().equals(electricianWorkOrder.getStatus())) {
                result.setResultCode(ResultCode.ERROR_406);
                result.setResultMessage("工作记录不可添加");
                return result;
            }

            if (addWorkLogReqData.getText().length() > 1000) {
                result.setResultCode(ResultCode.ERROR_404);
                result.setResultMessage("text长度限制(1000个汉字)");
                return result;
            }

            ElectricianWorkLog workLog = new ElectricianWorkLog();
            workLog.setElectricianWorkOrderId(electricianWorkOrder.getId());
            workLog.setWorkOrderId(electricianWorkOrder.getWorkOrderId());
            workLog.setElectricianId(addWorkLogReqData.getUid());
            workLog.setContent(addWorkLogReqData.getText());
            workLog.setCompanyId(electricianWorkOrder.getCompanyId());
            workLog.setDeleted(Constants.TAG_NO);

            Date now = new Date();
            List<ElectricianWorkLogAttachment> listAttachment = new ArrayList<ElectricianWorkLogAttachment>();
            if (CollectionUtils.isNotEmpty(addWorkLogReqData.getFiles())) {
                for (FileInfo file : addWorkLogReqData.getFiles()) {
                    ElectricianWorkLogAttachment attachment = new ElectricianWorkLogAttachment();
                    attachment.setType(file.getType());
                    attachment.setFid(file.getFileId());
                    attachment.setCompanyId(workLog.getCompanyId());
                    attachment.setDeleted(Constants.TAG_NO);
                    attachment.setCreateTime(now);
                    attachment.setCreateUser(workLog.getCreateUser());
                    listAttachment.add(attachment);
                }
            }

            // 保存日志及附件信息
            electricianWorkLogService.saveEntity(workLog, listAttachment);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("nearbyBuss:{}", e);
        }

        return result;
    }

    /**
     * 工单删除工作记录
     *
     * @param delWorkLogReqData
     * @return
     */
    @Override
    public DelWorkLogResult delWorkLog(DelWorkLogReqData delWorkLogReqData) {
        DelWorkLogResult result = new DelWorkLogResult();
        try {
            UserInfo userInfo = new UserInfo();
            GetUserInfoResult userInfoResult = userInfoService.getUserInfo(delWorkLogReqData.getUid());
            if (userInfoResult.getResultCode() == ResultCode.SUCCESS) {
                userInfo = userInfoResult.getMemberInfo();
            }
            if (!userInfo.getIsElectrician().equals(Constants.TAG_YES)) {
                result.setResultCode(ResultCode.ERROR_406);
                result.setResultMessage("没有电工的权限");
                return result;
            }

            if (StringUtils.isBlank(delWorkLogReqData.getOrderId())) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("orderId不能为空");
                return result;
            }

            if (delWorkLogReqData.getWorkLogId() == null) {
                result.setResultCode(ResultCode.ERROR_403);
                result.setResultMessage("workLogId不能为空");
                return result;
            }

            ElectricianWorkOrder electricianWorkOrder = electricianWorkOrderService.getEntityByOrderId(delWorkLogReqData.getUid(), delWorkLogReqData.getOrderId());
            if (electricianWorkOrder == null) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("订单不存在");
                return result;
            }

            if (!ElectricianWorkOrderStatus.EXECUTING.getValue().equals(electricianWorkOrder.getStatus())) {
                result.setResultCode(ResultCode.ERROR_406);
                result.setResultMessage("工作记录不可删除");
                return result;
            }

            ElectricianWorkLog electricianWorkLog = electricianWorkLogService.getEntityById(delWorkLogReqData.getWorkLogId());
            if (electricianWorkLog == null) {
                result.setResultCode(ResultCode.ERROR_405);
                result.setResultMessage("工作记录不存在");
                return result;
            }
            if (!delWorkLogReqData.getUid().equals(electricianWorkLog.getElectricianId())
                    || !electricianWorkOrder.getWorkOrderId().equals(electricianWorkLog.getWorkOrderId())) {
                result.setResultCode(ResultCode.ERROR_405);
                result.setResultMessage("工作记录不存在");
                return result;
            }

            electricianWorkLogService.deleteEntity(electricianWorkLog);

        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("nearbyBuss:{}", e);
        }

        return result;
    }

    /**
     * 删除工作记录附件ID
     *
     * @param attachmentReqData
     * @return
     */
    @Override
    public DelWorkLogAttachmentResult delWorkLogAttachment(DelWorkLogAttachmentReqData attachmentReqData) {
        DelWorkLogAttachmentResult result = new DelWorkLogAttachmentResult();
        try {
            UserInfo userInfo = new UserInfo();
            GetUserInfoResult userInfoResult = userInfoService.getUserInfo(attachmentReqData.getUid());
            if (userInfoResult.getResultCode() == ResultCode.SUCCESS) {
                userInfo = userInfoResult.getMemberInfo();
            }
            if (!userInfo.getIsElectrician().equals(Constants.TAG_YES)) {
                result.setResultCode(ResultCode.ERROR_406);
                result.setResultMessage("没有电工的权限");
                return result;
            }

            if (StringUtils.isBlank(attachmentReqData.getOrderId())) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("orderId不能为空");
                return result;
            }

            if (attachmentReqData.getWorkLogAttachmentId() == null) {
                result.setResultCode(ResultCode.ERROR_403);
                result.setResultMessage("workLogAttachmentId不能为空");
                return result;
            }

            ElectricianWorkOrder electricianWorkOrder = electricianWorkOrderService.getEntityByOrderId(attachmentReqData.getUid(), attachmentReqData.getOrderId());
            if (electricianWorkOrder == null) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("订单不存在");
                return result;
            }

            if (!ElectricianWorkOrderStatus.EXECUTING.getValue().equals(electricianWorkOrder.getStatus())) {
                result.setResultCode(ResultCode.ERROR_406);
                result.setResultMessage("工作记录附件不可删除");
                return result;
            }

            ElectricianWorkLogAttachment workLogAttachment = electricianWorkLogAttachmentService.getEntityById(attachmentReqData.getWorkLogAttachmentId());
            if (workLogAttachment == null) {
                result.setResultCode(ResultCode.ERROR_405);
                result.setResultMessage("工作记录附件不存在");
                return result;
            }

            electricianWorkLogAttachmentService.delete(workLogAttachment);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("nearbyBuss:{}", e);
        }
        return result;
    }

    /**
     * 工单工作记录列表
     *
     * @param listWorkLogReqData
     * @return
     */
    @Override
    public ListWorkLogResult listWorkLog(ListWorkLogReqData listWorkLogReqData) {
        ListWorkLogResult result = new ListWorkLogResult();

        try {
            if (StringUtils.isBlank(listWorkLogReqData.getOrderId())) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("orderId不能为空");
                return result;
            }

            ElectricianWorkOrder electricianWorkOrder = electricianWorkOrderService.getEntityByOrderId(listWorkLogReqData.getUid(), listWorkLogReqData.getOrderId());
            if (electricianWorkOrder == null) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("订单不存在");
                return result;
            }

            List<WorkLogInfo> workLogInfoList = new ArrayList<>();
            ElectricianWorkLog searchEntity = new ElectricianWorkLog();
            searchEntity.setElectricianId(listWorkLogReqData.getUid());
            searchEntity.setElectricianWorkOrderId(electricianWorkOrder.getId());
            searchEntity.setWorkOrderId(electricianWorkOrder.getWorkOrderId());
            List<ElectricianWorkLog> workLogList = electricianWorkLogService.getAllEntity(searchEntity);
            if (CollectionUtils.isNotEmpty(workLogList)) {
                // 读取app.properties信息
                ResourceBundle resource = ResourceBundle.getBundle("app");
                // 附件访问地址
                String picBaseUrl = resource.getString("pic.server.baseurl");
                
                for (ElectricianWorkLog workLog : workLogList) {
                    // 获取工作记录附件信息
                    List<FileInfo> fileInfoList = new ArrayList<>();
                    List<ElectricianWorkLogAttachment> attachments = electricianWorkLogAttachmentService.getAllEntity(workLog.getId(), 0, 3);
                    if (CollectionUtils.isNotEmpty(attachments)) {
                        for (ElectricianWorkLogAttachment attachment : attachments) {
                            FileInfo fileInfo = new FileInfo();
                            fileInfo.setId(attachment.getId());
                            fileInfo.setType(attachment.getType());
                            fileInfo.setFileId(attachment.getFid());
                            fileInfoList.add(fileInfo);
                        }
                    }

                    WorkLogInfo workLogInfo = new WorkLogInfo();
                    workLogInfo.setId(workLog.getId());
                    workLogInfo.setFiles(fileInfoList);
                    workLogInfo.setContent(workLog.getContent());
                    workLogInfo.setCreateTime(DateUtils.format(workLog.getCreateTime(), DateUtils.DATE_TIME_PATTERN));

                    workLogInfoList.add(workLogInfo);
                }
            }
            result.setWorklogs(workLogInfoList);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("nearbyBuss:{}", e);
        }
        return result;
    }

    @Override
    public ElectricianListResult electricianList(
            ElectricianListReqData electricianListReqData) {
        ElectricianListResult result = new ElectricianListResult();
        try {
            Long uid = electricianListReqData.getUid();
            if (StringUtils.isBlank(electricianListReqData.getOrderId())) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("orderId不能为空");
                return result;
            }

            WorkOrder workOrder = workOrderService.getEntityByOrderId(electricianListReqData.getOrderId());
            if (workOrder == null) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("工单信息不完整");
                return result;
            }

            Map<String, Object> leaderMap = electricianWorkOrderService.getProjectLeader(workOrder.getId());
            if (MapUtils.isEmpty(leaderMap)) {
                result.setResultCode(ResultCode.ERROR_403);
                result.setResultMessage("工单信息不完整");
                return result;
            }

            UserInfo userInfo = new UserInfo();
            GetUserInfoResult userInfoResult = userInfoService.getUserInfo(uid);
            if (userInfoResult.getResultCode() == ResultCode.SUCCESS) {
                userInfo = userInfoResult.getMemberInfo();
            }
            if (!userInfo.isCompanyElectrician()) {
                result.setResultCode(ResultCode.ERROR_403);
                result.setResultMessage("不是企业电工，无权限操作");
                return result;
            }

            if (!uid.equals(Long.valueOf(leaderMap.get("uid").toString()))) {
                result.setResultCode(ResultCode.ERROR_403);
                result.setResultMessage("不是工单负责人，无权限操作");
                return result;
            }

            int statuss[] = {
                    ElectricianWorkOrderStatus.UNCONFIRMED.getValue(),
                    ElectricianWorkOrderStatus.CONFIRMED.getValue(),
                    ElectricianWorkOrderStatus.EXECUTING.getValue(),
                    ElectricianWorkOrderStatus.FINISHED.getValue(),
                    ElectricianWorkOrderStatus.FEE_CONFIRM.getValue(),
                    ElectricianWorkOrderStatus.LIQUIDATED.getValue()
            };
            List<ElectricianWorkOrderInfo> list = electricianWorkOrderService.queryElectricianList(workOrder.getId(), statuss);
            if (CollectionUtils.isNotEmpty(list)) {
                for (ElectricianWorkOrderInfo electricianWorkOrderInfo : list) {
                    Integer status = electricianWorkOrderInfo.getStatus();
                    if (status > 0) {
                        // 0未确认 1已确认，这里为业务需求特殊处理
                        electricianWorkOrderInfo.setStatus(1);
                    }
                }
            }

            result.setElectricianList(structureOrderList(list));
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("electricianList:{}", e);
        }
        return result;
    }

    /**
     * 构建工单人员信息
     *
     * @param list
     * @return
     */
    private List<Map<String, Object>> structureOrderList(List<ElectricianWorkOrderInfo> list) {
        List<Map<String, Object>> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            Map<Long, List<ElectricianWorkOrderInfo>> groupByUid = new HashMap<>();
            Set<Long> socialWorkOrderIds = new HashSet<>();
            for (ElectricianWorkOrderInfo electricianWorkOrderInfo : list) {
                Long uid = electricianWorkOrderInfo.getUid();
                Integer type = electricianWorkOrderInfo.getType();
                if (type.equals(ElectricianWorkOrderType.SOCIAL_ELECTRICIAN_WD.getValue())) {
                    socialWorkOrderIds.add(electricianWorkOrderInfo.getSocialWorkOrderId());
                }
                if (groupByUid.containsKey(uid)) {
                    List<ElectricianWorkOrderInfo> newList = groupByUid.get(uid);
                    newList.add(electricianWorkOrderInfo);
                    groupByUid.put(uid, newList);
                } else {
                    List<ElectricianWorkOrderInfo> newList = new ArrayList<>();
                    newList.add(electricianWorkOrderInfo);
                    groupByUid.put(uid, newList);
                }
            }

            Map<Long, String> mapIdToContent = new HashMap<>();
            if (socialWorkOrderIds.size() > 0) {
                List<Map<String, Object>> mapList = socialWorkOrderService.getMapIdToContentByIds(socialWorkOrderIds.toArray(new Long[]{}));
                if (CollectionUtils.isNotEmpty(mapList)) {
                    for (Map<String, Object> map : mapList) {
                        mapIdToContent.put(Long.valueOf(map.get("id").toString()), map.get("title").toString());
                    }
                }
            }

            for (Map.Entry<Long, List<ElectricianWorkOrderInfo>> entry : groupByUid.entrySet()) {
                Map<String, Object> map = new HashMap<>();
                map.put("uid", entry.getKey());//电工ID
                List<ElectricianWorkOrderInfo> electricianWorkOrderInfoList = entry.getValue();
                List<Map<String, Object>> orderInfoList = new ArrayList<>();
                for (ElectricianWorkOrderInfo e : electricianWorkOrderInfoList) {
                    map.put("name", e.getName());//电工名字
                    map.put("mobile", e.getMobile());//电工手机号码
                    map.put("type", e.getType());//1企业电工，2社会电工
                    Map<String, Object> orderInfoMap = new HashMap<>();

                    orderInfoMap.put("status", e.getStatus());
                    String title = e.getCheckOption();
                    orderInfoMap.put("title", e.getCheckOption());
                    if (e.getType().equals(ElectricianWorkOrderType.SOCIAL_ELECTRICIAN_WD.getValue())) {
                        title = "";
                        Long socialWorkOrderId = e.getSocialWorkOrderId();
                        if (mapIdToContent.containsKey(socialWorkOrderId)) {
                            title = mapIdToContent.get(socialWorkOrderId);
                        }
                    }
                    orderInfoMap.put("title", title);
                    orderInfoList.add(orderInfoMap);
                }
                map.put("orderInfo", orderInfoList);
                result.add(map);
            }
        }
        return result;
    }

    /**
     * 工单结算(社会电工工单)
     *
     * @param settlementReqData
     * @return
     */
    @Override
    public SettlementResult settlementWorkOrder(SettlementReqData settlementReqData) {
        SettlementResult result = new SettlementResult();
        try {
            if (StringUtils.isBlank(settlementReqData.getOrderId())) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("orderId不能为空");
                return result;
            }

            ElectricianWorkOrder electricianWorkOrder = electricianWorkOrderService.getEntityByOrderId(settlementReqData.getUid(), settlementReqData.getOrderId());
            if (electricianWorkOrder == null) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("订单不存在");
                return result;
            }
            // 必须是社会电工工单类型且社会电工订单ID不能为0
            if (!electricianWorkOrder.getType().equals(ElectricianType.SOCIETY.getValue()) ||
                    electricianWorkOrder.getSocialWorkOrderId().equals(0L)) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("订单不存在");
                return result;
            }
            // 工单结算支付状态(0:结算未支付,1:结算已支付)
            if (electricianWorkOrder.getSettlementPayStatus().equals(SettlementPayStatus.UNPAY.getValue())) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("工单结算支付等待中 ");
                return result;
            }

            UserInfo userInfo = new UserInfo();
            GetUserInfoResult userInfoResult = userInfoService.getUserInfo(settlementReqData.getUid());
            if (userInfoResult.getResultCode() == ResultCode.SUCCESS) {
                userInfo = userInfoResult.getMemberInfo();
            }
            if (!userInfo.getIsElectrician().equals(Constants.TAG_YES)) {
                result.setResultCode(ResultCode.ERROR_403);
                result.setResultMessage("没有电工的权限");
                return result;
            }
            if (!userInfo.isSocialElectrician()) {
                result.setResultCode(ResultCode.ERROR_403);
                result.setResultMessage("没有社会电工的权限");
                return result;
            }

            Double amount = 0D;
            if (StringUtils.isBlank(settlementReqData.getAmount())) {
                result.setResultCode(ResultCode.ERROR_405);
                result.setResultMessage("支付金额格式不正确");
                return result;
            }
            try {
                amount = Double.valueOf(settlementReqData.getAmount());
            } catch (NumberFormatException e) {
                result.setResultCode(ResultCode.ERROR_405);
                result.setResultMessage("支付金额格式不正确");
                return result;
            }

            SocialWorkOrder socialWorkOrder = socialWorkOrderService.getEntityById(electricianWorkOrder.getSocialWorkOrderId());
            if (socialWorkOrder == null) {
                result.setResultCode(ResultCode.ERROR_406);
                result.setResultMessage("社会工单不存在");
                return result;
            }

            Company company = companyService.getById(electricianWorkOrder.getCompanyId());
            if (company == null) {
                result.setResultCode(ResultCode.ERROR_407);
                result.setResultMessage("服务商不存在");
                return result;
            }

            // 接口传入金额与数据库计算实际金额比较，不一致提示用户
            Double fee = socialWorkOrder.getFee() == null ? 0D : socialWorkOrder.getFee();
            Double actualWorkTime = electricianWorkOrder.getActualWorkTime() == null ? 0D : electricianWorkOrder.getActualWorkTime();
            BigDecimal totalAmount = BigDecimalUtil.mul(actualWorkTime, fee);
            if (totalAmount.doubleValue() != amount) {
                result.setResultCode(ResultCode.ERROR_408);
                result.setResultMessage("支付金额有变动");
                return result;
            }

            // 服务商会员资金变动明细表
            UserWalletDetail companyWallet = new UserWalletDetail();
            companyWallet.setUid(company.getMemberId());
            companyWallet.setType(WalletType.WITHHOLD.getValue());
            companyWallet.setDealType(WalletDealType.COSTS.getValue());
            companyWallet.setOrderId(socialWorkOrder.getOrderId());
            
            companyWallet.setFundTarget(new Long(WalleFundType.PLATFORM.getValue()));//平台
            companyWallet.setFundSource(new Long(WalleFundType.WALLE_FROZEN.getValue()));//冻结余额
            companyWallet.setAmount(totalAmount.doubleValue());
            companyWallet.setDeleted(Constants.TAG_NO);

            // 社会电工会员资金变动明细表
            UserWalletDetail socialElectricianWallet = new UserWalletDetail();
            socialElectricianWallet.setUid(electricianWorkOrder.getElectricianId());
            socialElectricianWallet.setType(WalletType.INCOME.getValue());
            socialElectricianWallet.setDealType(WalletDealType.INCOME.getValue());
            socialElectricianWallet.setOrderId(socialWorkOrder.getOrderId());
            socialElectricianWallet.setFundTarget(new Long(WalleFundType.WALLE_BALANCE.getValue()));//余额
            socialElectricianWallet.setFundSource(new Long(WalleFundType.PLATFORM.getValue()));//平台
            socialElectricianWallet.setAmount(totalAmount.doubleValue());
            socialElectricianWallet.setDeleted(Constants.TAG_NO);

            // 修改工单状态，扣除服务商冻结余额，增加社会电工余额
            electricianWorkOrderService.settlementWorkOrder(electricianWorkOrder, companyWallet, socialElectricianWallet, company, totalAmount, userInfo);

            // 消息推送
            WorkOrder workOrder = workOrderService.getEntityById(electricianWorkOrder.getWorkOrderId());
            if (workOrder != null) {
                String userName = userInfo.getLoginName();
                Electrician electrician = electricianService.getByUid(userInfo.getUid());
                if (electrician!= null) {
                    userName = electrician.getUserName();
                }
                Map<String, String> params = new HashMap<>();
                params.put("name", workOrder.getName());
                params.put("user_name", userName);
                params.put("amount", String.valueOf(totalAmount.doubleValue()));

                //2)派单人->sms+push
                GetUserInfoResult dispatchPersonResult = userInfoService.getUserInfo(workOrder.getMemberId());
                if (dispatchPersonResult.isSuccess()) {
                    UserInfo dispatchPerson = dispatchPersonResult.getMemberInfo();
                    messageInfoService.sendSmsAndPushMessage(dispatchPerson.getUid(), dispatchPerson.getMobile(), MessageId.ORDER_FINISH, params);
                }

                //1)自己->push
                MessageInfo messageInfo = new MessageInfo();
                messageInfo.setMsgId(MessageId.SOCIAL_ORDER_SETTLEMENT.getValue());
                messageInfo.setUid(userInfo.getUid());
                messageInfo.setPushTime(new Date());
                messageInfo.setParams(params);
                messageInfoService.sendPushMessage(messageInfo);
            }
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("electricianList:{}", e);
        }
        return result;
    }

}
