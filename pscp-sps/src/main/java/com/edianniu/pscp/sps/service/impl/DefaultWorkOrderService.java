package com.edianniu.pscp.sps.service.impl;

import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.message.commons.MessageId;
import com.edianniu.pscp.message.service.dubbo.MessageInfoService;
import com.edianniu.pscp.mis.bean.GetUserInfoResult;
import com.edianniu.pscp.mis.bean.defectrecord.vo.DefectRecordVO;
import com.edianniu.pscp.mis.bean.electricianworkorder.ElectricianWorkOrderType;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.bean.workorder.WorkOrderType;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.mis.service.dubbo.WorkOrderDefectRecordInfoService;
import com.edianniu.pscp.sps.bean.OrderIdPrefix;
import com.edianniu.pscp.sps.bean.certificate.vo.CertificateVO;
import com.edianniu.pscp.sps.bean.project.ProjectInfo;
import com.edianniu.pscp.sps.bean.project.vo.ProjectVO;
import com.edianniu.pscp.sps.bean.socialworkorder.SocialWorkOrderStatus;
import com.edianniu.pscp.sps.bean.socialworkorder.recruit.RecruitInfo;
import com.edianniu.pscp.sps.bean.workorder.chieforder.*;
import com.edianniu.pscp.sps.bean.workorder.chieforder.vo.*;
import com.edianniu.pscp.sps.bean.workorder.electrician.ElectricianWorkOrderStatus;
import com.edianniu.pscp.sps.bean.workorder.evaluate.WorkOrderEvaluateInfo;
import com.edianniu.pscp.sps.commons.Constants;
import com.edianniu.pscp.sps.commons.PageResult;
import com.edianniu.pscp.sps.commons.ResultCode;
import com.edianniu.pscp.sps.dao.*;
import com.edianniu.pscp.sps.domain.*;
import com.edianniu.pscp.sps.service.SpsCertificateService;
import com.edianniu.pscp.sps.service.WorkOrderEvaluateService;
import com.edianniu.pscp.sps.service.WorkOrderService;
import com.edianniu.pscp.sps.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.*;

/**
 * ClassName: DefaultWorkOrderService
 * Author: tandingbo
 * CreateTime: 2017-05-12 16:48
 */
@Service
@Repository("workOrderService")
public class DefaultWorkOrderService implements WorkOrderService {
    private static final Logger logger = LoggerFactory.getLogger(DefaultWorkOrderService.class);

    @Autowired
    private WorkOrderDao workOrderDao;
    @Autowired
    private SocialWorkOrderDao socialWorkOrderDao;
    @Autowired
    private ElectricianWorkOrderDao electricianWorkOrderDao;
    @Autowired
    private EngineeringProjectDao engineeringProjectDao;
    @Autowired
    private SpsCompanyDao spsCompanyDao;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private SpsCertificateService spsCertificateService;
    @Autowired
    private SpsCompanyCustomerDao companyCustomerDao;
    @Autowired
    private MessageInfoService messageInfoService;
    @Autowired
    private WorkOrderDefectRecordInfoService workOrderDefectRecordInfoService;
    @Autowired
    private WorkOrderEvaluateService workOrderEvaluateService;

