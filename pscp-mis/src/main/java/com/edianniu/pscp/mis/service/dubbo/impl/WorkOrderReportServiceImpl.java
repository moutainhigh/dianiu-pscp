package com.edianniu.pscp.mis.service.dubbo.impl;

import com.edianniu.pscp.mis.bean.GetUserInfoResult;
import com.edianniu.pscp.mis.bean.attachment.common.AttachmentQuery;
import com.edianniu.pscp.mis.bean.attachment.common.BusinessType;
import com.edianniu.pscp.mis.bean.electricianworkorder.WorkOrderStatus;
import com.edianniu.pscp.mis.bean.query.WorkOrderReportQuery;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.bean.workorder.room.RoomInfo;
import com.edianniu.pscp.mis.bean.worksheetreport.*;
import com.edianniu.pscp.mis.bean.worksheetreport.vo.ReportDetailsVO;
import com.edianniu.pscp.mis.bean.worksheetreport.vo.ReportListVO;
import com.edianniu.pscp.mis.commons.Constants;
import com.edianniu.pscp.mis.commons.PageResult;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.domain.*;
import com.edianniu.pscp.mis.service.*;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.mis.service.dubbo.WorkOrderReportInfoService;
import com.edianniu.pscp.mis.util.BizUtils;
import com.edianniu.pscp.mis.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * ClassName: WorkOrderReportServiceImpl
 * Author: tandingbo
 * CreateTime: 2017-09-12 10:27
 */
@Service
@Repository("workOrderReportInfoService")
public class WorkOrderReportServiceImpl implements WorkOrderReportInfoService {
    private static final Logger logger = LoggerFactory.getLogger(WorkOrderReportServiceImpl.class);

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;
    @Autowired
    @Qualifier("workOrderService")
    private WorkOrderService workOrderService;
    @Autowired
    @Qualifier("workOrderReportService")
    private WorkOrderReportService workOrderReportService;
    @Autowired
    @Qualifier("engineeringProjectService")
    private EngineeringProjectService engineeringProjectService;
    @Autowired
    @Qualifier("customerDistributionRoomService")
    private CustomerDistributionRoomService customerDistributionRoomService;
    @Autowired
    @Qualifier("commonAttachmentService")
    private CommonAttachmentService commonAttachmentService;

