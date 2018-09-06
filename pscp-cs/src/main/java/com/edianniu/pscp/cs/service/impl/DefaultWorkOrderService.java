package com.edianniu.pscp.cs.service.impl;

import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.cs.bean.workorder.DetailsReqData;
import com.edianniu.pscp.cs.bean.workorder.DetailsResult;
import com.edianniu.pscp.cs.bean.workorder.ListQuery;
import com.edianniu.pscp.cs.bean.workorder.vo.CompanyVO;
import com.edianniu.pscp.cs.bean.workorder.vo.EvaluateVO;
import com.edianniu.pscp.cs.bean.workorder.vo.WorkOrderDetailsVO;
import com.edianniu.pscp.cs.bean.workorder.vo.WorkOrderVO;
import com.edianniu.pscp.cs.commons.PageResult;
import com.edianniu.pscp.cs.commons.ResultCode;
import com.edianniu.pscp.cs.dao.*;
import com.edianniu.pscp.cs.domain.*;
import com.edianniu.pscp.cs.service.WorkOrderService;
import com.edianniu.pscp.cs.util.DateUtil;
import com.edianniu.pscp.cs.util.DateUtils;
import com.edianniu.pscp.mis.bean.defectrecord.vo.DefectRecordVO;
import com.edianniu.pscp.mis.bean.workorder.WorkOrderType;
import com.edianniu.pscp.mis.service.dubbo.WorkOrderDefectRecordInfoService;
import com.edianniu.pscp.sps.bean.OrderIdPrefix;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private CompanyDao companyDao;
    @Autowired
    private WorkOrderDao workOrderDao;
    @Autowired
    private CompanyCustomerDao companyCustomerDao;
    @Autowired
    private WorkOrderEvaluateDao workOrderEvaluateDao;
    @Autowired
    private EngineeringProjectDao engineeringProjectDao;
    @Autowired
    private ElectricianWorkOrderDao electricianWorkOrderDao;
    @Autowired
    private WorkOrderEvaluateAttachmentDao workOrderEvaluateAttachmentDao;
    @Autowired
    private WorkOrderDefectRecordInfoService workOrderDefectRecordInfoService;

    @Override
    public PageResult<WorkOrderVO> selectPageWorkOrderInfo(ListQuery listQuery) {
        PageResult<WorkOrderVO> result = new PageResult<>();
        int total = workOrderDao.queryListWorkOrderVOCount(listQuery);
        int nextOffset = 0;
        if (total > 0) {
            List<WorkOrderVO> entityList = workOrderDao.queryListWorkOrderVO(listQuery);
            if (CollectionUtils.isNotEmpty(entityList)) {
                for (WorkOrderVO workOrderVO : entityList) {
                    workOrderVO.setTypeName(WorkOrderType.getDesc(workOrderVO.getType()));
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

    @Override
    public DetailsResult getWorkOrderDetails(DetailsReqData detailsReqData) {
        DetailsResult result = new DetailsResult();
        WorkOrderDetailsVO workOrderDetailsVO = workOrderDao.getWorkOrderDetailsVOByOrderId(detailsReqData.getOrderId());
        if (workOrderDetailsVO == null) {
            result.setResultCode(ResultCode.ERROR_401);
            result.setResultMessage("工单不存在");
            return result;
        }

        // 勘察工单不下发任何数据
        if (workOrderDetailsVO.getType().equals(WorkOrderType.PROSPECTING.getValue())) {
            return result;
        }

        workOrderDetailsVO.setTypeName(WorkOrderType.getDesc(workOrderDetailsVO.getType()));

        // 项目负责人信息
        Map<String, Object> leaderMap = electricianWorkOrderDao.getWorkOrderLeader(workOrderDetailsVO.getId());
        if (MapUtils.isNotEmpty(leaderMap)) {
            // 项目负责人
            workOrderDetailsVO.setProjectLeader(leaderMap.get("name") == null ? "" : leaderMap.get("name").toString());
            // 项目负责人联系电话
            workOrderDetailsVO.setContactTel(leaderMap.get("contactTel") == null ? "" : leaderMap.get("contactTel").toString());
        }

        // 检修项目
        List<Map<String, Object>> companyWorkOrderMapList = new ArrayList<>();
        Map<String, String> mapCheckOptionToId = new HashMap<>();
        Map<String, List<Map<String, Object>>> companyWorkOrderMap = structureCompanyWorkOrderMap(workOrderDetailsVO.getId(), mapCheckOptionToId);
        if (MapUtils.isNotEmpty(companyWorkOrderMap)) {
            for (Map.Entry<String, List<Map<String, Object>>> entry : companyWorkOrderMap.entrySet()) {
                Map<String, Object> newMap = new HashMap<>();
                newMap.put("name", entry.getKey());
                newMap.put("personnel", entry.getValue());
                newMap.put("checkOptionId", mapCheckOptionToId.get(entry.getKey()));
                companyWorkOrderMapList.add(newMap);
            }
        }
        workOrderDetailsVO.setCheckOption(JSON.toJSONString(companyWorkOrderMapList));

        result.setWorkOrder(workOrderDetailsVO);

        // 项目信息
        EngineeringProject project = engineeringProjectDao.queryObject(workOrderDetailsVO.getEngineeringProjectId());
        // 客户信息
        CompanyVO customerInfo = new CompanyVO();
        if (project != null) {
            // 项目名称
            workOrderDetailsVO.setProjectName(project.getName());

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
        Company facilitator = companyDao.getCompanyById(workOrderDetailsVO.getCompanyId());
        if (facilitator != null) {
            companyInfo.setName(facilitator.getName());// 单位名称
            companyInfo.setAddress(facilitator.getAddress());// 单位联系地址
            companyInfo.setContacts(facilitator.getContacts());// 单位联系人
            companyInfo.setContactNumber(facilitator.getContactTel());// 单位联系电话
        }
        result.setCompanyInfo(companyInfo);

        // 评价信息
        EvaluateVO evaluateVO = new EvaluateVO();
        WorkOrderEvaluate evaluate = workOrderEvaluateDao.getEvaluateByWorkId(workOrderDetailsVO.getId());
        if (evaluate != null) {
            evaluateVO.setEvaluateId(evaluate.getId());
            evaluateVO.setServiceQuality(evaluate.getServiceQuality());
            evaluateVO.setServiceSpeed(evaluate.getServiceSpeed());
            evaluateVO.setContent(evaluate.getContent());
            evaluateVO.setCreateTime(DateUtils.format(evaluate.getCreateTime(), DateUtil.YYYY_MM_DD_FORMAT));

            // 附件信息
            List<Map<String, Object>> attachmentList = workOrderEvaluateAttachmentDao.listMapByEvaluateId(evaluate.getId());
            evaluateVO.setAttachment(attachmentList);
        }
        result.setEvaluateInfo(evaluateVO);

        // 修复缺陷记录
        if (StringUtils.isNotBlank(workOrderDetailsVO.getDefectRecords())) {
            String[] strArray = workOrderDetailsVO.getDefectRecords().trim().split(",");
            List<Long> ids = new ArrayList<>();
            if (strArray.length > 0) {
                for (String str : strArray) {
                    ids.add(Long.valueOf(str));
                }
            }

            List<DefectRecordVO> defectRepairList = workOrderDefectRecordInfoService.getRepairDefectRecord(ids);
            if (CollectionUtils.isNotEmpty(defectRepairList)) {
                for (DefectRecordVO defectRecordVO : defectRepairList) {
                    defectRecordVO.setProjectName(workOrderDetailsVO.getProjectName());
                }
            }

            result.setDefectRepairList(defectRepairList);
        }

        return result;
    }

    @Override
    public WorkOrder getWorkOrderByOrderId(String orderId) {
        return workOrderDao.getWorkOrderByOrderId(orderId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateEvaluateInfo(WorkOrder workOrder, WorkOrderEvaluate workOrderEvaluate, List<WorkOrderEvaluateAttachment> attachmentList) {
        Integer c = workOrderDao.updateEvaluateInfo(workOrder);
        if (c > 0) {
            workOrderEvaluateDao.saveWorkOrderEvaluate(workOrderEvaluate);
            if (CollectionUtils.isNotEmpty(attachmentList)) {
                workOrderEvaluateAttachmentDao.saveBatch(attachmentList, workOrderEvaluate.getId());
            }
        }
    }

    private Map<String, List<Map<String, Object>>> structureCompanyWorkOrderMap(Long workOrderId, Map<String, String> mapCheckOptionToId) {
        // 企业电工工单信息
        Map<String, List<Map<String, Object>>> companyWorkOrderMap = new HashMap<>();
        List<Map<String, Object>> mapList = electricianWorkOrderDao.getCompanyElectricianWorkOrder(workOrderId);

        if (CollectionUtils.isNotEmpty(mapList)) {
            for (Map<String, Object> map : mapList) {
                if (map.get("id") != null && map.get("name") != null && map.get("checkOptionId") != null &&
                        !OrderIdPrefix.LEADER_ELECTRICIAN_CHECK_OPTION_ID.equals(map.get("checkOptionId").toString())) {
                    Map<String, Object> electrician = new HashMap<>();
                    electrician.put("id", map.get("id"));
                    electrician.put("name", map.get("name"));
                    String checkOption = map.get("checkOption") == null ? "**" : map.get("checkOption").toString();

                    if (!mapCheckOptionToId.containsKey(checkOption) && map.get("checkOptionId") != null) {
                        mapCheckOptionToId.put(checkOption, map.get("checkOptionId").toString());
                    }

                    List<Map<String, Object>> listMap = new ArrayList<>();
                    if (companyWorkOrderMap.containsKey(checkOption)) {
                        listMap = companyWorkOrderMap.get(checkOption);
                        listMap.add(electrician);
                    } else {
                        listMap.add(electrician);
                    }

                    companyWorkOrderMap.put(checkOption, listMap);
                }
            }
        }
        return companyWorkOrderMap;
    }
}