    @Override
    public PageResult<WorkOrderInfo> selectPageWorkOrderInfo(ListQuery listQuery) {
        PageResult<WorkOrderInfo> result = new PageResult<>();
        int total = workOrderDao.queryListWorkOrderCount(listQuery);
        int nextOffset = 0;
        if (total > 0) {
            List<WorkOrderInfo> entityList = workOrderDao.queryListWorkOrder(listQuery);
            if (CollectionUtils.isNotEmpty(entityList)) {
                for (WorkOrderInfo workOrderInfo : entityList) {
                    // 专职电工
                    String enterpriseElectrician = getElectricianWorkOrderRatio(ElectricianWorkOrderType.COMPANY_ELECTRICIAN_WD.getValue(), workOrderInfo.getId());
                    workOrderInfo.setEnterpriseElectrician(enterpriseElectrician);

                    // 社会电工
                    String socialElectrician = getElectricianWorkOrderRatio(ElectricianWorkOrderType.SOCIAL_ELECTRICIAN_WD.getValue(), workOrderInfo.getId());
                    workOrderInfo.setSocialElectrician(socialElectrician);

                    // 根据经纬度计算距离
                    double distance = 0D;
                    if (StringUtils.isNoneBlank(listQuery.getLongitude()) &&
                            StringUtils.isNoneBlank(listQuery.getLatitude())) {
                        distance = DistanceUtil.getDistance(Double.valueOf(listQuery.getLatitude()),
                                Double.valueOf(listQuery.getLongitude()),
                                Double.valueOf(workOrderInfo.getLatitude()),
                                Double.valueOf(workOrderInfo.getLongitude()));
                    }
                    if (distance > 1D) {
                        workOrderInfo.setDistance(String.format("%skm", BigDecimalUtil.dobCoverTwoDecimal(distance)));
                    } else {
                        BigDecimal bigDecimal = BigDecimalUtil.mul(distance, 1000D);
                        workOrderInfo.setDistance(String.format("%sm", BigDecimalUtil.dobCoverTwoDecimal(bigDecimal.doubleValue())));
                    }
                    workOrderInfo.setTypeName(WorkOrderType.getDesc(workOrderInfo.getType()));

                    // 工单负责人
                    Map<String, Object> leaderMap = electricianWorkOrderDao.getWorkOrderLeader(workOrderInfo.getId());
                    if (MapUtils.isNotEmpty(leaderMap) && leaderMap.get("name") != null) {
                        workOrderInfo.setLeaderName(String.valueOf(leaderMap.get("name")));
                    }

                    // 派单人
                    String dispatchPersonnel = getRealNameByLoginName(workOrderInfo.getDispatchPersonnel());
                    workOrderInfo.setDispatchPersonnel(dispatchPersonnel);
                    // 最后修改人
                    String lastAmendment = getRealNameByLoginName(workOrderInfo.getLastAmendment());
                    workOrderInfo.setLastAmendment(lastAmendment);
                }
            }

            result.setData(entityList);
            nextOffset = listQuery.getOffset() + entityList.size();
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
     * 根据登录名称获取用户真实姓名
     *
     * @param loginName
     * @return
     */
    private String getRealNameByLoginName(String loginName) {
        String realName = "";
        GetUserInfoResult dispatchPersonnelResult = userInfoService.getUserInfoByLoginName(loginName);
        if (dispatchPersonnelResult.isSuccess()) {
            UserInfo userInfo = dispatchPersonnelResult.getMemberInfo();
            if (StringUtils.isBlank(userInfo.getRealName())) {
                return realName;
            }
            realName = userInfo.getRealName();
        }
        return realName;
    }

    @Override
    @Transactional
    public CancelResult cancelWorkOrder(Long id, SysUserEntity userInfo) {
        CancelResult result = new CancelResult();

        WorkOrder workOrder = workOrderDao.queryObject(id, null, userInfo.getCompanyId());
        if (workOrder == null) {
            result.setResultCode(ResultCode.ERROR_401);
            result.setResultMessage("工单不存在");
            return result;
        }

        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("workOrderId", workOrder.getId());
        queryMap.put("condition", String.format("and status &gt;= %s", ElectricianWorkOrderStatus.CONFIRMED.getValue()));

        // 企业电工
        queryMap.put("type", Constants.TAG_YES);
        List<ElectricianWorkOrder> queryListElectrician = electricianWorkOrderDao.queryList(queryMap);
        if (CollectionUtils.isNotEmpty(queryListElectrician)) {
            result.setResultCode(ResultCode.ERROR_402);
            result.setResultMessage("已有电工确认，不能取消");
            return result;
        }

        // 社会电工
        queryMap.put("type", 2);
        List<ElectricianWorkOrder> queryListSocialElectrician = electricianWorkOrderDao.queryList(queryMap);
        if (CollectionUtils.isNotEmpty(queryListSocialElectrician)) {
            result.setResultCode(ResultCode.ERROR_403);
            result.setResultMessage("已确认社会电工，不能取消");
            return result;
        }

        // 设置企业工单为取消状态
        electricianWorkOrderDao.updateByWorkOrderId(workOrder.getId(), userInfo.getLoginName(), Constants.TAG_YES, ElectricianWorkOrderStatus.UNCONFIRMED.getValue(), ElectricianWorkOrderStatus.CANCELED.getValue());

        // 处理以接单的社会工单
        List<SocialWorkOrder> socialWorkOrderEntityList = socialWorkOrderDao.getEntityByWorkOrderId(workOrder.getId());
        if (CollectionUtils.isNotEmpty(socialWorkOrderEntityList)) {
            for (SocialWorkOrder socialWorkOrder : socialWorkOrderEntityList) {
                // 设置社会工单为取消状态
                Map<String, Object> updateMap = new HashMap<>();
                updateMap.put("id", socialWorkOrder.getId());
                updateMap.put("status", WorkOrderStatus.CANCELED.getValue());
                updateMap.put("modifiedTime", new Date());
                updateMap.put("modifiedUser", userInfo.getLoginName());
                socialWorkOrderDao.update(updateMap);
            }
        }

        // 设置企业工单为取消状态
        electricianWorkOrderDao.updateByWorkOrderId(workOrder.getId(), userInfo.getLoginName(), ElectricianWorkOrderType.SOCIAL_ELECTRICIAN_WD.getValue(), ElectricianWorkOrderStatus.UNCONFIRMED.getValue(), ElectricianWorkOrderStatus.CANCELED.getValue());

        // 设置工单为取消状态
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("id", workOrder.getId());
        updateMap.put("status", WorkOrderStatus.CANCELED.getValue());
        updateMap.put("modifiedTime", new Date());
        updateMap.put("modifiedUser", userInfo.getLoginName());
        workOrderDao.update(updateMap);

        return result;
    }

    @Override
    public ViewResult getWorkOrderViewInfo(String orderId, UserInfo userInfo) {
        ViewResult result = new ViewResult();

        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("orderId", orderId);
        //queryMap.put("companyId", userInfo.getCompanyId());
        WorkOrder workOrder = workOrderDao.queryEntity(queryMap);
        if (workOrder == null) {
            result.setResultCode(ResultCode.ERROR_401);
            result.setResultMessage("工单不存在");
            return result;
        }

        // 工单信息
        WorkOrderVO workOrderVO = new WorkOrderVO();
        workOrderVO.setOrderId(workOrder.getOrderId());// 工单编号
        workOrderVO.setName(workOrder.getName());// 工单名称
        workOrderVO.setContent(workOrder.getContent());// 工单详情
        workOrderVO.setDevices(workOrder.getDevices());// 检修设备名称
        workOrderVO.setAddress(workOrder.getAddress());// 工作地点
        workOrderVO.setStartTime(DateUtils.format(workOrder.getStartTime(), DateUtils.DATE_PATTERN));// 检修时间
        workOrderVO.setEndTime(DateUtils.format(workOrder.getEndTime(), DateUtils.DATE_PATTERN));
        workOrderVO.setLatitude(workOrder.getLatitude().toString());
        workOrderVO.setLongitude(workOrder.getLongitude().toString());
        workOrderVO.setTypeName(WorkOrderType.getDesc(workOrder.getType()));
        workOrderVO.setType(workOrder.getType());

        // 危险有害因数辨识
        if (StringUtils.isNoneBlank(workOrder.getHazardFactorIdentifications())) {
            result.setHazardFactorIdentifications(JSON.parseArray(workOrder.getHazardFactorIdentifications(), Map.class));
            workOrderVO.setHazardFactor(workOrder.getHazardFactorIdentifications());
            workOrderVO.setSafetyMeasuresOther(workOrder.getIdentificationOther());
        }
        result.setIdentificationText(workOrder.getIdentificationOther());

        // 安全措施
        if (StringUtils.isNoneBlank(workOrder.getSafetyMeasures())) {
            result.setSafetyMeasures(JSON.parseArray(workOrder.getSafetyMeasures(), Map.class));
            workOrderVO.setSafetyMeasures(workOrder.getSafetyMeasures());
            workOrderVO.setSafetyMeasuresOther(workOrder.getSafetyMeasuresOther());
        }
        result.setMeasureText(workOrder.getSafetyMeasuresOther());

        // 携带机械或设备
        if (StringUtils.isNoneBlank(workOrder.getCarryingTools())) {
            result.setCarryingTools(JSON.parseArray(workOrder.getCarryingTools(), Map.class));
            workOrderVO.setCarryingTools(workOrder.getCarryingTools());
        }

        // 项目信息
        ProjectInfo project = engineeringProjectDao.queryObject(workOrder.getEngineeringProjectId());
        ProjectVO projectVO = new ProjectVO();
        if (project != null) {
            projectVO.setId(project.getId());
            projectVO.setName(project.getName());

        }
        result.setProjectVO(projectVO);

        // 客户信息
        CompanyVO customerInfo = new CompanyVO();
        if (project != null) {
            CompanyCustomer companyCustomer = companyCustomerDao.getByCustomerId(project.getCustomerId());
            if (companyCustomer != null) {
                customerInfo.setName(companyCustomer.getCpName());// 单位名称
                customerInfo.setContacts(companyCustomer.getCpContact());// 单位联系人
                customerInfo.setContactNumber(companyCustomer.getContactTel());// 单位联系电话
                customerInfo.setAddress(companyCustomer.getCpAddress());// 单位联系地址
            }
        }
        result.setCustomerInfo(customerInfo);

        // 服务商信息
        CompanyVO facilitatorInfo = new CompanyVO();
        Company facilitator = spsCompanyDao.getCompanyById(workOrder.getCompanyId());
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
        Map<String, Object> leaderMap = electricianWorkOrderDao.getWorkOrderLeader(workOrder.getId());
        if (MapUtils.isNotEmpty(leaderMap)) {
            workOrderLeader.setUid(Long.valueOf(leaderMap.get("id").toString()));
            workOrderLeader.setName(leaderMap.get("name").toString());
        }
        result.setWorkOrderLeader(workOrderLeader);

        // 企业电工工单信息
        Map<String, String> mapCheckOptionToId = new HashMap<>();
        List<Map<String, Object>> companyWorkOrderMapList = new ArrayList<>();
        Map<String, List<Map<String, Object>>> companyWorkOrderMap = structureCompanyWorkOrderMap(workOrder.getId(), mapCheckOptionToId);
        if (MapUtils.isNotEmpty(companyWorkOrderMap)) {
            for (Map.Entry<String, List<Map<String, Object>>> entry : companyWorkOrderMap.entrySet()) {
                Map<String, Object> newMap = new HashMap<>();
                newMap.put("projectName", entry.getKey());
                newMap.put("electrician", entry.getValue());
                newMap.put("projectId", mapCheckOptionToId.get(entry.getKey()));
                companyWorkOrderMapList.add(newMap);
            }
        }

        result.setCompanyWorkOrder(companyWorkOrderMapList);

        // 社会电工工单信息
        Map<String, Double> totalFeeMap = new HashMap<>();
        result.setSocialWorkOrderList(structureSocialWorkOrderList(workOrder.getId(), totalFeeMap, true));

        // 修复缺陷记录
        if (StringUtils.isNotBlank(workOrder.getDefectRecords())) {
            result.setDefectRepairList(buildListDefectRepair(workOrder, workOrderVO.getProjectName()));
        }

        workOrderVO.setTotalFee(totalFeeMap.get("totalFee") == null ? 0D : totalFeeMap.get("totalFee"));
        result.setWorkOrder(workOrderVO);

        return result;
    }

    /**
     * 构建工单缺陷记录信息
     *
     * @param workOrder
     * @param projectName
     * @return
     */
    private List<DefectRecordVO> buildListDefectRepair(WorkOrder workOrder, String projectName) {
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
                    defectRecordVO.setProjectName(projectName);
                }
            }
            return defectRepairList;
        }
        return null;
    }

    @Override
    public FacilitatorAppDetailsResult getFacilitatorAppDetails(String orderId, UserInfo userInfo) {
        FacilitatorAppDetailsResult result = new FacilitatorAppDetailsResult();

        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("orderId", orderId);
        queryMap.put("companyId", userInfo.getCompanyId());
        WorkOrder workOrder = workOrderDao.queryEntity(queryMap);
        if (workOrder == null) {
            result.setResultCode(ResultCode.ERROR_401);
            result.setResultMessage("工单不存在");
            return result;
        }

        // 工单信息
        FacilitatorWorkOrderVO workOrderVO = new FacilitatorWorkOrderVO();
        workOrderVO.setId(workOrder.getId());
        // 工单类型
        workOrderVO.setType(workOrder.getType());
        workOrderVO.setName(workOrder.getName());// 工单名称
        workOrderVO.setOrderId(workOrder.getOrderId());// 工单编号
        workOrderVO.setContent(workOrder.getContent());// 工单描述
        workOrderVO.setAddress(workOrder.getAddress());// 工作地点
        String workTime = String.format("%s~%s",
                DateUtils.format(workOrder.getStartTime(), DateUtils.DATE_PATTERN),
                DateUtils.format(workOrder.getEndTime(), DateUtils.DATE_PATTERN));
        workOrderVO.setWorkTime(workTime);// 工作时间(yyyy-mm-dd~yyyy-mm-dd)
        workOrderVO.setLatitude(workOrder.getLatitude().toString());// 经度
        workOrderVO.setLongitude(workOrder.getLongitude().toString());// 纬度
        workOrderVO.setStatus(workOrder.getStatus());// 状态
        workOrderVO.setPublishTime(DateUtils.format(workOrder.getCreateTime(), DateUtils.DATE_PATTERN));//工单发布时间
        // 实际工单开始工作时间
        workOrderVO.setActualStartTime(DateUtils.format(workOrder.getActualStartTime(), DateUtils.DATE_PATTERN));
        // 实际工单结束工作时间
        workOrderVO.setActualEndTime(DateUtils.format(workOrder.getActualEndTime(), DateUtils.DATE_PATTERN));

        // 危险有害因数辨识
        if (StringUtils.isNoneBlank(workOrder.getHazardFactorIdentifications())) {
            workOrderVO.setHazardFactor(workOrder.getHazardFactorIdentifications());
            workOrderVO.setHazardFactorOther(workOrder.getIdentificationOther());
        }
        // 安全措施
        if (StringUtils.isNoneBlank(workOrder.getSafetyMeasures())) {
            workOrderVO.setSafetyMeasures(workOrder.getSafetyMeasures());
            workOrderVO.setSafetyMeasuresOther(workOrder.getSafetyMeasuresOther());
        }
        // 携带机械或设备
        if (StringUtils.isNoneBlank(workOrder.getCarryingTools())) {
        	workOrderVO.setCarryingTools(workOrder.getCarryingTools());
		}
        
        /* 
         * 2017-12-06注释，供详情页面机械设备展示用，前台已改造，此段代码暂不用
        // 服务商所有拥有的设备
        List<ToolEquipmentVO> toolList = spsToolEquipmentService.selectAllToolEquipmentVOByCompanyId(userInfo.getCompanyId());
        List<Long> allIds = new ArrayList<>();
        HashMap<Long, String> allToolsMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(toolList)) {
			for (ToolEquipmentVO tool : toolList) {
				Long id = tool.getId();
				String name = tool.getName();
				allIds.add(id);
				allToolsMap.put(id, name);
			}
		}
        // 这个工单所携带的设备
        String carryingTools = workOrder.getCarryingTools();
        List<Long> actualIds = new ArrayList<>();
        List<HashMap<String, Object>> actualToolsMapList = new ArrayList<>();
        if (StringUtils.isNotBlank(carryingTools)) {
            JSONArray jsonArray = JSON.parseArray(carryingTools);
        	for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Integer id = (Integer) jsonObject.get("id");
				String name = (String) jsonObject.get("name");
				Integer checked = (Integer) jsonObject.get("checked");
				Integer num = (Integer) jsonObject.get("num");
				actualIds.add(Long.valueOf(id.toString()));
				HashMap<String, Object> actualToolsMap = new HashMap<>();
				actualToolsMap.put("id", id);
				actualToolsMap.put("name", name);
				actualToolsMap.put("checked", checked);
				actualToolsMap.put("num", num);
				actualToolsMapList.add(actualToolsMap);
			}
        }
        // 获取这个工单没有携带的设备  即allIds与actualIds的差集
        if (CollectionUtils.isNotEmpty(allIds)) {
			allIds.removeAll(actualIds);
		}
        // 将客户所有没有被此工单选择的设备(即allIds与actualIds的差集)追加到carryingTools字符串，checked为0，num为0
        if (allIds.size() > 0) {
        	List<HashMap<String, Object>> mapList = new ArrayList<>();
        	for (Long id : allIds) {
				HashMap<String, Object> map = new HashMap<>();
        		map.put("id", id);
        		map.put("name", allToolsMap.get(id));
        		map.put("checked", 0);
        		map.put("num", 0);
        		mapList.add(map);
			}
        	if (CollectionUtils.isNotEmpty(actualToolsMapList)) {
				mapList.addAll(actualToolsMapList);
			}
        	String jsonMapList = JSON.toJSONString(mapList);
        	carryingTools = jsonMapList;
		}
        */

        // 项目负责人信息
        Map<String, Object> leaderMap = electricianWorkOrderDao.getWorkOrderLeader(workOrder.getId());
        if (MapUtils.isNotEmpty(leaderMap)) {
            // 工单负责人ID
            workOrderVO.setProjectLeaderId(leaderMap.get("id") == null ? 0L : Long.valueOf(leaderMap.get("id").toString()));
            // 项目负责人
            workOrderVO.setProjectLeader(leaderMap.get("name") == null ? "" : leaderMap.get("name").toString());
            // 项目负责人联系电话
            workOrderVO.setContactTel(leaderMap.get("contactTel") == null ? "" : leaderMap.get("contactTel").toString());
        }

        // 项目信息
        ProjectInfo project = engineeringProjectDao.queryObject(workOrder.getEngineeringProjectId());
        // 客户信息
        CompanyVO customerInfo = new CompanyVO();
        if (project != null) {
            // 项目名称
            workOrderVO.setProjectName(project.getName());

            CompanyCustomer companyCustomer = companyCustomerDao.getByCustomerId(project.getCustomerId());
            if (companyCustomer != null) {
                customerInfo.setName(companyCustomer.getCpName());// 单位名称
                customerInfo.setContacts(companyCustomer.getCpContact());// 单位联系人
                customerInfo.setContactNumber(companyCustomer.getContactTel());// 单位联系电话
                customerInfo.setAddress(companyCustomer.getCpAddress());// 单位联系地址
            }
        }
        result.setCustomerInfo(customerInfo);

        // 服务商信息
        CompanyVO companyInfo = new CompanyVO();
        Company facilitator = spsCompanyDao.getCompanyById(workOrder.getCompanyId());
        if (facilitator != null) {
            companyInfo.setName(facilitator.getName());// 单位名称
            companyInfo.setAddress(facilitator.getAddress());// 单位联系地址
            companyInfo.setContacts(facilitator.getContacts());// 单位联系人
            companyInfo.setContactNumber(facilitator.getContactTel());// 单位联系电话
        }
        result.setCompanyInfo(companyInfo);

        // 社会工单信息
        result.setSocialWorkOrderList(structureSocialWorkOrderList(workOrder.getId(), null, false));

        // 修复缺陷记录
        if (StringUtils.isNotBlank(workOrder.getDefectRecords())) {
            result.setDefectRepairList(buildListDefectRepair(workOrder, workOrderVO.getProjectName()));
        }

        // 检修项目
        List<Map<String, Object>> companyWorkOrderMapList = new ArrayList<>();
        Map<String, String> mapCheckOptionToId = new HashMap<>();
        Map<String, List<Map<String, Object>>> companyWorkOrderMap = structureCompanyWorkOrderMap(workOrder.getId(), mapCheckOptionToId);
        if (MapUtils.isNotEmpty(companyWorkOrderMap)) {
            for (Map.Entry<String, List<Map<String, Object>>> entry : companyWorkOrderMap.entrySet()) {
                Map<String, Object> newMap = new HashMap<>();
                newMap.put("name", entry.getKey());
                newMap.put("personnel", entry.getValue());
                newMap.put("checkOptionId", mapCheckOptionToId.get(entry.getKey()));
                companyWorkOrderMapList.add(newMap);
            }
        }
        workOrderVO.setCheckOption(JSON.toJSONString(companyWorkOrderMapList));
        workOrderVO.setTypeName(WorkOrderType.getDesc(workOrder.getType()));
        result.setWorkOrder(workOrderVO);

        // 工单评价
        com.edianniu.pscp.sps.bean.workorder.evaluate.ListReqData listReqData = new com.edianniu.pscp.sps.bean.workorder.evaluate.ListReqData();
        listReqData.setWorkOrderId(workOrder.getId());
        WorkOrderEvaluateInfo evaluateInfo = workOrderEvaluateService.selectOneEvaluateInfo(listReqData);
        result.setEvaluateInfo(evaluateInfo);
        return result;
    }

    private Map<String, List<Map<String, Object>>> structureCompanyWorkOrderMap(Long workOrderId, Map<String, String> mapCheckOptionToId) {
        // 企业电工工单信息
        Map<String, List<Map<String, Object>>> companyWorkOrderMap = new HashMap<>();
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("workOrderId", workOrderId);
        List<Map<String, Object>> mapList = electricianWorkOrderDao.getCompanyElectricianWorkOrder(queryMap);

        if (CollectionUtils.isNotEmpty(mapList)) {
            for (Map<String, Object> map : mapList) {
                if (map.get("id") != null && map.get("name") != null && map.get("checkOptionId") != null &&
                        !OrderIdPrefix.LEADER_ELECTRICIAN_CHECK_OPTION_ID.equals(map.get("checkOptionId").toString())) {
                    String checkOption = map.get("checkOption") == null ? "**" : map.get("checkOption").toString();

                    if (!mapCheckOptionToId.containsKey(checkOption) && map.get("checkOptionId") != null) {
                        mapCheckOptionToId.put(checkOption, map.get("checkOptionId").toString());
                    }

                    List<Map<String, Object>> listMap = new ArrayList<>();
                    // 未取消
                    if (!WorkOrderStatus.CANCELED.getValue().equals(Integer.valueOf(String.valueOf(map.get("status"))))) {
                        Map<String, Object> electrician = new HashMap<>();
                        electrician.put("id", map.get("id"));
                        electrician.put("name", map.get("name"));

                        if (companyWorkOrderMap.containsKey(checkOption)) {
                            listMap = companyWorkOrderMap.get(checkOption);
                            listMap.add(electrician);
                        } else {
                            listMap.add(electrician);
                        }
                    }

                    companyWorkOrderMap.put(checkOption, listMap);
                }
            }
        }
        return companyWorkOrderMap;
    }

    /**
     * 构建社会工单信息
     *
     * @param workOrderId
     * @param totalFeeMap
     * @return
     */
    private List<SocialWorkOrderVO> structureSocialWorkOrderList(Long workOrderId, Map<String, Double> totalFeeMap, boolean isBackground) {
        // 社会电工工单信息
        Double totalFee = 0D;
        List<SocialWorkOrderVO> socialWorkOrderList = new ArrayList<>();
        List<SocialWorkOrder> socialWorkOrderEntityList = socialWorkOrderDao.getEntityByWorkOrderId(workOrderId);
        if (CollectionUtils.isNotEmpty(socialWorkOrderEntityList)) {
            Map<Long, String> mapCertificate = getMapCertificate();
            for (SocialWorkOrder entity : socialWorkOrderEntityList) {
                SocialWorkOrderVO socialWorkOrderVO = new SocialWorkOrderVO();
                socialWorkOrderVO.setFee(entity.getFee());
                socialWorkOrderVO.setTitle(entity.getTitle());
                socialWorkOrderVO.setContent(entity.getContent());
                socialWorkOrderVO.setQuantity(entity.getQuantity());
                if (isBackground) {
                    socialWorkOrderVO.setUnit(entity.getUnit());
                    socialWorkOrderVO.setExpiryTime(DateUtils.format(entity.getExpiryTime(), DateUtils.DATE_PATTERN));
                    socialWorkOrderVO.setBeginWorkTime(DateUtils.format(entity.getBeginWorkTime(), DateUtils.DATE_PATTERN));
                    socialWorkOrderVO.setEndWorkTime(DateUtils.format(entity.getEndWorkTime(), DateUtils.DATE_PATTERN));
                } else {
                    String workTime = String.format("%S~%s",
                            DateUtils.format(entity.getBeginWorkTime(), DateUtils.DATE_PATTERN),
                            DateUtils.format(entity.getEndWorkTime(), DateUtils.DATE_PATTERN));
                    socialWorkOrderVO.setWorkTime(workTime);
                    socialWorkOrderVO.setExpiryTime(DateUtils.format(entity.getExpiryTime(), DateUtils.DATE_PATTERN));
                }
                socialWorkOrderList.add(socialWorkOrderVO);

                // 电工资质
                StringBuilder certificateNames = new StringBuilder("");
                socialWorkOrderVO.setQualifications(certificateNames.toString());
                if (StringUtils.isNotBlank(entity.getQualifications())) {
                    List<Map> qualifications = JSON.parseArray(entity.getQualifications(), Map.class);
                    for (Map map : qualifications) {
                        Long certificateId = Long.valueOf(map.get("id").toString());
                        if (mapCertificate.containsKey(certificateId)) {
                            certificateNames.append(mapCertificate.get(certificateId)).append(",");
                        }
                    }
                }
                if (StringUtils.isNotBlank(certificateNames.toString())) {
                    socialWorkOrderVO.setQualifications(certificateNames.toString().substring(0, certificateNames.lastIndexOf(",")));
                }

                // 计算总预支费用
                BigDecimal fee = BigDecimalUtil.mul(entity.getFee(), entity.getQuantity().doubleValue());
                totalFee = BigDecimalUtil.add(totalFee, fee.doubleValue()).doubleValue();
            }
        }

        if (totalFeeMap != null) {
            totalFeeMap.put("totalFee", totalFee);
        }
        return socialWorkOrderList;
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
        List<Map<String, Object>> electricianWorkOrderList = electricianWorkOrderDao.selectAllCheckOption(selectAllMap);
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
     * 电工资质
     *
     * @return
     */
    private Map<Long, String> getMapCertificate() {
        Map<Long, String> mapCertificate = new HashMap<>();
        List<CertificateVO> certificateVOList = spsCertificateService.selectAllCertificateVO();
        if (CollectionUtils.isNotEmpty(certificateVOList)) {
            for (CertificateVO certificateVO : certificateVOList) {
                mapCertificate.put(certificateVO.getId(), certificateVO.getName());
            }
        }
        return mapCertificate;
    }

    @Override
    @Transactional
    public SaveOrUpdateResult saveWorkOrderInfo(SaveOrUpdateInfo saveOrUpdateInfo) throws Exception {
        SaveOrUpdateResult result = new SaveOrUpdateResult();

        GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(saveOrUpdateInfo.getUid());
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
        Date now = new Date();

        // 工单信息
        Map<String, Object> structureWorkOrderResult = structureWorkOrder(userInfo, saveOrUpdateInfo, now);
        if (structureWorkOrderResult.get("resultCode") != null &&
                Integer.valueOf(structureWorkOrderResult.get("resultCode").toString()) != ResultCode.SUCCESS) {
            result.setResultMessage(structureWorkOrderResult.get("resultMessage").toString());
            result.setResultCode(Integer.valueOf(structureWorkOrderResult.get("resultCode").toString()));
            return result;
        }

        // 提前获取工单主键ID
        Long workOrderId = workOrderDao.getWorkOrderSequence();
        WorkOrder workOrder = (WorkOrder) structureWorkOrderResult.get("workOrder");
        workOrder.setId(workOrderId);

        // 构建电工工单信息
        Map<String, Object> structureElectricianMapResult = structureElectricianWorkOrderList(userInfo, saveOrUpdateInfo, workOrderId, now);
        if (structureElectricianMapResult.get("resultCode") != null &&
                Integer.valueOf(structureElectricianMapResult.get("resultCode").toString()) != ResultCode.SUCCESS) {
            result.setResultMessage(structureElectricianMapResult.get("resultMessage").toString());
            result.setResultCode(Integer.valueOf(structureElectricianMapResult.get("resultCode").toString()));
            return result;
        }

        // 是否需要招募社会电工（0不需要，1需要）
        Map<String, Object> structureSocialMapResult = new HashMap<>();
        if (CollectionUtils.isNotEmpty(saveOrUpdateInfo.getSocialRecruitList())) {
            structureSocialMapResult = structureSocialWorkOrderList(saveOrUpdateInfo, workOrderId, now);
            if (Integer.valueOf(structureSocialMapResult.get("resultCode").toString()) != ResultCode.SUCCESS) {
                result.setResultMessage(structureSocialMapResult.get("resultMessage").toString());
                result.setResultCode(Integer.valueOf(structureSocialMapResult.get("resultCode").toString()));
                return result;
            }
        }

        // 保存工单信息
        workOrder.setCreateUser(userInfo.getLoginName());
        workOrder.setModifiedUser(userInfo.getLoginName());
        workOrder.setModifiedTime(new Date());
        workOrderDao.save(workOrder);
        final List<ElectricianWorkOrder> electricianWorkOrderFinalList = (List<ElectricianWorkOrder>) structureElectricianMapResult.get("electricianWorkOrderList");
        // 保存电工工单信息
        electricianWorkOrderDao.saveBatch(electricianWorkOrderFinalList);

        // 保存社会工单信息
        if (structureSocialMapResult.get("socialWorkOrderList") != null) {
            List<SocialWorkOrder> socialWorkOrderList = (List<SocialWorkOrder>) structureSocialMapResult.get("socialWorkOrderList");
            socialWorkOrderDao.batchSave(socialWorkOrderList);

            StringBuilder orderIdStr = new StringBuilder("");
            List<Long> socialWorkOrderIds = new ArrayList<>();
            for (SocialWorkOrder socialWorkOrder : socialWorkOrderList) {
                Long id = socialWorkOrder.getId();
                if (!socialWorkOrderIds.contains(id)) {
                    socialWorkOrderIds.add(socialWorkOrder.getId());
                    orderIdStr.append(socialWorkOrder.getOrderId()).append(",");
                }
            }
            // 返回总支付费用
            result.setSocialWorkOrderIds(socialWorkOrderIds);
            String socialOrderIdStr = orderIdStr.substring(0, orderIdStr.lastIndexOf(","));
            result.setSocialOrderIdStr(socialOrderIdStr);
            // FIXME 社会工单保存成功后，匹配注册社会电工发送推送消息
        }

        // 工单名称
        final String name = workOrder.getName();
        ThreadUtil.getSortTimeOutThread(new Runnable() {
            @Override
            public void run() {
                // 发送推送消息
                for (ElectricianWorkOrder electricianWorkOrder : electricianWorkOrderFinalList) {
                    MessageId smsMsgId = MessageId.NEW_ORDER_REMIND;
                    Map<String, String> params = new HashMap<>();
                    params.put("name", name);

                    // 电工->sms+push
                    GetUserInfoResult dispatchPersonResult = userInfoService.getUserInfo(electricianWorkOrder.getElectricianId());
                    if (dispatchPersonResult.isSuccess()) {
                        UserInfo dispatchPerson = dispatchPersonResult.getMemberInfo();
                        messageInfoService.sendSmsAndPushMessage(dispatchPerson.getUid(), dispatchPerson.getMobile(), smsMsgId, params);
                    }
                }
            }
        });

        // 返回工单主键ID
        result.setId(workOrderId);
        result.setOrderId(workOrder.getOrderId());
        return result;
    }

    /**
     * 构建工单信息
     *
     * @param userInfo
     * @param saveOrUpdateInfo
     * @param now
     * @return
     */
    private Map<String, Object> structureWorkOrder(UserInfo userInfo, SaveOrUpdateInfo saveOrUpdateInfo, Date now) {
        WorkOrder workOrder = new WorkOrder();
        Map<String, Object> result = new HashMap<>();

        if (StringUtils.isBlank(saveOrUpdateInfo.getOrderId())) {
            result.put("resultCode", ResultCode.ERROR_401);
            result.put("resultMessage", "工单编号不能为空");
            return result;
        }
        workOrder.setOrderId(saveOrUpdateInfo.getOrderId());

        if (saveOrUpdateInfo.getType() == null) {
            result.put("resultCode", ResultCode.ERROR_401);
            result.put("resultMessage", "工单类型不能为空");
            return result;
        }
        workOrder.setType(saveOrUpdateInfo.getType());

        if (saveOrUpdateInfo.getProjectInfo() == null || saveOrUpdateInfo.getProjectInfo().getId() == null) {
            result.put("resultCode", ResultCode.ERROR_401);
            result.put("resultMessage", "请选择项目");
            return result;
        }
        workOrder.setEngineeringProjectId(saveOrUpdateInfo.getProjectInfo().getId());

        if (StringUtils.isBlank(saveOrUpdateInfo.getName())) {
            result.put("resultCode", ResultCode.ERROR_401);
            result.put("resultMessage", "工单名称不能为空");
            return result;
        }
        if (! BizUtils.checkLength(saveOrUpdateInfo.getName(), 50)) {
            result.put("resultCode", ResultCode.ERROR_401);
            result.put("resultMessage", "工单名称不能不能超过50字");
            return result;
        }

        // 工单名称重复检查
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("name", saveOrUpdateInfo.getName());
        WorkOrder workOrderByName = workOrderDao.queryEntity(queryMap);
        if (workOrderByName != null &&
                !workOrder.getOrderId().equals(workOrderByName.getOrderId())) {
            result.put("resultCode", ResultCode.ERROR_401);
            result.put("resultMessage", "工单名称已被使用");
            return result;
        }
        workOrder.setName(saveOrUpdateInfo.getName());

        Date startTime = DateUtils.parse(saveOrUpdateInfo.getStartTime(), DateUtils.DATE_PATTERN);
        if (startTime == null) {
            result.put("resultCode", ResultCode.ERROR_401);
            result.put("resultMessage", "检修日期格式不正确");
            return result;
        }
        workOrder.setStartTime(DateUtils.getStartDate(startTime));

        Date endTime = DateUtils.parse(saveOrUpdateInfo.getEndTime(), DateUtils.DATE_PATTERN);
        if (endTime == null) {
            result.put("resultCode", ResultCode.ERROR_401);
            result.put("resultMessage", "检修日期格式不正确");
            return result;
        }
        workOrder.setEndTime(DateUtils.getEndDate(endTime));

        if (StringUtils.isBlank(saveOrUpdateInfo.getAddress())) {
            result.put("resultCode", ResultCode.ERROR_401);
            result.put("resultMessage", "工作地点不能为空");
            return result;
        }
        if (! BizUtils.checkLength(saveOrUpdateInfo.getAddress(), 50)) {
            result.put("resultCode", ResultCode.ERROR_401);
            result.put("resultMessage", "工作地点不能超过50字");
            return result;
        }
        workOrder.setAddress(saveOrUpdateInfo.getAddress());

        if (saveOrUpdateInfo.getLeaderId() == null) {
            result.put("resultCode", ResultCode.ERROR_401);
            result.put("resultMessage", "请选择项目负责人");
            return result;
        }

        if (StringUtils.isBlank(saveOrUpdateInfo.getContent())) {
            result.put("resultCode", ResultCode.ERROR_401);
            result.put("resultMessage", "工单描述不能为空");
            return result;
        }
        if (! BizUtils.checkLength(saveOrUpdateInfo.getContent(), 1000)) {
        	result.put("resultCode", ResultCode.ERROR_401);
            result.put("resultMessage", "工单描述不能超过1000字");
            return result;
		}
        workOrder.setContent(saveOrUpdateInfo.getContent());

        if (StringUtils.isBlank(saveOrUpdateInfo.getIdentifications())) {
            result.put("resultCode", ResultCode.ERROR_401);
            result.put("resultMessage", "危险有害因素辨别不能为空");
            return result;
        }
        workOrder.setHazardFactorIdentifications(saveOrUpdateInfo.getIdentifications());
        // 附加危险有害因素辨别
        if (StringUtils.isNotBlank(saveOrUpdateInfo.getIdentificationText())) {
			if (! BizUtils.checkLength(saveOrUpdateInfo.getIdentificationText(), 50)) {
				result.put("resultCode", ResultCode.ERROR_401);
				result.put("resultMessage", "附加危险有害因素辨别不能超过50字");
	            return result;
			}
		}
        workOrder.setIdentificationOther(saveOrUpdateInfo.getIdentificationText());

        if (StringUtils.isBlank(saveOrUpdateInfo.getSafetyMeasures())) {
            result.put("resultCode", ResultCode.ERROR_401);
            result.put("resultMessage", "安全措施不能为空");
            return result;
        }
        workOrder.setSafetyMeasures(saveOrUpdateInfo.getSafetyMeasures());
        // 附加安全措施
        if (StringUtils.isNotBlank(saveOrUpdateInfo.getMeasureText())) {
			if (! BizUtils.checkLength(saveOrUpdateInfo.getMeasureText(), 50)) {
				result.put("resultCode", ResultCode.ERROR_401);
				result.put("resultMessage", "附加安全措施不能超过50字");
	            return result;
			}
		}
        workOrder.setSafetyMeasuresOther(saveOrUpdateInfo.getMeasureText());

        // 携带机械或设备
        workOrder.setCarryingTools(saveOrUpdateInfo.getToolequipmentInfo());
        // 工单类型为空默认检修工单
        Integer workType = saveOrUpdateInfo.getType() == null ? WorkOrderType.OVERHAUL.getValue() : saveOrUpdateInfo.getType();
        workOrder.setType(workType);// 工单类型
        workOrder.setDeleted(0);
        workOrder.setDevices("");// 检修设备名称
        workOrder.setCreateTime(now);
        workOrder.setMemberId(userInfo.getUid());
        workOrder.setCompanyId(userInfo.getCompanyId());
        workOrder.setCreateUser(userInfo.getLoginName());
        workOrder.setStatus(WorkOrderStatus.UNCONFIRMED.getValue());
        // 经纬度信息
        workOrder.setLatitude(saveOrUpdateInfo.getLatitude() == null ? 0D : saveOrUpdateInfo.getLatitude());
        workOrder.setLongitude(saveOrUpdateInfo.getLongitude() == null ? 0D : saveOrUpdateInfo.getLongitude());

        if (StringUtils.isNoneBlank(saveOrUpdateInfo.getDefectRecords())) {
            workOrder.setDefectRecords(saveOrUpdateInfo.getDefectRecords());
        }

        result.put("workOrder", workOrder);
        result.put("resultCode", ResultCode.SUCCESS);
        return result;
    }

    /**
     * 构建电工工单信息
     *
     * @param userInfo
     * @param saveOrUpdateInfo
     * @param workOrderId
     * @param now
     * @return
     */
    private Map<String, Object> structureElectricianWorkOrderList(UserInfo userInfo, SaveOrUpdateInfo saveOrUpdateInfo, Long workOrderId, Date now) {
        Map<String, Object> result = new HashMap<>();

        List<ElectricianWorkOrder> electricianWorkOrderList = new ArrayList<>();
        try {
            if (CollectionUtils.isEmpty(saveOrUpdateInfo.getElectricianWorkOrderInfoList())) {
                result.put("resultCode", ResultCode.ERROR_401);
                result.put("resultMessage", "检修项目不能为空");
                return result;
            }

            for (ElectricianWorkOrderInfo electricianWorkOrderInfo : saveOrUpdateInfo.getElectricianWorkOrderInfoList()) {
                String checkOptionId = BizUtils.getOrderId(OrderIdPrefix.ELECTRICIAN_CHECK_OPTION_ID_PREFIX);
                String checkOption = electricianWorkOrderInfo.getCheckOption();
                if (StringUtils.isBlank(checkOption)) {
                    result.put("resultCode", ResultCode.ERROR_401);
                    result.put("resultMessage", "检修项目不能为空");
                    return result;
                }
                if (! BizUtils.checkLength(checkOption, 50)) {
                	result.put("resultCode", ResultCode.ERROR_401);
                	result.put("resultMessage", "检修项目不能超过50字");
                	return result;
                }

                Map<String, Object> queryMap = new HashMap<>();
                queryMap.put("workOrderId", workOrderId);
                queryMap.put("checkOption", checkOption);
                List<ElectricianWorkOrder> list = electricianWorkOrderDao.queryList(queryMap);
                if (CollectionUtils.isNotEmpty(list)) {
                    result.put("resultCode", ResultCode.ERROR_401);
                    result.put("resultMessage", "同一工单中，检修项目不能重复");
                    return result;
                }

                if (CollectionUtils.isEmpty(electricianWorkOrderInfo.getElectricianIdList())) {
                    result.put("resultCode", ResultCode.ERROR_401);
                    result.put("resultMessage", "检修人员不能为空");
                    return result;
                }

                List<Long> electricianIdList = electricianWorkOrderInfo.getElectricianIdList();
                for (Long electricianId : electricianIdList) {
                    ElectricianWorkOrder electricianWorkOrder = new ElectricianWorkOrder();
                    electricianWorkOrder.setOrderId(BizUtils.getOrderId(OrderIdPrefix.ELECTRICIAN_WORK_ORDER_PREFIX));
                    electricianWorkOrder.setCheckOption(checkOption);
                    electricianWorkOrder.setCheckOptionId(checkOptionId);
                    electricianWorkOrder.setType(ElectricianWorkOrderType.COMPANY_ELECTRICIAN_WD.getValue());// 企业电工
                    electricianWorkOrder.setSocialWorkOrderId(0L);
                    // 是否工单负责人0否，1是
                    electricianWorkOrder.setWorkOrderLeader(0L);
                    electricianWorkOrder.setElectricianId(electricianId);
                    electricianWorkOrder.setStatus(ElectricianWorkOrderStatus.UNCONFIRMED.getValue());
                    electricianWorkOrder.setCreateTime(now);
                    electricianWorkOrder.setCreateUser(userInfo.getLoginName());
                    electricianWorkOrder.setCompanyId(userInfo.getCompanyId());
                    electricianWorkOrder.setDeleted(0);
                    electricianWorkOrder.setWorkOrderId(workOrderId);
                    electricianWorkOrderList.add(electricianWorkOrder);
                }
            }

            if (result.get("resultCode") != null) {
                return result;
            }

            // 构建工单负责人的电工工单
            ElectricianWorkOrder electricianWorkOrder = structureLeaderElectricianWorkOrder(saveOrUpdateInfo, userInfo, workOrderId, now);
            if (electricianWorkOrder == null) {
                result.put("resultCode", ResultCode.ERROR_401);
                result.put("resultMessage", "工单负责人工单创建失败");
                return result;
            }
            electricianWorkOrderList.add(electricianWorkOrder);


            if (CollectionUtils.isEmpty(electricianWorkOrderList)) {
                result.put("resultCode", ResultCode.ERROR_401);
                result.put("resultMessage", "检修项目不能为空");
                return result;
            }

            result.put("resultCode", ResultCode.SUCCESS);
            result.put("electricianWorkOrderList", electricianWorkOrderList);
        } catch (Exception e) {
            logger.error("structureElectricianWorkOrderList error:", e);
            result.put("resultCode", ResultCode.ERROR_500);
            result.put("resultMessage", "保存数据异常");
        }
        return result;
    }

    /**
     * 构建工单负责人的电工工单
     *
     * @param saveOrUpdateInfo
     * @param userInfo
     * @param workOrderId
     * @param now
     * @return
     */
    private ElectricianWorkOrder structureLeaderElectricianWorkOrder(SaveOrUpdateInfo saveOrUpdateInfo, UserInfo userInfo, Long workOrderId, Date now) {
        ElectricianWorkOrder electricianWorkOrder = new ElectricianWorkOrder();
        electricianWorkOrder.setOrderId(BizUtils.getOrderId(OrderIdPrefix.ELECTRICIAN_WORK_ORDER_PREFIX));
        electricianWorkOrder.setCheckOption("工单负责人项目");
        electricianWorkOrder.setCheckOptionId(OrderIdPrefix.LEADER_ELECTRICIAN_CHECK_OPTION_ID);
        electricianWorkOrder.setType(ElectricianWorkOrderType.COMPANY_ELECTRICIAN_WD.getValue());// 企业电工
        electricianWorkOrder.setSocialWorkOrderId(0L);
        // 是否工单负责人0否，1是
        electricianWorkOrder.setWorkOrderLeader(1L);
        electricianWorkOrder.setElectricianId(saveOrUpdateInfo.getLeaderId());
        electricianWorkOrder.setStatus(ElectricianWorkOrderStatus.UNCONFIRMED.getValue());
        electricianWorkOrder.setCreateTime(now);
        electricianWorkOrder.setCreateUser(userInfo.getLoginName());
        electricianWorkOrder.setCompanyId(userInfo.getCompanyId());
        electricianWorkOrder.setDeleted(0);
        electricianWorkOrder.setWorkOrderId(workOrderId);
        return electricianWorkOrder;
    }

    /**
     * 构建社会工单信息
     *
     * @param saveOrUpdateInfo
     * @param workOrderId
     * @return
     */
    private Map<String, Object> structureSocialWorkOrderList(SaveOrUpdateInfo saveOrUpdateInfo, Long workOrderId, Date now) {
        Map<String, Object> result = new HashMap<>();

        GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(saveOrUpdateInfo.getUid());
        if (!getUserInfoResult.isSuccess()) {
            result.put("resultCode", ResultCode.ERROR_401);
            result.put("resultMessage", "登录信息异常");
            return result;
        }
        UserInfo userInfo = getUserInfoResult.getMemberInfo();
        if (userInfo == null) {
            result.put("resultCode", ResultCode.ERROR_401);
            result.put("resultMessage", "登录信息异常");
            return result;
        }

        // 社会工单信息
        List<SocialWorkOrder> socialWorkOrderList = new ArrayList<>();
        for (RecruitInfo recruitInfo : saveOrUpdateInfo.getSocialRecruitList()) {
            SocialWorkOrder socialWorkOrder = new SocialWorkOrder();

            if (StringUtils.isBlank(recruitInfo.getQualifications())) {
                result.put("resultCode", ResultCode.ERROR_401);
                result.put("resultMessage", "电工资质不能为空");
                return result;
            }
            socialWorkOrder.setQualifications(recruitInfo.getQualifications());

            if (recruitInfo.getQuantity() == null || recruitInfo.getQuantity() < 0) {
                result.put("resultCode", ResultCode.ERROR_401);
                result.put("resultMessage", "人数不能为空");
                return result;
            }
            socialWorkOrder.setQuantity(recruitInfo.getQuantity());

            if (recruitInfo.getFee() == null || recruitInfo.getFee() < 0D) {
                result.put("resultCode", ResultCode.ERROR_401);
                result.put("resultMessage", "费用不能为空");
                return result;
            }
            socialWorkOrder.setFee(recruitInfo.getFee());

            if (StringUtils.isBlank(recruitInfo.getTitle())) {
                result.put("resultCode", ResultCode.ERROR_401);
                result.put("resultMessage", "标题不能为空");
                return result;
            }
            if (! BizUtils.checkLength(recruitInfo.getTitle(), 50)) {
                result.put("resultCode", ResultCode.ERROR_401);
                result.put("resultMessage", "标题长度不能超过50个字");
                return result;
            }
            socialWorkOrder.setTitle(recruitInfo.getTitle());

            if (StringUtils.isBlank(recruitInfo.getContent())) {
                result.put("resultCode", ResultCode.ERROR_401);
                result.put("resultMessage", "需求描述不能为空");
                return result;
            }
            if (! BizUtils.checkLength(recruitInfo.getContent(), 1000)) {
            	result.put("resultCode", ResultCode.ERROR_401);
                result.put("resultMessage", "需求描述不能超过1000");
                return result;
			}
            socialWorkOrder.setContent(recruitInfo.getContent());

            Date beginWorkTime = DateUtils.parse(recruitInfo.getBeginWorkTime(), DateUtils.DATE_PATTERN);
            if (beginWorkTime == null) {
                result.put("resultCode", ResultCode.ERROR_401);
                result.put("resultMessage", "工作时间格式不正确");
                return result;
            }
            Date endWorkTime = DateUtils.parse(recruitInfo.getEndWorkTime(), DateUtils.DATE_PATTERN);
            if (endWorkTime == null) {
                result.put("resultCode", ResultCode.ERROR_401);
                result.put("resultMessage", "工作时间格式不正确");
                return result;
            }
            if (endWorkTime.before(beginWorkTime)) {
                result.put("resultCode", ResultCode.ERROR_401);
                result.put("resultMessage", "结束时间不能早于开始时间");
                return result;
            }
            socialWorkOrder.setBeginWorkTime(DateUtils.getStartDate(beginWorkTime));
            socialWorkOrder.setEndWorkTime(DateUtils.getEndDate(endWorkTime));

            Date expiryTime = DateUtils.parse(recruitInfo.getExpiryTime(), DateUtils.DATE_PATTERN);
            if (expiryTime == null) {
                result.put("resultCode", ResultCode.ERROR_401);
                result.put("resultMessage", "派单截止时间格式不正确");
                return result;
            }
            if (DateUtils.getEndDate(expiryTime).before(now)) {
                result.put("resultCode", ResultCode.ERROR_401);
                result.put("resultMessage", "派单截止时间不能早于当前时间");
                return result;
            }
            socialWorkOrder.setExpiryTime(DateUtils.getEndDate(expiryTime));

            // 每人每天费用
            BigDecimal feeForDay = BigDecimalUtil.mul(socialWorkOrder.getFee(), socialWorkOrder.getQuantity().doubleValue());
            // 计算天数
            Double day = Double.valueOf(DateUtils.daysBetween(beginWorkTime, endWorkTime)) + 1;
            if (day < 1D) {
                result.put("resultCode", ResultCode.ERROR_401);
                result.put("resultMessage", "工作时间不能小于一天");
                return result;
            }
            // 计算总费用(人数*费用*天数)
            BigDecimal totalFee = BigDecimalUtil.mul(feeForDay.doubleValue(), day.doubleValue());
            socialWorkOrder.setTotalFee(totalFee.doubleValue());

            socialWorkOrder.setUnit(0);
            socialWorkOrder.setCreateTime(now);
            socialWorkOrder.setWorkOrderId(workOrderId);
            socialWorkOrder.setCompanyId(userInfo.getCompanyId());
            socialWorkOrder.setCreateUser(userInfo.getLoginName());
            socialWorkOrder.setStatus(SocialWorkOrderStatus.UN_PUBLISH.getValue());
            socialWorkOrder.setOrderId(BizUtils.getOrderId(OrderIdPrefix.SOCIAL_WORK_ORDER_PREFIX));

            socialWorkOrderList.add(socialWorkOrder);
        }

        if (result.get("resultCode") != null) {
            return result;
        }

        result.put("resultCode", ResultCode.SUCCESS);
        result.put("socialWorkOrderList", socialWorkOrderList);
        return result;
    }

    @Override
    @Transactional
    public SaveOrUpdateResult updateWorkOrderInfo(SaveOrUpdateInfo saveOrUpdateInfo) throws Exception {
        SaveOrUpdateResult result = new SaveOrUpdateResult();

        GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(saveOrUpdateInfo.getUid());
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

        WorkOrder workOrder = workOrderDao.queryObject(saveOrUpdateInfo.getId(), null, userInfo.getCompanyId());
        if (workOrder == null) {
            result.setResultCode(ResultCode.ERROR_401);
            result.setResultMessage("未匹配到工单信息");
            return result;
        }

        if (workOrder.getStatus().equals(WorkOrderStatus.CANCELED.getValue()) ||
                workOrder.getStatus().equals(WorkOrderStatus.UN_EVALUATE.getValue()) ||
                workOrder.getStatus().equals(WorkOrderStatus.EVALUATED.getValue())) {
            result.setResultCode(ResultCode.ERROR_401);
            result.setResultMessage("工单已完成，不可修改");
            return result;
        }
        String t1 = workOrder.getDefectRecords();
        String t2 = saveOrUpdateInfo.getDefectRecords();
        if (null == t1) {
        	t1 = " ";
		}
        if (null == t2) {
        	t2 = " ";
        }
        if (! t1.equals(t2)) {
        	result.setResultCode(ResultCode.ERROR_401);
            result.setResultMessage("缺陷记录不可编辑");
            return result;   
		}
        
        // 工单修改信息
        WorkOrder updateEntity = new WorkOrder();

        // 工单信息
        if (! BizUtils.checkLength(saveOrUpdateInfo.getName(), 50)) {
			result.set(ResultCode.ERROR_401, "工单名称不能超过50个字");
			return result;
		}
        if (StringUtils.isNotBlank(saveOrUpdateInfo.getName())) {
            Map<String, Object> queryMap = new HashMap<>();
            queryMap.put("name", saveOrUpdateInfo.getName());
            WorkOrder workOrderByName = workOrderDao.queryEntity(queryMap);
            if (workOrderByName != null && !workOrder.getId().equals(workOrderByName.getId())) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("工单名称已被使用");
                return result;
            }

            updateEntity.setName(saveOrUpdateInfo.getName());
        }
        if (! BizUtils.checkLength(saveOrUpdateInfo.getContent(), 1000)) {
			result.set(ResultCode.ERROR_401, "工单描述不能超过1000个字");
			return result;
		}
        if (StringUtils.isNotBlank(saveOrUpdateInfo.getContent())) {
            updateEntity.setContent(saveOrUpdateInfo.getContent());
        }
        if (! BizUtils.checkLength(saveOrUpdateInfo.getAddress(), 50)) {
			result.set(ResultCode.ERROR_401, "工作地点不能超过50个字");
			return result;
		}
        if (StringUtils.isNotBlank(saveOrUpdateInfo.getAddress())) {
            updateEntity.setAddress(saveOrUpdateInfo.getAddress());
        }
        if (saveOrUpdateInfo.getStartTime() != null && saveOrUpdateInfo.getEndTime() != null) {
            Date startTime = DateUtils.parse(saveOrUpdateInfo.getStartTime(), DateUtils.DATE_PATTERN);
            if (startTime == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("开始时间格式不正确");
                return result;
            }
            Date endTime = DateUtils.parse(saveOrUpdateInfo.getEndTime(), DateUtils.DATE_PATTERN);
            if (endTime == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("结束时间格式不正确");
                return result;
            }
            if (startTime.after(endTime)) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("开始时间不晚于结束时间");
                return result;
            }
            updateEntity.setStartTime(startTime);
            updateEntity.setEndTime(endTime);
        }
        if (saveOrUpdateInfo.getLatitude() != null) {
            updateEntity.setLatitude(saveOrUpdateInfo.getLatitude());
        }
        if (saveOrUpdateInfo.getLongitude() != null) {
            updateEntity.setLongitude(saveOrUpdateInfo.getLongitude());
        }
        if (StringUtils.isNotBlank(saveOrUpdateInfo.getContent())) {
            updateEntity.setContent(saveOrUpdateInfo.getContent());
        }
        if (StringUtils.isNotBlank(saveOrUpdateInfo.getSafetyMeasures())) {
            updateEntity.setSafetyMeasures(saveOrUpdateInfo.getSafetyMeasures());
        }
        if (StringUtils.isNotBlank(saveOrUpdateInfo.getIdentifications())) {
            updateEntity.setHazardFactorIdentifications(saveOrUpdateInfo.getIdentifications());
        }
        if (StringUtils.isNotBlank(saveOrUpdateInfo.getToolequipmentInfo())) {
            updateEntity.setCarryingTools(saveOrUpdateInfo.getToolequipmentInfo());
        }
        if (StringUtils.isNotBlank(saveOrUpdateInfo.getMeasureText())) {
            // 安全措施其他（业主单位负责人填写内容）
            updateEntity.setSafetyMeasuresOther(saveOrUpdateInfo.getMeasureText());
        }
        if (StringUtils.isNotBlank(saveOrUpdateInfo.getIdentificationText())) {
            // 危险有害因数辨识(业主单位负责人填写内容）
            updateEntity.setIdentificationOther(saveOrUpdateInfo.getIdentificationText());
        }

        if (saveOrUpdateInfo.getLeaderId() == null) {
            result.setResultCode(ResultCode.ERROR_401);
            result.setResultMessage("项目负责人不能为空");
            return result;
        }
        if (CollectionUtils.isEmpty(saveOrUpdateInfo.getElectricianWorkOrderInfoList())) {
            result.setResultCode(ResultCode.ERROR_401);
            result.setResultMessage("检修项目与电工信息不能为空");
            return result;
        }

        // 检修项目信息修改
        handleRepairItems(result, saveOrUpdateInfo, workOrder, userInfo);
        if (!result.isSuccess()) {
            return result;
        }

        updateEntity.setId(workOrder.getId());
        updateEntity.setModifiedUser(userInfo.getLoginName());
        updateEntity.setModifiedTime(new Date());
        workOrderDao.update(updateEntity);

        result.setOrderId(workOrder.getOrderId());
        return result;
    }

    /**
     * 工单负责人、检修项目信息修改
     *
     * @param result
     * @param saveOrUpdateInfo
     * @param workOrder
     * @param userInfo
     * @return
     * @throws Exception
     */
    private SaveOrUpdateResult handleRepairItems(SaveOrUpdateResult result, SaveOrUpdateInfo saveOrUpdateInfo,
                                                 WorkOrder workOrder, UserInfo userInfo) {
        // 工单原始企业电工信息
        Map<String, Object> queryElectricianWorkOrderMap = new HashMap<>();
        queryElectricianWorkOrderMap.put("type", ElectricianWorkOrderType.COMPANY_ELECTRICIAN_WD.getValue());
        queryElectricianWorkOrderMap.put("workOrderId", workOrder.getId());
        List<ElectricianWorkOrder> electricianWorkOrderList = electricianWorkOrderDao.queryList(queryElectricianWorkOrderMap);
        if (CollectionUtils.isEmpty(electricianWorkOrderList)) {
            result.setResultCode(ResultCode.ERROR_401);
            result.setResultMessage("工单检修信息不完整");
            return result;
        }

        Date now = new Date();
        Long leaderOldId = 0L;
        boolean leaderChange = false;
        // 工单负责人ID
        Long leaderId = saveOrUpdateInfo.getLeaderId();

        // 检修项目ID与NAME对应关系
        Map<String, String> checkOptionIdToNameMap = new HashMap<>();
        // 检修项目ID与电工ID对应关系
        Map<String, List<Long>> checkOptionIdToElectricianId = new HashMap<>();
        // 构建检修项目对应关系
        for (ElectricianWorkOrder electricianWorkOrder : electricianWorkOrderList) {
            if (electricianWorkOrder.getStatus() < 0) {
                continue;
            }

            String checkOptionId = electricianWorkOrder.getCheckOptionId();
            if (!checkOptionIdToNameMap.containsKey(checkOptionId)) {
                checkOptionIdToNameMap.put(checkOptionId, electricianWorkOrder.getCheckOption());
            }

            if (checkOptionIdToElectricianId.containsKey(checkOptionId)) {
                Long electricianId = electricianWorkOrder.getElectricianId();
                List<Long> electricianIdList = checkOptionIdToElectricianId.get(checkOptionId);
                if (!electricianIdList.contains(electricianId)) {
                    electricianIdList.add(electricianId);
                    checkOptionIdToElectricianId.put(checkOptionId, electricianIdList);
                }
            } else {
                List<Long> electricianIdList = new ArrayList<>();
                electricianIdList.add(electricianWorkOrder.getElectricianId());
                checkOptionIdToElectricianId.put(checkOptionId, electricianIdList);
            }

            if (electricianWorkOrder.getWorkOrderLeader().equals(1L)) {
                leaderOldId = electricianWorkOrder.getElectricianId();
                // 判断工单负责人有无变动
                leaderChange = !leaderOldId.equals(leaderId);
            }
        }

        // 修改检修项目信息
        List<ElectricianWorkOrder> updateCheckOptionList = new ArrayList<>();
        List<ElectricianWorkOrder> newElectricianWorkOrderList = new ArrayList<>();

        // 检修项目与电工信息
        List<ElectricianWorkOrderInfo> electricianWorkOrderInfoList = saveOrUpdateInfo.getElectricianWorkOrderInfoList();
        try {
            for (ElectricianWorkOrderInfo electricianWorkOrderInfo : electricianWorkOrderInfoList) {
                // 检修项目
                if (StringUtils.isBlank(electricianWorkOrderInfo.getCheckOption())) {
                    throw new RuntimeException("检修项目信息不完整");
                }
                String checkOption = electricianWorkOrderInfo.getCheckOption().trim();
                if (checkOption.length() > 50) {
                    throw new RuntimeException("检修项目【" + checkOption + "】长度不能超过50位");
                }

                Map<String, Object> queryMap = new HashMap<>();
                queryMap.put("workOrderId", workOrder.getId());
                queryMap.put("checkOption", checkOption);
                List<ElectricianWorkOrder> list = electricianWorkOrderDao.queryList(queryMap);

                if (StringUtils.isBlank(electricianWorkOrderInfo.getCheckOptionId())) {
                    // 新增
                    if (CollectionUtils.isNotEmpty(list)) {
                        throw new RuntimeException("同一工单中，检修项目不能重复");
                    }

                    List<Long> electricianIdList = electricianWorkOrderInfo.getElectricianIdList();
                    for (Long electricianId : electricianIdList) {
                        String checkOptionId = BizUtils.getOrderId(OrderIdPrefix.ELECTRICIAN_CHECK_OPTION_ID_PREFIX);
                        ElectricianWorkOrder electricianWorkOrder = new ElectricianWorkOrder();
                        electricianWorkOrder.setOrderId(BizUtils.getOrderId(OrderIdPrefix.ELECTRICIAN_WORK_ORDER_PREFIX));
                        electricianWorkOrder.setCheckOption(checkOption);
                        electricianWorkOrder.setCheckOptionId(checkOptionId);
                        electricianWorkOrder.setType(ElectricianWorkOrderType.COMPANY_ELECTRICIAN_WD.getValue());// 企业电工
                        electricianWorkOrder.setSocialWorkOrderId(0L);
                        // 是否工单负责人0否，1是
                        electricianWorkOrder.setWorkOrderLeader(0L);
                        electricianWorkOrder.setElectricianId(electricianId);
                        electricianWorkOrder.setStatus(ElectricianWorkOrderStatus.UNCONFIRMED.getValue());
                        electricianWorkOrder.setCreateTime(now);
                        electricianWorkOrder.setCreateUser(userInfo.getLoginName());
                        electricianWorkOrder.setCompanyId(userInfo.getCompanyId());
                        electricianWorkOrder.setDeleted(0);
                        electricianWorkOrder.setWorkOrderId(workOrder.getId());
                        newElectricianWorkOrderList.add(electricianWorkOrder);
                    }
                } else {
                    // 修改
                    String checkOptionId = electricianWorkOrderInfo.getCheckOptionId().trim();

                    if (CollectionUtils.isNotEmpty(list) && !list.get(0).getCheckOptionId().equals(checkOptionId)) {
                        throw new RuntimeException("同一工单中，检修项目不能重复");
                    }

                    if (checkOptionIdToNameMap.containsKey(checkOptionId)) {
                        String checkOptionOld = checkOptionIdToNameMap.get(checkOptionId).trim();
                        if (!checkOptionOld.equals(checkOption)) {
                            ElectricianWorkOrder electricianWorkOrder = new ElectricianWorkOrder();
                            electricianWorkOrder.setCheckOption(checkOption);
                            electricianWorkOrder.setCheckOptionId(checkOptionId);
                            electricianWorkOrder.setWorkOrderId(workOrder.getId());
                            updateCheckOptionList.add(electricianWorkOrder);
                        }
                    }

                    // 检修电工
                    List<Long> electricianIdList = electricianWorkOrderInfo.getElectricianIdList();
                    // 原始检修电工
                    List<Long> electricianOldIdList = checkOptionIdToElectricianId.get(checkOptionId);

                    List<Long> electricianIdListCopy = new ArrayList<>(Arrays.asList(new Long[electricianIdList.size()]));
                    Collections.copy(electricianIdListCopy, electricianIdList);
                    if (CollectionUtils.isNotEmpty(electricianOldIdList)) {
                        electricianIdListCopy.removeAll(electricianOldIdList);
                    }
                    if (CollectionUtils.isNotEmpty(electricianIdListCopy)) {
                        // 新增
                        for (Long electricianId : electricianIdListCopy) {
                            buildElectricianWorkOrder(userInfo, workOrder, checkOptionId, checkOption, electricianId, 0L, now, newElectricianWorkOrderList);
                        }
                    }
                }
            }
        } catch (RuntimeException e) {
            result.setResultCode(ResultCode.ERROR_401);
            result.setResultMessage(e.getMessage());
            e.printStackTrace();
        }

        if (!result.isSuccess()) {
            return result;
        }

        // 更新检修项目信息
        if (CollectionUtils.isNotEmpty(updateCheckOptionList)) {
            electricianWorkOrderDao.updateBatchCheckOption(updateCheckOptionList);
        }
        // 修改工单负责人
        if (leaderChange) {
            ElectricianWorkOrder electricianWorkOrder = new ElectricianWorkOrder();
            electricianWorkOrder.setWorkOrderLeader(1L);
            electricianWorkOrder.setElectricianId(leaderId);
            electricianWorkOrder.setWorkOrderId(workOrder.getId());
            electricianWorkOrder.setCheckOptionId(OrderIdPrefix.LEADER_ELECTRICIAN_CHECK_OPTION_ID);
            electricianWorkOrderDao.updateLeader(electricianWorkOrder);
        }
        // 新增电工工单
        if (CollectionUtils.isNotEmpty(newElectricianWorkOrderList)) {
            electricianWorkOrderDao.saveBatch(newElectricianWorkOrderList);

            // 工单名称
            final String name = workOrder.getName();
            final List<ElectricianWorkOrder> electricianWorkOrderListMsg = newElectricianWorkOrderList;
            ThreadUtil.getSortTimeOutThread(new Runnable() {
                @Override
                public void run() {
                    // 发送推送消息
                    for (ElectricianWorkOrder electricianWorkOrder : electricianWorkOrderListMsg) {
                        MessageId smsMsgId = MessageId.NEW_ORDER_REMIND;
                        Map<String, String> params = new HashMap<>();
                        params.put("name", name);

                        // 电工->sms+push
                        GetUserInfoResult dispatchPersonResult = userInfoService.getUserInfo(electricianWorkOrder.getElectricianId());
                        if (dispatchPersonResult.isSuccess()) {
                            UserInfo dispatchPerson = dispatchPersonResult.getMemberInfo();
                            messageInfoService.sendSmsAndPushMessage(dispatchPerson.getUid(), dispatchPerson.getMobile(), smsMsgId, params);
                        }
                    }
                }
            });
        }

        return result;
    }

    private void buildElectricianWorkOrder(UserInfo userInfo, WorkOrder workOrder, String checkOptionId, String checkOption, Long electricianId, Long isLeader, Date now, List<ElectricianWorkOrder> newElectricianWorkOrderList) {
        // 新增工单负责人电工工单
        ElectricianWorkOrder electricianWorkOrder = new ElectricianWorkOrder();
        electricianWorkOrder.setOrderId(BizUtils.getOrderId(OrderIdPrefix.ELECTRICIAN_WORK_ORDER_PREFIX));
        electricianWorkOrder.setCheckOption(checkOption);
        electricianWorkOrder.setCheckOptionId(checkOptionId);
        electricianWorkOrder.setType(1);// 企业电工
        electricianWorkOrder.setSocialWorkOrderId(0L);
        // 是否工单负责人0否，1是
        electricianWorkOrder.setWorkOrderLeader(isLeader);
        electricianWorkOrder.setElectricianId(electricianId);
        electricianWorkOrder.setStatus(ElectricianWorkOrderStatus.UNCONFIRMED.getValue());
        electricianWorkOrder.setCreateTime(now);
        electricianWorkOrder.setCreateUser(userInfo.getLoginName());
        electricianWorkOrder.setCompanyId(userInfo.getCompanyId());
        electricianWorkOrder.setDeleted(0);
        electricianWorkOrder.setWorkOrderId(workOrder.getId());
        newElectricianWorkOrderList.add(electricianWorkOrder);
    }

    @Override
    public WorkOrder queryObject(Long workOrderId, Long uid, Long companyId) {
        return workOrderDao.queryObject(workOrderId, uid, companyId);
    }

    /**
     * 计算电工确认比例
     *
     * @param type
     * @param workOrderId
     * @return
     */
    private String getElectricianWorkOrderRatio(Integer type, Long workOrderId) {
        int total = 0, confirmTotal = 0;

        if (ElectricianWorkOrderType.SOCIAL_ELECTRICIAN_WD.getValue().equals(type)) {
            // 社会电工招募总数
            List<SocialWorkOrder> socialWorkOrderList = socialWorkOrderDao.getEntityByWorkOrderId(workOrderId);
            if (CollectionUtils.isNotEmpty(socialWorkOrderList)) {
                for (SocialWorkOrder socialWorkOrder : socialWorkOrderList) {
                    total += socialWorkOrder.getQuantity() == null ? 0 : socialWorkOrder.getQuantity();
                }
            }
        }

        Map<String, Object> electricianWorkOrderMap = new HashMap<>();
        electricianWorkOrderMap.put("type", type);
        electricianWorkOrderMap.put("workOrderId", workOrderId);
        // 取消的工单统计
        electricianWorkOrderMap.put("unequal", WorkOrderStatus.CANCELED.getValue());
        List<ElectricianWorkOrder> entityList = electricianWorkOrderDao.queryList(electricianWorkOrderMap);
        if (CollectionUtils.isNotEmpty(entityList)) {
            for (ElectricianWorkOrder entity : entityList) {
                // 工单负责人工单不统计
                if (StringUtils.isNotBlank(entity.getCheckOptionId()) && entity.getCheckOptionId().equals(OrderIdPrefix.LEADER_ELECTRICIAN_CHECK_OPTION_ID)) {
                    continue;
                }

                if (ElectricianWorkOrderType.COMPANY_ELECTRICIAN_WD.getValue().equals(type)) {
                    total++;
                }

                // 确认人数
                if (entity.getStatus() > 0) {
                    confirmTotal++;
                }
            }
        }
        if (total == 0) {
            return "";
        }
        return String.format("%s/%s", confirmTotal, total);
    }

    @Override
    public WorkOrder getById(Long id) {
        return workOrderDao.getById(id);
    }

    @Override
    public WorkOrder getWorkOrderByCondition(Map<String, Object> queryMap) {
        return workOrderDao.getWorkOrderByCondition(queryMap);
    }

    @Override
    public PageResult<SelectDataVO> queryPageSelectData(SelectDataQuery selectDataQuery) {
        PageResult<SelectDataVO> result = new PageResult<SelectDataVO>();
        int total = workOrderDao.querySelectDataCount(selectDataQuery);
        int nextOffset = 0;
        if (total > 0) {
            List<SelectDataVO> entityList = workOrderDao.queryPageSelectData(selectDataQuery);

            result.setData(entityList);
            nextOffset = selectDataQuery.getOffset() + entityList.size();
            result.setNextOffset(nextOffset);
        }

        if (nextOffset > 0 && nextOffset < total) {
            result.setHasNext(true);
        }

        result.setOffset(selectDataQuery.getOffset());
        result.setNextOffset(nextOffset);
        result.setTotal(total);
        return result;
    }

    @Override
    public boolean existByProjectId(Long projectId) {
        return workOrderDao.getCountByProjectId(projectId) > 0 ? true : false;
    }

    @Override
    public List<WorkOrderVO> getWorkOrderVOs(WorkOrderVOQurey workOrderVOQurey) {
        return workOrderDao.getWorkOrderVOs(workOrderVOQurey);
    }

    @Override
    public int querySelectDataCount(SelectDataQuery selectDataQuery) {
        return workOrderDao.querySelectDataCount(selectDataQuery);
    }

    /**
     * 此方法仅用于操作修改人信息
     *
     * @param workOrder
     * @return
     */
    @Override
    public int updateModifiedUserInfo(WorkOrder workOrder) {
        return workOrderDao.updateModifiedUserInfo(workOrder);
    }

}