    @Override
    public ListResult pageListReport(ListReqData reqData) {
        ListResult result = new ListResult();
        try {
            GetUserInfoResult userInfoResult = userInfoService.getUserInfo(reqData.getUid());
            if (!userInfoResult.isSuccess()) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("登录信息错误");
                return result;
            }
            // 登录用户信息
            UserInfo userInfo = userInfoResult.getMemberInfo();
            if (userInfo == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("登录信息错误");
                return result;
            }

            List<Long> roomIds = new ArrayList<>();
            if (reqData.getType() != ReportType.SURVEY.getValue()
                    && StringUtils.isBlank(reqData.getOrderId())
                    && (reqData.getRoomId() == null ||  -1 == reqData.getRoomId())) {

                Map<String, Object> param = new HashMap<>();
                param.put("companyId", userInfo.getCompanyId());
                List<RoomInfo> roomInfoList = customerDistributionRoomService.getRoomInfoByMap(param);
                if (CollectionUtils.isNotEmpty(roomInfoList)) {
                    for (RoomInfo roomInfo : roomInfoList) {
                        roomIds.add(roomInfo.getId());
                    }
                }

                if (CollectionUtils.isEmpty(roomIds) || roomIds.size() < 1) {
                    if (reqData.getType() == ReportType.REPAIR_TEST.getValue()) {
                        result.setRepairTestRecordList(new ArrayList<ReportListVO>());
                    } else if (reqData.getType() == ReportType.PATROL.getValue()) {
                        result.setPatrolRecordList(new ArrayList<ReportListVO>());
                    } else if (reqData.getType() == ReportType.SURVEY.getValue()) {
                        result.setSurveyRecordList(new ArrayList<ReportListVO>());
                    }

                    return result;
                }
            }

            if (reqData.getPageSize() <= 0) {
                reqData.setPageSize(Constants.DEFAULT_PAGE_SIZE);
            }
            WorkOrderReportQuery reportQuery = new WorkOrderReportQuery();
            BeanUtils.copyProperties(reqData, reportQuery);
            if (null == reportQuery.getRoomId() || -1 == reportQuery.getRoomId()) {
            	reportQuery.setRoomId(null);
			}
            if (CollectionUtils.isNotEmpty(roomIds)) {
                reportQuery.setRoomIds(roomIds);
            }

            if (StringUtils.isNotBlank(reqData.getOrderId())) {
                // 工单信息
                WorkOrder workOrder = workOrderService.getEntityByOrderId(reqData.getOrderId());
                if (workOrder == null) {
                    result.setResultCode(ResultCode.ERROR_401);
                    result.setResultMessage("工单不存在");
                    return result;
                }
                reportQuery.setWorkOrderId(workOrder.getId());
            }

            PageResult<ReportListVO> pageResult = workOrderReportService.queryList(reportQuery);
            if (pageResult.getData() != null) {
                if (reqData.getType() == ReportType.REPAIR_TEST.getValue()) {
                    result.setRepairTestRecordList(pageResult.getData());
                } else if (reqData.getType() == ReportType.PATROL.getValue()) {
                    result.setPatrolRecordList(pageResult.getData());
                } else if (reqData.getType() == ReportType.SURVEY.getValue()) {
                    result.setSurveyRecordList(structureSurveyRecordList(userInfo, pageResult.getData()));
                }
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

    /**
     * 构建勘察记录数据
     * @param userInfo
     * @param datas
     * @return
     */
    private List<ReportListVO> structureSurveyRecordList(UserInfo userInfo, List<ReportListVO> datas) {
        List<ReportListVO> dataList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(dataList)) {
            return dataList;
        }

        for (ReportListVO data : datas) {
            ReportListVO reportListVO = new ReportListVO();
            reportListVO.setId(data.getId());
            reportListVO.setWorkDate(data.getWorkDate());
            reportListVO.setWorkContent(data.getWorkContent());
            // 获取附件信息
            reportListVO.setAttachmentList(buildSurveyRecordAttachment(data.getId(), BusinessType.SURVEY_RECORD_ATTACHMENT.getValue(), null, 0, 3));
            dataList.add(reportListVO);
        }
        return dataList;
    }

    @Override
    public SelectDataResult getReportSelectData(SelectDataReqData reqData) {
        SelectDataResult result = new SelectDataResult();
        try {
            if (StringUtils.isBlank(reqData.getOrderId()) &&
                    reqData.getRoomId() == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("请求参数错误");
                return result;
            }

            GetUserInfoResult userInfoResult = userInfoService.getUserInfo(reqData.getUid());
            if (!userInfoResult.isSuccess()) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("登录信息错误");
                return result;
            }
            // 登录用户信息
            @SuppressWarnings("unused")
			UserInfo userInfo = userInfoResult.getMemberInfo();

            // 工单信息
            WorkOrder workOrder = workOrderService.getEntityByOrderId(reqData.getOrderId());
            if (workOrder == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("工单不存在");
                return result;
            }
            
            if (null == reqData.getRoomId() || -1 == reqData.getRoomId()) {
            	reqData.setRoomId(null);
			}
            
            Map<String, Object> map = new HashMap<>();
            map.put("workOrderId", workOrder.getId());
            map.put("roomId", reqData.getRoomId());
            map.put("type", reqData.getType());
            List<ReportListVO> workOrderReportList = workOrderReportService.queryListReport(map);
            if (reqData.getType() == ReportType.REPAIR_TEST.getValue()) {
                result.setRepairTestRecordList(workOrderReportList);
            } else if (reqData.getType() == ReportType.PATROL.getValue()) {
                result.setPatrolRecordList(workOrderReportList);
            }
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("nearbyBuss:{}", e);
        }
        return result;
    }

