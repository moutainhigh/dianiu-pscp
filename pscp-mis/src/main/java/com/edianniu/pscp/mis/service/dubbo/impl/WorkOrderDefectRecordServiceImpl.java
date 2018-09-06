package com.edianniu.pscp.mis.service.dubbo.impl;

import com.edianniu.pscp.mis.bean.GetUserInfoResult;
import com.edianniu.pscp.mis.bean.attachment.common.CommonAttachment;
import com.edianniu.pscp.mis.bean.defectrecord.*;
import com.edianniu.pscp.mis.bean.defectrecord.vo.DefectRecordDetailsVO;
import com.edianniu.pscp.mis.bean.defectrecord.vo.DefectRecordVO;
import com.edianniu.pscp.mis.bean.electricianworkorder.WorkOrderStatus;
import com.edianniu.pscp.mis.bean.query.DefectRecordQuery;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.bean.workorder.room.RoomInfo;
import com.edianniu.pscp.mis.commons.Constants;
import com.edianniu.pscp.mis.commons.PageResult;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.domain.CustomerDistributionRoom;
import com.edianniu.pscp.mis.domain.WorkOrder;
import com.edianniu.pscp.mis.domain.WorkOrderDefectRecord;
import com.edianniu.pscp.mis.service.CustomerDistributionRoomService;
import com.edianniu.pscp.mis.service.EngineeringProjectService;
import com.edianniu.pscp.mis.service.WorkOrderDefectRecordService;
import com.edianniu.pscp.mis.service.WorkOrderService;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.mis.service.dubbo.WorkOrderDefectRecordInfoService;
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
 * ClassName: WorkOrderDefectRecordServiceImpl
 * Author: tandingbo
 * CreateTime: 2017-09-12 10:36
 */
@Service
@Repository("workOrderDefectRecordInfoService")
public class WorkOrderDefectRecordServiceImpl implements WorkOrderDefectRecordInfoService {
    private static final Logger logger = LoggerFactory.getLogger(WorkOrderDefectRecordServiceImpl.class);

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;
    @Autowired
    @Qualifier("workOrderService")
    private WorkOrderService workOrderService;
    @Autowired
    @Qualifier("workOrderDefectRecordService")
    private WorkOrderDefectRecordService workOrderDefectRecordService;
    @Autowired
    @Qualifier("customerDistributionRoomService")
    private CustomerDistributionRoomService customerDistributionRoomService;
    @Autowired
    @Qualifier("engineeringProjectService")
    private EngineeringProjectService engineeringProjectService;

