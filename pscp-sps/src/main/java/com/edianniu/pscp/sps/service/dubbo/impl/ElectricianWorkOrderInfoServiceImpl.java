/**
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月11日 下午6:27:33
 * @version V1.0
 */
package com.edianniu.pscp.sps.service.dubbo.impl;

import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.mis.bean.GetUserInfoResult;
import com.edianniu.pscp.mis.bean.defectrecord.vo.DefectRecordVO;
import com.edianniu.pscp.mis.bean.electricianworkorder.ElectricianWorkOrderType;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.bean.workorder.WorkOrderType;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.mis.service.dubbo.WorkOrderDefectRecordInfoService;
import com.edianniu.pscp.sps.bean.electrician.ElectricianType;
import com.edianniu.pscp.sps.bean.evaluate.electrician.VO.EvaluateAttachmentVO;
import com.edianniu.pscp.sps.bean.evaluate.electrician.VO.EvaluateVO;
import com.edianniu.pscp.sps.bean.project.ProjectInfo;
import com.edianniu.pscp.sps.bean.workorder.chieforder.WorkOrderStatus;
import com.edianniu.pscp.sps.bean.workorder.chieforder.vo.CompanyVO;
import com.edianniu.pscp.sps.bean.workorder.electrician.*;
import com.edianniu.pscp.sps.bean.workorder.worklog.ElectricianWorkLogInfo;
import com.edianniu.pscp.sps.commons.Constants;
import com.edianniu.pscp.sps.commons.PageResult;
import com.edianniu.pscp.sps.commons.ResultCode;
import com.edianniu.pscp.sps.domain.*;
import com.edianniu.pscp.sps.service.*;
import com.edianniu.pscp.sps.service.dubbo.ElectricianWorkOrderInfoService;
import com.edianniu.pscp.sps.service.dubbo.SpsCompanyCustomerInfoService;
import com.edianniu.pscp.sps.util.DateUtil;
import com.edianniu.pscp.sps.util.MoneyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yanlin.chen
 * @version V1.0
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月11日 下午6:27:33
 */