    /**
     * 修试、巡视报告保存
     */
    @Override
    public SaveResult saveWorkOrderReport(SaveReqData reqData) {
        SaveResult result = new SaveResult();
        try {
            GetUserInfoResult userInfoResult = userInfoService.getUserInfo(reqData.getUid());
            if (!userInfoResult.isSuccess()) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("登录信息错误");
                return result;
            }
            // 登录用户信息
            UserInfo userInfo = userInfoResult.getMemberInfo();
            boolean isCompanyElectrician = userInfo.isCompanyElectrician();
            
            /*如果是客户自己添加报告，则工单ID可为空，报告直接同roomId关联；
                                    如果是工单负责人（企业电工）添加报告，则工单ID不能为空*/
            checkParameter(reqData, isCompanyElectrician);
            WorkOrderReport report = new WorkOrderReport();
            BeanUtils.copyProperties(reqData, report);
            
            // 配电房信息
            CustomerDistributionRoom room = customerDistributionRoomService.getEntityById(reqData.getRoomId());
            if (room == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("配电房信息不存在");
                return result;
            }
            WorkOrder workOrder = null;
            EngineeringProject project = null;
            if (isCompanyElectrician) {
            	 // 工单信息
            	workOrder = workOrderService.getEntityByOrderId(reqData.getOrderId());
                if (workOrder == null) {
                    result.setResultCode(ResultCode.ERROR_401);
                    result.setResultMessage("工单信息不存在");
                    return result;
                }
                // 项目信息
                project = engineeringProjectService.getById(workOrder.getEngineeringProjectId());
                if (project == null) {
                    result.setResultCode(ResultCode.ERROR_401);
                    result.setResultMessage("项目信息不存在");
                    return result;
                }
                report.setWorkOrderId(workOrder.getId());
                report.setWorkOrderName(workOrder.getName());
                report.setProjectName(project.getName());
			}

            report.setCreateUser(userInfo.getLoginName());
            report.setWorkDate(DateUtil.formString(reqData.getWorkDate(), DateUtil.YYYY_MM_DD_FORMAT));
            workOrderReportService.save(report, userInfo);
            
            // 保存报告附件
            if (reqData.getType() == ReportType.REPAIR_TEST.getValue()) {
            	saveReportAttachment(report.getAttachmentList(), BusinessType.REPAIR_TEST_RECORD_ATTACHMENT.getValue(), report.getId(), userInfo);
            } else if (reqData.getType() == ReportType.PATROL.getValue()) {
            	saveReportAttachment(report.getAttachmentList(), BusinessType.PATROL_RECORD_ATTACHMENT.getValue(), report.getId(), userInfo);
            } 
        } catch (RuntimeException e) {
            result.setResultCode(ResultCode.ERROR_401);
            result.setResultMessage(e.getMessage());
            logger.error("nearbyBuss:{}", e.getMessage());
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("nearbyBuss:{}", e);
        }
        return result;
    }
    
    /**
     * 勘察报告保存
     */
    @Override
    public SaveResult saveSurveyReport(SaveReqData reqData) {
        SaveResult result = new SaveResult();
        try {
            if (StringUtils.isBlank(reqData.getOrderId())) {
                result.set(ResultCode.ERROR_401, "工单编号不能为空");
                return result;
            }
            if (! BizUtils.checkLength(reqData.getWorkContent(), 1000)) {
            	result.set(ResultCode.ERROR_401, "内容不能为空或超过1000个字");
                return result;
			}
            if (StringUtils.isNotBlank(reqData.getRemark())) {
				if (! BizUtils.checkLength(reqData.getRemark(), 1000)) {
					result.set(ResultCode.ERROR_401, "备注不能超过1000个字");
	                return result;
				}
			}
            
            GetUserInfoResult userInfoResult = userInfoService.getUserInfo(reqData.getUid());
            if (!userInfoResult.isSuccess()) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("登录信息错误");
                return result;
            }
            // 登录用户信息
            UserInfo userInfo = userInfoResult.getMemberInfo();

            // 工单信息
            WorkOrder workOrder = workOrderService.getEntityByOrderId(reqData.getOrderId());
            if (workOrder == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("工单不存在");
                return result;
            }

            WorkOrderReport report = new WorkOrderReport();
            BeanUtils.copyProperties(reqData, report);
            report.setWorkOrderId(workOrder.getId());
            report.setWorkOrderName(workOrder.getName());
            report.setWorkDate(new Date());
            report.setCreateUser(userInfo.getLoginName());
            workOrderReportService.save(report, userInfo);
            // 附件保存
            List<com.edianniu.pscp.mis.bean.attachment.common.CommonAttachment> attachmentList = reqData.getAttachmentList();
            List<CommonAttachment> attList = new ArrayList<>();
            for (com.edianniu.pscp.mis.bean.attachment.common.CommonAttachment commonAttachment : attachmentList) {
            	CommonAttachment att = new CommonAttachment();
            	BeanUtils.copyProperties(commonAttachment, att);
            	attList.add(att);
			}
            
            saveReportAttachment(attList, BusinessType.SURVEY_RECORD_ATTACHMENT.getValue(), report.getId(), userInfo);

        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("nearbyBuss:{}", e);
        }
        return result;
    }

    /**
     * 报告详情
     */
    @Override
    public DetailsResult getWorkOrderReportById(DetailsReqData reqData) {
        DetailsResult result = new DetailsResult();
        try {
            if (reqData.getId() == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("参数错误");
                return result;
            }

            ReportDetailsVO reportDetailsVO = workOrderReportService.getReportDetailsVOById(reqData.getId(), reqData.getType());
            if (reqData.getType() == ReportType.REPAIR_TEST.getValue()) {
                result.setRepairTestDetails(structureRecordDetails(reportDetailsVO, BusinessType.REPAIR_TEST_RECORD_ATTACHMENT.getValue()));
            } else if (reqData.getType() == ReportType.PATROL.getValue()) {
                result.setPatrolRecordDetails(structureRecordDetails(reportDetailsVO, BusinessType.PATROL_RECORD_ATTACHMENT.getValue()));
            } else if (reqData.getType() == ReportType.SURVEY.getValue()) {  
                result.setSurveyDetails(structureRecordDetails(reportDetailsVO, BusinessType.SURVEY_RECORD_ATTACHMENT.getValue()));
            }
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("nearbyBuss:{}", e);
        }
        return result;
    }

    /**
     * 构建记录详情数据
     * @param reportDetailsVO
     * @param businessType   附件业务类型
     * @return
     */
    private ReportDetailsVO structureRecordDetails(ReportDetailsVO reportDetailsVO, Integer businessType) {
        if (reportDetailsVO == null) {
            return null;
        }
        // 附件信息   
        reportDetailsVO.setAttachmentList(buildSurveyRecordAttachment(reportDetailsVO.getId(), businessType, null, null, null));
        return reportDetailsVO;
    }
    
    /**
     * 查询附件
     * @param foreignKey    外键
     * @param businessType  附件业务类型
     * @return
     */
    private List<Map<String, Object>> buildSurveyRecordAttachment(Long foreignKey, Integer businessType, Long companyId, Integer offset, Integer pageSize) {
        List<Map<String, Object>> attachmentListMap = null;
        // 获取附件信息
        AttachmentQuery attachmentQuery = new AttachmentQuery();
        if (offset != null) {
            attachmentQuery.setOffset(offset);
        }
        if (pageSize != null) {
            attachmentQuery.setPageSize(pageSize);
        }
        if (companyId != null) {
            attachmentQuery.setCompanyId(companyId);
        }
        attachmentQuery.setForeignKey(foreignKey);
        attachmentQuery.setBusinessType(businessType);
        List<CommonAttachment> attachmentList = commonAttachmentService.queryListAttachment(attachmentQuery);
        if (CollectionUtils.isNotEmpty(attachmentList)) {
            attachmentListMap = new ArrayList<>();
            for (CommonAttachment attachment : attachmentList) {
                Map<String, Object> map = new HashMap<>();
                map.put("fid", attachment.getFid());
                map.put("orderNum", attachment.getOrderNum());
                attachmentListMap.add(map);
            }
        }
        return attachmentListMap;
    }

    /**
     * 报告删除
     */
    @Override
    public RemoveResult removeWorkOrderReport(RemoveReqData reqData) {
        RemoveResult result = new RemoveResult();
        try {
            if (reqData.getId() == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("参数错误");
                return result;
            }
            GetUserInfoResult userInfoResult = userInfoService.getUserInfo(reqData.getUid());
            if (!userInfoResult.isSuccess()) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("登录信息错误");
                return result;
            }
            // 登录用户信息
            UserInfo userInfo = userInfoResult.getMemberInfo();

            WorkOrderReport report = workOrderReportService.getEntityById(reqData.getId(), reqData.getType());
            if (report == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("参数错误");
                return result;
            }

            WorkOrder workOrder = workOrderService.getEntityById(report.getWorkOrderId());
            if (workOrder == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("参数错误");
                return result;
            }

            if (reqData.getType() != ReportType.SURVEY.getValue()
                    && !userInfo.getCompanyId().equals(workOrder.getCompanyId())) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("参数错误");
                return result;
            }
            
            if (workOrder.getStatus() >= WorkOrderStatus.PENDING_EVALUATION.getValue()) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("工单已经结束，不能删除");
                return result;
            }

            Map<String, Object> updateMap = new HashMap<>();
            updateMap.put("deleted", -1);
            updateMap.put("id", reqData.getId());
            updateMap.put("type", report.getType());
            updateMap.put("modifiedUser", userInfo.getRealName());
            workOrderReportService.updateMapCondition(updateMap);
            // 删除附件
            if (reqData.getType() == ReportType.REPAIR_TEST.getValue()) {
            	deleteSurveyAttachment(reqData.getId(), BusinessType.REPAIR_TEST_RECORD_ATTACHMENT.getValue(), userInfo.getRealName());
            } else if (reqData.getType() == ReportType.PATROL.getValue()) {
            	deleteSurveyAttachment(reqData.getId(), BusinessType.PATROL_RECORD_ATTACHMENT.getValue(), userInfo.getRealName());
            } else if (reqData.getType() == ReportType.SURVEY.getValue()) {  
            	deleteSurveyAttachment(reqData.getId(), BusinessType.SURVEY_RECORD_ATTACHMENT.getValue(), userInfo.getRealName());
            }
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("nearbyBuss:{}", e);
        }
        return result;
    }
    
    /**
     * 附件保存
     * @param attachmentList
     * @param businessType
     * @param foreignKey
     * @param userInfo
     */
    public void saveReportAttachment(List<CommonAttachment> attachmentList, Integer businessType, Long foreignKey, UserInfo userInfo){
    	if (CollectionUtils.isNotEmpty(attachmentList)) {
			for (CommonAttachment attachment : attachmentList) {
                attachment.setForeignKey(foreignKey);
                attachment.setIsOpen(Constants.TAG_YES);
                attachment.setBusinessType(BusinessType.SURVEY_RECORD_ATTACHMENT.getValue());
                attachment.setType(attachment.getType() == null ? 1 : attachment.getType());
                attachment.setOrderNum(attachment.getOrderNum() == null ? 0L : attachment.getOrderNum());
                attachment.setBusinessType(businessType);
                attachment.setCompanyId(userInfo.getCompanyId());
                attachment.setMemberId(userInfo.getUid());
			}
			commonAttachmentService.saveBatchEntity(attachmentList);
		}
    }
    
    /**
     * 删除附件
     *
     * @param foreignKey
     * @param businessType
     * @param modifiedUser
     */
    private void deleteSurveyAttachment(Long foreignKey, Integer businessType, String modifiedUser) {
        AttachmentQuery attachmentQuery = new AttachmentQuery();
        attachmentQuery.setForeignKey(foreignKey);
        attachmentQuery.setBusinessType(businessType);
        List<CommonAttachment> attachmentList = commonAttachmentService.queryListAttachment(attachmentQuery);
        if (CollectionUtils.isNotEmpty(attachmentList)) {
            List<Long> ids = new ArrayList<>();
            for (CommonAttachment attachment : attachmentList) {
                Long id = attachment.getId();
                if (!ids.contains(id)) {
                    ids.add(id);
                }
            }
            if (CollectionUtils.isNotEmpty(ids)) {
                commonAttachmentService.deleteBatchEntity(ids, modifiedUser);
            }
        }
    }

    /**
     * 参数检查
     *
     * @param reqData
     */
    private void checkParameter(SaveReqData reqData, boolean isCompanyElectrician) throws RuntimeException {
    	if (isCompanyElectrician) {
    		if (StringUtils.isBlank(reqData.getOrderId())) {
                throw new RuntimeException("工单信息不能为空");
            }
		} 
    	if (reqData.getRoomId() == null) {
            throw new RuntimeException("配电房信息不能为空");
        }
        if (StringUtils.isBlank(reqData.getDeviceName())) {
            throw new RuntimeException("设备名称不能为空");
        }
        if (! BizUtils.checkLength(reqData.getWorkContent(), 1000)) {
        	throw new RuntimeException("内容不能为空或超过1000字");
		}
        if (StringUtils.isBlank(reqData.getWorkDate())) {
            throw new RuntimeException("日期不能为空");
        }
        Date solveDate = DateUtil.formString(reqData.getWorkDate(), DateUtil.YYYY_MM_DD_FORMAT);
        if (solveDate == null) {
            throw new RuntimeException("日期格式不正确");
        }
        if (StringUtils.isBlank(reqData.getLeadingOfficial())) {
            throw new RuntimeException("负责人不能为空");
        }
        if (StringUtils.isBlank(reqData.getCompanyName())) {
            throw new RuntimeException("单位名称不能为空");
        }
        if (StringUtils.isBlank(reqData.getContactNumber())) {
            throw new RuntimeException("联系电话不能为空");
        }
        if (StringUtils.isBlank(reqData.getReceiver()) && ReportType.REPAIR_TEST.getValue() == reqData.getType()) {
            throw new RuntimeException("验收人不能为空");
        }
        if (StringUtils.isNotBlank(reqData.getRemark())) {
			if (! BizUtils.checkLength(reqData.getRemark(), 1000)) {
				throw new RuntimeException("备注不能超过1000字");
			}
		}
    }
}