    @Override
    public ListResult pageListDefectRecord(ListReqData reqData) {
        ListResult result = new ListResult();
        try {
            GetUserInfoResult userInfoResult = userInfoService.getUserInfo(reqData.getUid());
            if (userInfoResult == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("用户信息不存在");
                return result;
            }
            UserInfo userInfo = userInfoResult.getMemberInfo();
            if (userInfo == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("用户信息不存在");
                return result;
            }
            WorkOrder workOrder = null;
            if (StringUtils.isNotBlank(reqData.getOrderId())) {
                workOrder = workOrderService.getEntityByOrderId(reqData.getOrderId());
                if (workOrder == null) {
                    result.setResultCode(ResultCode.ERROR_401);
                    result.setResultMessage("工单信息不存在");
                    return result;
                }
            }

            List<Long> roomIds = new ArrayList<>();
            if (StringUtils.isBlank(reqData.getOrderId()) && reqData.getProjectId() == null
                    && (reqData.getRoomId() == null || -1 == reqData.getRoomId())) {
                Map<String, Object> param = new HashMap<>();
                param.put("companyId", userInfo.getCompanyId());
                List<RoomInfo> roomInfoList = customerDistributionRoomService.getRoomInfoByMap(param);
                if (CollectionUtils.isNotEmpty(roomInfoList)) {
                    for (RoomInfo roomInfo : roomInfoList) {
                        roomIds.add(roomInfo.getId());
                    }
                }

                if (CollectionUtils.isEmpty(roomIds) || roomIds.size() < 1) {
                    result.setDefectRecordList(new ArrayList<DefectRecordVO>());
                    return result;
                }
            }
            
            // 根据项目ID获取关联的配电房ID
            Long projectId = reqData.getProjectId();
            if (null != projectId) {
				String roomIdsOfString = engineeringProjectService.getRoomIdsById(projectId);
            	if (StringUtils.isNotBlank(roomIdsOfString)) {
					String[] split = roomIdsOfString.split(",");
            		for (String id : split) {
						if (StringUtils.isBlank(id)) {
							continue;
						}
						roomIds.add(Long.valueOf(id));
					}
				}
			}

            if (reqData.getPageSize() <= 0) {
                reqData.setPageSize(Constants.DEFAULT_PAGE_SIZE);
            }
            
            DefectRecordQuery defectRecordQuery = new DefectRecordQuery();
            BeanUtils.copyProperties(reqData, defectRecordQuery);
            if (null == defectRecordQuery.getRoomId() || -1 == defectRecordQuery.getRoomId()) {
            	defectRecordQuery.setRoomId(null);
			}
            if (CollectionUtils.isNotEmpty(roomIds)) {
                defectRecordQuery.setRoomIds(roomIds);
            }
            if (workOrder != null) {
                defectRecordQuery.setWorkOrderId(workOrder.getId());
            }

            PageResult<DefectRecordVO> pageResult = workOrderDefectRecordService.queryList(defectRecordQuery);
            if (pageResult.getData() != null) {
                result.setDefectRecordList(pageResult.getData());
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
    public SelectDataResult getDefectRecordSelectData(SelectDataReqData reqData) {
        SelectDataResult result = new SelectDataResult();
        try {
            if (StringUtils.isBlank(reqData.getOrderId()) && reqData.getProjectId() == null
                    && (reqData.getRoomId() == null || -1 == reqData.getRoomId())) {

                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("请求参数错误");
                return result;
            }
            if (reqData.getRoomId() == null || -1 == reqData.getRoomId()) {
				reqData.setRoomId(null);
			}
            
            // 根据项目ID获取关联的配电房ID
            List<Long> roomIds = null;
            Long projectId = reqData.getProjectId();
            if (null != projectId) {
				String roomIdsOfString = engineeringProjectService.getRoomIdsById(projectId);
            	if (StringUtils.isNotBlank(roomIdsOfString)) {
            		roomIds = new ArrayList<>();
					String[] split = roomIdsOfString.split(",");
            		for (String id : split) {
						if (StringUtils.isBlank(id)) {
							continue;
						}
						roomIds.add(Long.valueOf(id));
					}
				}
			}
            
            Map<String, Object> param = new HashMap<>();
            param.put("roomId", reqData.getRoomId());
            param.put("workOrderId", reqData.getOrderId());
            param.put("roomIds", roomIds);
            /*param.put("projectId", reqData.getProjectId());*/
            param.put("status", Constants.TAG_NO);
            List<DefectRecordVO> defectRecordVOList = workOrderDefectRecordService.queryListDefectRecord(param);
            result.setDefectRecordList(defectRecordVOList);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("nearbyBuss:{}", e);
        }
        return result;
    }

    @Override
    public SaveResult saveDefectRecord(SaveReqData reqData) {
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
            /*如果是客户自己添加缺陷记录，则工单ID可为空，缺陷记录直接同roomId关联；
                                如果是工单负责人（企业电工）添加缺陷记录，则工单ID不能为空*/
            boolean isCompanyElectrician = userInfo.isCompanyElectrician();
            checkParameter(reqData, isCompanyElectrician);
            
            //对象拷贝
            List<CommonAttachment> attachmentList = reqData.getAttachmentList();
            List<com.edianniu.pscp.mis.domain.CommonAttachment> attList = new ArrayList<>();
            for (CommonAttachment commonAttachment : attachmentList) {
            	com.edianniu.pscp.mis.domain.CommonAttachment att = new com.edianniu.pscp.mis.domain.CommonAttachment();
            	BeanUtils.copyProperties(commonAttachment, att);
            	attList.add(att);
			}
            WorkOrderDefectRecord defectRecord = new WorkOrderDefectRecord();
            BeanUtils.copyProperties(reqData, defectRecord);
            defectRecord.setAttachmentList(attList);
            
            // 配电房信息
            CustomerDistributionRoom room = customerDistributionRoomService.getEntityById(reqData.getRoomId());
            if (room == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("配电房信息不存在");
                return result;
            }
            // 工单信息
            WorkOrder workOrder = null;
            if (isCompanyElectrician) {
            	workOrder = workOrderService.getEntityByOrderId(reqData.getOrderId());
                if (workOrder == null) {
                    result.setResultCode(ResultCode.ERROR_401);
                    result.setResultMessage("工单信息不存在");
                    return result;
                }
                defectRecord.setWorkOrderId(workOrder.getId());
                defectRecord.setDiscoveryOrderName(workOrder.getName());
                defectRecord.setProjectId(workOrder.getEngineeringProjectId());
			}
            defectRecord.setDiscoveryDate(DateUtil.formString(reqData.getDiscoveryDate(), DateUtil.YYYY_MM_DD_FORMAT));
            defectRecord.setStatus(CorrectionStatus.UNRESOLVED.getValue());
            defectRecord.setCreateUser(userInfo.getLoginName());
            workOrderDefectRecordService.save(defectRecord, userInfo);
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

    @Override
    public DetailsResult getDefectRecord(DetailsReqData reqData) {
        DetailsResult result = new DetailsResult();
        try {
            if (reqData.getId() == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("请求参数错误");
                return result;
            }

            DefectRecordDetailsVO details = workOrderDefectRecordService.getDefectRecordVOById(reqData.getId());
            result.setDefectRecordDetails(details);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("nearbyBuss:{}", e);
        }
        return result;
    }

    @Override
    public CorrectionResult correction(CorrectionReqData reqData) {
        CorrectionResult result = new CorrectionResult();
        try {
            checkCorrectionParameter(reqData);

            GetUserInfoResult userInfoResult = userInfoService.getUserInfo(reqData.getUid());
            if (!userInfoResult.isSuccess()) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("登录信息错误");
                return result;
            }
            // 登录用户信息
            UserInfo userInfo = userInfoResult.getMemberInfo();

            WorkOrder workOrder = workOrderService.getEntityByOrderId(reqData.getOrderId());
            if (workOrder == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("工单信息不存在");
                return result;
            }

            // 缺陷信息
            WorkOrderDefectRecord defectRecord = workOrderDefectRecordService.getEntityById(reqData.getId());
            if (defectRecord == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("缺陷信息不存在");
                return result;
            }
            if (defectRecord.getStatus().equals(CorrectionStatus.RESOLVED.getValue())) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("缺陷已整改，不能重复操作");
                return result;
            }

            defectRecord.setSolver(reqData.getSolver());
            defectRecord.setSolveOrderId(workOrder.getId());
            defectRecord.setSolveRemark(reqData.getSolveRemark());
            defectRecord.setStatus(CorrectionStatus.RESOLVED.getValue());
            defectRecord.setSolveDate(DateUtil.formString(reqData.getSolveDate(), DateUtil.YYYY_MM_DD_FORMAT));
            defectRecord.setModifiedUser(userInfo.getLoginName());
            defectRecord.setSolveAttachmentList(reqData.getSolveAttachmentList());
            
            workOrderDefectRecordService.updateSolveInfo(defectRecord, userInfo);
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

    @Override
    public RemoveResult removeDefectRecord(RemoveReqData reqData) {
        RemoveResult result = new RemoveResult();
        try {
            if (reqData.getId() == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("缺陷信息不存在");
                return result;
            }

            if (StringUtils.isBlank(reqData.getOrderId())) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("工单信息不存在");
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

            WorkOrder workOrder = workOrderService.getEntityByOrderId(reqData.getOrderId());
            if (workOrder == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("工单信息不存在");
                return result;
            }

            if (workOrder.getStatus() >= WorkOrderStatus.PENDING_EVALUATION.getValue()) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("工单已经结束，不能删除缺陷信息");
                return result;
            }

            // 缺陷信息
            WorkOrderDefectRecord defectRecord = workOrderDefectRecordService.getEntityById(reqData.getId());
            if (defectRecord == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("缺陷信息不存在");
                return result;
            }

            if (!workOrder.getId().equals(defectRecord.getWorkOrderId())) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("只能在发现工单中删除");
                return result;
            }

            Map<String, Object> updateMap = new HashMap<>();
            updateMap.put("deleted", -1);
            updateMap.put("id", reqData.getId());
            updateMap.put("modifiedUser", userInfo.getLoginName());
            workOrderDefectRecordService.updateMapCondition(updateMap);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("nearbyBuss:{}", e);
        }
        return result;
    }

    /**
     * 获取修复缺陷记录
     *
     * @param ids
     * @return
     */
    @Override
    public List<DefectRecordVO> getRepairDefectRecord(List<Long> ids) {
        return workOrderDefectRecordService.getRepairDefectRecord(ids);
    }

    /**
     * 缺陷整改参数检查
     *
     * @param reqData
     * @throws RuntimeException
     */

    private void checkCorrectionParameter(CorrectionReqData reqData) throws RuntimeException {
        if (StringUtils.isBlank(reqData.getOrderId())) {
            throw new RuntimeException("工单信息不能为空");
        }
        if (reqData.getId() == null) {
            throw new RuntimeException("缺陷信息不能为空");
        }
        if (StringUtils.isBlank(reqData.getSolver())) {
            throw new RuntimeException("验收人不能为空");
        }
        if (StringUtils.isBlank(reqData.getSolveDate())) {
            throw new RuntimeException("整改日期不能为空");
        }
        Date solveDate = DateUtil.formString(reqData.getSolveDate(), DateUtil.YYYY_MM_DD_FORMAT);
        if (solveDate == null) {
            throw new RuntimeException("整改日期格式不正确");
        }
        if (solveDate.after(new Date())) {
            throw new RuntimeException("整改日期不能在当前时间之后");
        }
    }

    /**
     * 保存数据前参数检查
     *
     * @param reqData
     * @throws RuntimeException
     */
    private void checkParameter(SaveReqData reqData, boolean isCompanyElectrician) throws RuntimeException {
        if (isCompanyElectrician) {   // 工单负责人添加记录时，工单ID不可为空
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
        if (! BizUtils.checkLength(reqData.getDefectContent(), 1000)) {
        	throw new RuntimeException("缺陷内容不能为空或超过1000字");
		}
        if (StringUtils.isBlank(reqData.getDiscoveryDate())) {
            throw new RuntimeException("发现日期不能为空");
        }
        Date discoveryDate = DateUtil.formString(reqData.getDiscoveryDate(), DateUtil.YYYY_MM_DD_FORMAT);
        if (discoveryDate == null) {
            throw new RuntimeException("发现日期格式不正确");
        }
        if (discoveryDate.after(new Date())) {
            throw new RuntimeException("发现日期不能在当前时间之后");
        }
        if (StringUtils.isBlank(reqData.getDiscoverer())) {
            throw new RuntimeException("发现人不能为空");
        }
        if (StringUtils.isBlank(reqData.getDiscoveryCompany())) {
            throw new RuntimeException("发现单位不能为空");
        }
        if (StringUtils.isBlank(reqData.getContactNumber())) {
            throw new RuntimeException("联系电话不能为空");
        }
        if (StringUtils.isNotBlank(reqData.getRemark())) {
			if (! BizUtils.checkLength(reqData.getRemark(), 1000)) {
				throw new RuntimeException("备注不能超过1000字");
			}
		}
    }

}