@Service
@Repository("electricianWorkOrderInfoService")
public class ElectricianWorkOrderInfoServiceImpl implements
        ElectricianWorkOrderInfoService {
    private static final Logger logger = LoggerFactory
            .getLogger(ElectricianWorkOrderInfoServiceImpl.class);
    @Autowired
    private ElectricianWorkOrderService electricianWorkOrderService;
    @Autowired
    private WorkOrderService workOrderService;
    @Autowired
    private SocialWorkOrderService socialWorkOrderService;
    @Autowired
    private ElectricianEvaluateService electricianEvaluateService;
    @Autowired
    private ElectricianEvaluateAttachmentService electricianEvaluateAttachmentService;
    @Autowired
    private SpsCompanyService spsCompanyService;
    @Autowired
    private EngineeringProjectService engineeringProjectService;
    @Autowired
    private SpsCompanyCustomerInfoService companyCustomerService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ElectricianWorkLogService electricianWorkLogService;
    @Autowired
    private WorkOrderDefectRecordInfoService workOrderDefectRecordInfoService;

    @Override
    public ListResult list(ListReqData listReqData) {
        ListResult result = new ListResult();
        try {

            GetUserInfoResult userInfoResult = userInfoService.getUserInfo(listReqData.getUid());
            if (!userInfoResult.isSuccess()) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("用户信息不存在");
                return result;
            }

            UserInfo userInfo = userInfoResult.getMemberInfo();

            Company company = spsCompanyService.getCompanyById(userInfo.getCompanyId());
            if (company == null || !company.getStatus().equals(2)) {
                result.setElectricianWorkOrders(new ArrayList<ElectricianWorkOrderInfo>());
                result.setHasNext(false);
                result.setNextOffset(0);
                result.setTotalCount(0);
                return result;
            }

            ListQuery listQuery = new ListQuery();
            BeanUtils.copyProperties(listReqData, listQuery);
            listQuery.setCompanyId(userInfo.getCompanyId());

            if (StringUtils.isNotBlank(listReqData.getStatus())) {
                if (!ElectricianWorkOrderRequestType.isExist(listReqData.getStatus().trim())) {
                    result.setResultCode(ResultCode.ERROR_202);
                    result.setResultMessage("status 只支持:" + ElectricianWorkOrderRequestType.getValues());
                    return result;
                }
                ElectricianWorkOrderRequestType requestStatus = ElectricianWorkOrderRequestType.get(listReqData.getStatus().trim());
                if (requestStatus == null) {
                    result.setResultCode(ResultCode.ERROR_202);
                    result.setResultMessage("status 只支持:" + ElectricianWorkOrderRequestType.getValues());
                    return result;
                }
                
                // 待结算 显示社会电工工单的 3 4
                if (requestStatus.equals(ElectricianWorkOrderRequestType.STARTED)) {
					listQuery.setElectricianType(ElectricianType.SOCIETY.getValue());
				}
                // 已完成 显示社会电工工单的-2 -1 5 和企业电工工单的-2 -1 3
                if (requestStatus.equals(ElectricianWorkOrderRequestType.COMPLETED)) {
                	// 社会电工工单
					listQuery.setElectricianType(ElectricianType.SOCIETY.getValue());
					// 企业电工工单
                	listQuery.setElectricianType2(ElectricianType.COMPANY.getValue());
                	Integer[] statusArray2 = requestStatus.getStatus(true);
                	listQuery.setStatuss2(statusArray2);
				}
                
                Integer[] statusArray = requestStatus.getStatus(false);
                listQuery.setStatuss(statusArray);
            }

            PageResult<ElectricianWorkOrderInfo> pageResult = electricianWorkOrderService.query(listQuery);
            result.setElectricianWorkOrders(pageResult.getData());
            result.setHasNext(pageResult.isHasNext());
            result.setNextOffset(pageResult.getNextOffset());
            result.setTotalCount(pageResult.getTotal());
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("Electrician WorkOrder list:{}", e);
        }

        return result;
    }

    @Override
    public DetailResult detail(DetailReqData detailReqData) {
        DetailResult result = new DetailResult();
        try {
            // 获取电工工单信息
            ElectricianWorkOrder electricianWorkOrder = electricianWorkOrderService.getByOrderId(detailReqData.getOrderId());
            if (electricianWorkOrder != null) {
                // 工单ID
                Long workOrderId = electricianWorkOrder.getWorkOrderId();
                // 工单信息
                WorkOrder workOrder = workOrderService.getById(workOrderId);

                // 订单详情
                OrderDetailInfo orderDetail = new OrderDetailInfo();
                orderDetail.setId(electricianWorkOrder.getId());
                orderDetail.setStatus(electricianWorkOrder.getStatus());
                orderDetail.setOrderId(electricianWorkOrder.getOrderId());

                orderDetail.setCheckOption(structureCheckOptionInfo(workOrder.getId()));

                orderDetail.setName(workOrder.getName());
                orderDetail.setContent(workOrder.getContent());
                orderDetail.setAddress(workOrder.getAddress());
                orderDetail.setLatitude(workOrder.getLatitude() == null ? "0" : workOrder.getLatitude().toString());
                orderDetail.setLongitude(workOrder.getLongitude() == null ? "0" : workOrder.getLongitude().toString());
                orderDetail.setPublishTime(DateUtil.getFormatDate(workOrder.getCreateTime(), DateUtil.YYYY_MM_DD_FORMAT));

                String startDate = DateUtil.getFormatDate(workOrder.getStartTime(), DateUtil.YYYY_MM_DD_FORMAT);
                String endDate = DateUtil.getFormatDate(workOrder.getEndTime(), DateUtil.YYYY_MM_DD_FORMAT);
                orderDetail.setWorkTime(String.format("%s~%s", startDate, endDate));// 格式：2017-04-01~2017-04-15

                // jsonArray数据结构：[{id:10001,name:'xxx',checked:1}，{id:1002,name:'xxx',checked:0},{id:-1,name:'自定义',checked:0}]
                // 危险有害因数
                orderDetail.setHazardFactor(workOrder.getHazardFactorIdentifications());
                if (StringUtils.isNotBlank(workOrder.getHazardFactorIdentifications())) {
                    orderDetail.setHazardFactorOther(workOrder.getIdentificationOther());
                }
                // 安全措施
                orderDetail.setSafetyMeasures(workOrder.getSafetyMeasures());
                if (StringUtils.isNotBlank(workOrder.getSafetyMeasuresOther())) {
                    orderDetail.setSafetyMeasuresOther(workOrder.getSafetyMeasuresOther());
                }
                // 携带机械或设备
                orderDetail.setCarryingTools(workOrder.getCarryingTools());

                // 获取工单项目负责人信息
                Map<String, Object> mapProjectLeader = electricianWorkOrderService.getWorkOrderLeader(workOrderId);
                if (MapUtils.isEmpty(mapProjectLeader)) {
                    mapProjectLeader = new HashMap<String, Object>();
                }
                String projectLeader = mapProjectLeader.get("name") == null ? "" : mapProjectLeader.get("name").toString();
                orderDetail.setProjectLeader(projectLeader);// 项目负责人
                String contactTel = mapProjectLeader.get("contactTel") == null ? "" : mapProjectLeader.get("contactTel").toString();
                orderDetail.setContactTel(contactTel);// 项目负责人联系电话
                // 工单类型名称
                orderDetail.setType(workOrder.getType());
                orderDetail.setTypeName(WorkOrderType.getDesc(workOrder.getType()));

                // 扩展信息
                Map<String, Object> extendInfoMap = new HashMap<>();
                // 工单负责人标识
                extendInfoMap.put("isLeader", Constants.TAG_NO);
                Long leaderUid = mapProjectLeader.get("uid") == null ? 0L : (Long) mapProjectLeader.get("uid");
                if (leaderUid.equals(electricianWorkOrder.getElectricianId())) {
                    extendInfoMap.put("isLeader", Constants.TAG_YES);// 是工单负责人
                }
                // 工单负责人开始工作标识
                extendInfoMap.put("isLeaderStart", Constants.TAG_NO);
                if (workOrder.getStatus().equals(WorkOrderStatus.EXECUTING.getValue())) {
                    extendInfoMap.put("isLeaderStart", Constants.TAG_YES);
                }
                orderDetail.setExtendInfo(JSON.toJSONString(extendInfoMap));

                orderDetail.setFee("");// 费用(元/天)
                Double fee = 0D;// 电工费用
                if (electricianWorkOrder.getType().equals(ElectricianType.SOCIETY.getValue())
                        && !electricianWorkOrder.getSocialWorkOrderId().equals(0L)) {
                    // 社会电工费用
                    fee = socialWorkOrderService.getSocialElectricianFee(electricianWorkOrder.getSocialWorkOrderId());
                    orderDetail.setFee(MoneyUtils.format(fee));// 费用(元/天)
                }
                result.setOrderDetail(orderDetail);

                // 服务商信息
                CompanyVO companyInfo = new CompanyVO();
                Company serviceCompany = spsCompanyService.getCompanyById(workOrder.getCompanyId());
                if (serviceCompany == null) {
                    serviceCompany = new Company();
                }
                companyInfo.setId(serviceCompany.getId());
                companyInfo.setName(serviceCompany.getName());
                companyInfo.setContacts(serviceCompany.getContacts());
                companyInfo.setContactNumber(serviceCompany.getContactTel());
                companyInfo.setAddress(serviceCompany.getAddress());
                result.setCompanyInfo(companyInfo);

                // 客户信息
                CompanyVO customerInfo = new CompanyVO();
                // 获取项目信息
                ProjectInfo project = engineeringProjectService.getById(workOrder.getEngineeringProjectId());
                if (project != null) {
                    orderDetail.setProjectId(project.getId());
                    orderDetail.setProjectName(project.getName());
                    orderDetail.setCustomerId(project.getCustomerId());
                    CompanyCustomer companyCustomer = companyCustomerService.getByCustomerId(project.getCustomerId());
                    if (companyCustomer != null) {
                        orderDetail.setCustomerName(companyCustomer.getCpName());
                        customerInfo.setId(companyCustomer.getId());
                        customerInfo.setName(companyCustomer.getCpName());
                        customerInfo.setContacts(companyCustomer.getCpContact());
                        customerInfo.setContactNumber(companyCustomer.getCpContactMobile());
                        customerInfo.setAddress(companyCustomer.getCpAddress());
                    }


                }
                result.setCustomerInfo(customerInfo);

                // 结算信息
                SettlementInfo settlementInfo = new SettlementInfo();
                // 费用确认、结算后获取结算信息
                if (electricianWorkOrder.getType().equals(ElectricianType.SOCIETY.getValue()) &&
                        electricianWorkOrder.getStatus() >= ElectricianWorkOrderStatus.FEE_CONFIRM.getValue()) {
                    settlementInfo.setWorkTime(String.valueOf(electricianWorkOrder.getActualWorkTime()));
                    settlementInfo.setAmount(String.valueOf(electricianWorkOrder.getActualFee()));
                }
                result.setSettlementInfo(settlementInfo);

                //评价信息
                EvaluateVO evaluateInfo = new EvaluateVO();
                // 结算后获取电工评价信息
                if (electricianWorkOrder.getStatus().equals(ElectricianWorkOrderStatus.LIQUIDATED.getValue())) {
                    ElectricianEvaluate electricianEvaluate = electricianEvaluateService.queryEntityByElectricianWorkOrderId(electricianWorkOrder.getElectricianId(), electricianWorkOrder.getId());
                    if (electricianEvaluate != null) {
                        evaluateInfo.setEvaluateId(electricianEvaluate.getId());
                        evaluateInfo.setContent(electricianEvaluate.getContent());
                        evaluateInfo.setServiceSpeed(electricianEvaluate.getServiceSpeed());
                        evaluateInfo.setServiceQuality(electricianEvaluate.getServiceQuality());
                        // 附件信息
                        List<EvaluateAttachmentVO> attachmentVOList = new ArrayList<>();
                        List<ElectricianEvaluateAttachment> attachmentList = electricianEvaluateAttachmentService.queryListByEvaluateId(electricianEvaluate.getId());
                        if (CollectionUtils.isNotEmpty(attachmentList)) {
                            for (ElectricianEvaluateAttachment evaluateAttachment : attachmentList) {
                                EvaluateAttachmentVO evaluateAttachmentVO = new EvaluateAttachmentVO();
                                evaluateAttachmentVO.setId(evaluateAttachment.getId());
                                evaluateAttachmentVO.setFid(evaluateAttachment.getFid());
                                attachmentVOList.add(evaluateAttachmentVO);
                            }
                        }
                        evaluateInfo.setAttachment(attachmentVOList);
                    }
                }
                result.setEvaluateInfo(evaluateInfo);

                // 工作记录信息
                List<ElectricianWorkLogInfo> workLogList = electricianWorkLogService.selectWorkLogByElectricianId(electricianWorkOrder.getElectricianId(), electricianWorkOrder.getId());
                result.setWorkLogList(workLogList);

                // 修复缺陷记录
                if (StringUtils.isNotBlank(workOrder.getDefectRecords())) {
                    String[] strArray = workOrder.getDefectRecords().trim().split(",");
                    List<Long> ids = new ArrayList<>();
                    if (strArray.length > 0) {
                        for (String str : strArray) {
                            ids.add(Long.valueOf(str));
                        }
                    }

                    List<DefectRecordVO> defectRepairList = workOrderDefectRecordInfoService.getRepairDefectRecord(ids);
                    if (CollectionUtils.isNotEmpty(defectRepairList)) {
                        for (DefectRecordVO defectRecordVO : defectRepairList) {
                            if (project != null) {
                                defectRecordVO.setProjectName(project.getName());
                            }
                        }
                    }

                    result.setDefectRepairList(defectRepairList);
                }

            }
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("workOrdersDetail:{}", e);
        }
        return result;
    }

    @Override
    public void save(ElectricianWorkOrder electricianWorkOrder) {
        electricianWorkOrderService.save(electricianWorkOrder);
    }

    @Override
    public int update(ElectricianWorkOrder electricianWorkOrder) {
        return electricianWorkOrderService.update(electricianWorkOrder);
    }

    @Override
    public int delete(Long id) {
        return electricianWorkOrderService.delete(id);
    }

    @Override
    public int deleteBatch(Long[] ids) {
        return electricianWorkOrderService.deleteBatch(ids);
    }

    /**
     * 社会电工工单审核
     *
     * @param auditReqData
     * @return
     */
    @Override
    public AuditResult audit(AuditReqData auditReqData) {
        return electricianWorkOrderService.audit(auditReqData);
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

}
