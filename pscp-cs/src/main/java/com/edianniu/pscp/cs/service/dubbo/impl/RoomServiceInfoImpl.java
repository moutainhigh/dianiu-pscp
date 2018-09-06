package com.edianniu.pscp.cs.service.dubbo.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.edianniu.pscp.cs.bean.engineeringproject.EngineeringProjectStatus;
import com.edianniu.pscp.cs.bean.engineeringproject.vo.EngineeringProjectVO;
import com.edianniu.pscp.cs.bean.needs.NeedsStatus;
import com.edianniu.pscp.cs.bean.needs.vo.NeedsVO;
import com.edianniu.pscp.cs.bean.room.ListQuery;
import com.edianniu.pscp.cs.bean.room.ListReqData;
import com.edianniu.pscp.cs.bean.room.ListResult;
import com.edianniu.pscp.cs.bean.room.RoomInfo;
import com.edianniu.pscp.cs.bean.room.RoomListResult;
import com.edianniu.pscp.cs.bean.room.SaveResult;
import com.edianniu.pscp.cs.bean.room.vo.RoomVO;
import com.edianniu.pscp.cs.commons.CacheKey;
import com.edianniu.pscp.cs.commons.PageResult;
import com.edianniu.pscp.cs.commons.ResultCode;
import com.edianniu.pscp.cs.domain.CompanyCustomer;
import com.edianniu.pscp.cs.service.CompanyCustomerService;
import com.edianniu.pscp.cs.service.DutyLogService;
import com.edianniu.pscp.cs.service.EquipmentService;
import com.edianniu.pscp.cs.service.FirefightingEquipmentService;
import com.edianniu.pscp.cs.service.NeedsService;
import com.edianniu.pscp.cs.service.OperationRecordService;
import com.edianniu.pscp.cs.service.ProjectService;
import com.edianniu.pscp.cs.service.RoomService;
import com.edianniu.pscp.cs.service.SafetyEquipmentService;
import com.edianniu.pscp.cs.service.dubbo.RoomInfoService;
import com.edianniu.pscp.cs.util.BizUtils;
import com.edianniu.pscp.cs.util.DateUtils;
import com.edianniu.pscp.mis.bean.GetUserInfoResult;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.bean.worksheetreport.ReportType;
import com.edianniu.pscp.mis.bean.worksheetreport.vo.ReportListVO;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.mis.service.dubbo.WorkOrderDefectRecordInfoService;
import com.edianniu.pscp.mis.service.dubbo.WorkOrderReportInfoService;
import com.edianniu.pscp.sps.bean.project.DeleteResult;

import stc.skymobi.cache.redis.JedisUtil;


@Service
@Repository("roomInfoService")
public class RoomServiceInfoImpl implements RoomInfoService {
    private static final Logger logger = LoggerFactory.getLogger(RoomServiceInfoImpl.class);

    @Autowired
    @Qualifier("roomService")
    private RoomService roomService;
    
    @Autowired
    @Qualifier("companyCustomerService")
    private CompanyCustomerService companyCustomerService;

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;

    @Autowired
    @Qualifier("needsService")
    private NeedsService needsService;
    
    @Autowired
    @Qualifier("jedisUtil")
    private JedisUtil jedisUtil;
    
    @Autowired
    @Qualifier("operationRecordService")
    private OperationRecordService operationRecordService;
    
    @Autowired
    @Qualifier("equipmentService")
    private EquipmentService equipmentService;
    
    @Autowired
    @Qualifier("dutyLogService")
    private DutyLogService dutyLogService;
    
    @Autowired
    @Qualifier("firefightingEquipmentService")
    private FirefightingEquipmentService firefightingEquipmentService;
    
    @Autowired
    @Qualifier("safetyEquipmentService")
    private SafetyEquipmentService safetyEquipmentService;
    
    @Autowired
    @Qualifier("workOrderReportInfoService")
    private WorkOrderReportInfoService workOrderReportInfoService;
    
    @Autowired
    @Qualifier("workOrderDefectRecordInfoService")
    private WorkOrderDefectRecordInfoService workOrderDefectRecordInfoService;
    
    @Autowired
    @Qualifier("projectService")
    private ProjectService projectService;
    
    /**
     * 配电房列表
     */
    @Override
    public ListResult roomListResult(ListReqData listReqData) {
        ListResult result = new ListResult();
        try {
            GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(listReqData.getUid());
            if (!getUserInfoResult.isSuccess()) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("用户信息不存在");
                return result;
            }
            UserInfo userInfo = getUserInfoResult.getMemberInfo();
            if (null == userInfo) {
            	result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("用户信息不存在");
                return result;
			}
            ListQuery listQuery = new ListQuery();
            BeanUtils.copyProperties(listReqData, listQuery);
            listQuery.setCompanyId(userInfo.getCompanyId());
            String startTime = listReqData.getStartTime();
            if (StringUtils.isNotBlank(startTime)) {
            	if (!BizUtils.checkYmd(startTime)) {
            		result.setResultCode(ResultCode.ERROR_201);
                    result.setResultMessage("开始时间不合法");
                    return result;
				}
            	listQuery.setStartTime(DateUtils.parse(startTime));
			}
            String endTime = listReqData.getEndTime();
            if (StringUtils.isNotBlank(endTime)) {
            	if (!BizUtils.checkYmd(endTime)) {
            		result.setResultCode(ResultCode.ERROR_201);
                    result.setResultMessage("结束时间不合法");
                    return result;
				}
            	listQuery.setEndTime(DateUtils.parse(endTime));
			}

            PageResult<RoomVO> pageResult = roomService.selectRoomVOList(listQuery);
            result.setDistributionRooms(pageResult.getData());
            result.setHasNext(pageResult.isHasNext());
            result.setNextOffset(pageResult.getNextOffset());
            result.setTotalCount(pageResult.getTotal());
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("Room list:{}", e);
        }
        return result;
    }

    /**
     * 配电房新增、编辑
     */
    @Override
    public SaveResult save(Long uid, RoomInfo roomInfo) {
        SaveResult result = new SaveResult();
        try {
            if (null == uid) {
                result.set(ResultCode.ERROR_201, "uid不能为空");
                return result;
            }
            GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(uid);
            if (!getUserInfoResult.isSuccess()) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("用户信息不存在");
                return result;
            }
            UserInfo userInfo = getUserInfoResult.getMemberInfo();
            if (null == userInfo.getLoginName() || 0L == userInfo.getCompanyId()) {
                result.set(ResultCode.UNAUTHORIZED_ERROR, "请进行实名认证");
                return result;
            }
            if (!userInfo.isCustomer()) {
                result.set(ResultCode.UNAUTHORIZED_ERROR, "认证为客户后，才能添加配电房");
                return result;
            }
            String name = roomInfo.getName();
            if (!BizUtils.checkLength(name, 200)) {
            	result.set(ResultCode.ERROR_401, "配电房名称不能为空或超过200字");
                return result;
			}
            String director = roomInfo.getDirector();
            if (!BizUtils.checkLength(director, 10)) {
            	result.set(ResultCode.ERROR_402, "配电房负责人不能为空或超过10字");
                return result;
			}
            if (roomInfo.getContactNumber() == null) {
                result.set(ResultCode.ERROR_403, "联系电话不能为空");
                return result;
            }
            if (!BizUtils.checkMobile(roomInfo.getContactNumber())) {
                result.set(ResultCode.ERROR_403, "手机号码格式不正确");
                return result;
            }
            String address = roomInfo.getAddress();
            if (!BizUtils.checkLength(address, 512)) {
            	result.set(ResultCode.ERROR_404, "配电房地址不能为空或超过512字");
                return result;
			}
            roomInfo.setCompanyId(userInfo.getCompanyId());
            Long roomId = roomInfo.getId();
            //如果配电房ID为空，新增
            if (null == roomId) {
                roomInfo.setCreateUser(userInfo.getLoginName());
                roomInfo.setCreateTime(new Date());
                
                Long rs = jedisUtil.setnx(CacheKey.CACHE_KEY_USER_UID+uid, String.valueOf(uid), 200L);
                if (rs == 0) {
					result.set(ResultCode.ERROR_405, "操作进行中，请勿重复操作");
					return result;
				}
                roomService.saveRoom(roomInfo);
                jedisUtil.del(CacheKey.CACHE_KEY_USER_UID+uid);
            } else {//如果配电房ID不为空，编辑
                roomInfo.setModifiedUser(userInfo.getLoginName());
                roomInfo.setModifiedTime(new Date());
                
                Long rs = jedisUtil.setnx(CacheKey.CACHE_KEY_ROOM_ID+roomId, String.valueOf(roomId), 500L);
                if (rs == 0) {
					result.set(ResultCode.ERROR_405, "正在进行更改，请勿重复操作");
					return result;
				}
                roomService.update(roomInfo);
                jedisUtil.del(CacheKey.CACHE_KEY_ROOM_ID+roomId);
            }
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("saveOrUpdate :{}", e);
        }
        return result;
    }

    @Override
    public DeleteResult delete(Long id, Long uid) {
        DeleteResult result = new DeleteResult();
        try {
            if (uid == null) {
                result.set(ResultCode.ERROR_201, "uid不能为空");
                return result;
            }
            if (id == null) {
            	result.set(ResultCode.ERROR_201, "id不能为空");
                return result;
			}
            GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(uid);
            if (!getUserInfoResult.isSuccess()) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("用户信息不存在");
                return result;
            }
            UserInfo userInfo = getUserInfoResult.getMemberInfo();
            if (roomService.queryRoomVO(id) == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("配电房不存在，不可删除");
                return result;
            }
            // 判断是否有关联需求
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("companyId", getUserInfoResult.getMemberInfo().getCompanyId());
            List<Integer> queryStatus = new ArrayList<Integer>();
            queryStatus.add(NeedsStatus.AUDITING.getValue());
            queryStatus.add(NeedsStatus.RESPONDING.getValue());
            queryStatus.add(NeedsStatus.QUOTING.getValue());
            queryStatus.add(NeedsStatus.QUOTED.getValue());
            queryStatus.add(NeedsStatus.COOPETATED.getValue());
            map.put("queryStatus", queryStatus);
            List<NeedsVO> list = needsService.getList(map);
            if (CollectionUtils.isNotEmpty(list)) {
                for (NeedsVO needsVO : list) {
                    String distributionRooms = needsVO.getDistributionRooms();
                    if (StringUtils.isNotBlank(distributionRooms)) {
                        List<String> ids = Arrays.asList(distributionRooms.split(","));
                        if (ids.contains(id.toString())) {
                            result.set(ResultCode.ERROR_402, "该配电房还有关联需求，不可删除");
                            return result;
                        }
                    }
                }
            }
            // 判断是否有关联项目
            HashMap<String, Object> queryMap = new HashMap<>();
            queryMap.put("uid", uid);
            List<CompanyCustomer> companyCustomerList = companyCustomerService.getCompanyCustomers(queryMap);
            if (CollectionUtils.isNotEmpty(companyCustomerList)) {
            	HashMap<String, Object> queryProject = new HashMap<>();
            	List<Long> customerIds = new ArrayList<Long>();
				for (CompanyCustomer companyCustomer : companyCustomerList) {
					Long customerId = companyCustomer.getId();
					customerIds.add(customerId);
				}
				queryProject.put("customerIds", customerIds);
				List<Integer> projectStatus = new ArrayList<Integer>();
	            projectStatus.add(EngineeringProjectStatus.CONFIRMING.getValue());
	            projectStatus.add(EngineeringProjectStatus.ONGOING.getValue());
	            projectStatus.add(EngineeringProjectStatus.CONFIRM_COST.getValue());
	            projectStatus.add(EngineeringProjectStatus.SETTLED.getValue());
	            projectStatus.add(EngineeringProjectStatus.PAYFAILD.getValue());
	            queryProject.put("projectStatus", projectStatus);
	            List<EngineeringProjectVO> projectList = projectService.queryProjectList(queryProject);
	            if (CollectionUtils.isNotEmpty(projectList)) {
					for (EngineeringProjectVO projectVO : projectList) {
						String roomIds = projectVO.getRoomIds();
						if (StringUtils.isNotBlank(roomIds)) {
							List<String> ids = Arrays.asList(roomIds.split(","));
							if (ids.contains(id.toString())) {
								result.set(ResultCode.ERROR_402, "该配电房还有关联项目，不可删除");
	                            return result;
							}
						}
					}
				}
			}
            // 判断当前配电房是否存在关联设备和记录、报告
            Boolean exist = isExistReferringEquipmentAndRecord(id, userInfo.getCompanyId(), uid);
            if (exist) {
            	result.set(ResultCode.ERROR_402, "该配电房还有关联设备和记录，不可删除");
                return result;
			}
            Long rs = jedisUtil.setnx(CacheKey.CACHE_KEY_ROOM_ID+id, String.valueOf(id), 500L);
            if (rs == 0) {
				result.set(ResultCode.ERROR_403, "操作进行中，请勿重复操作");
				return result;
			}
            roomService.delete(id, userInfo.getLoginName());
            jedisUtil.del(CacheKey.CACHE_KEY_ROOM_ID+id);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("delete :{}", e);
        }
        return result;
    }

    @Override
    public RoomVO getRoomById(Long roomId) {
        return roomService.queryRoomVO(roomId);
    }
    
    /**
     * 判断当前配电房是否存在关联设备和记录、报告
     * @param roomId
     * @param companyId
     * @return
     */
    public Boolean isExistReferringEquipmentAndRecord(Long roomId, Long companyId, Long uid){
    	Boolean exist1 = equipmentService.isExistByRoomIdAndCompanyId(roomId, companyId);
    	Boolean exist2 = operationRecordService.isExistByRoomIdAndCompanyId(roomId, companyId);
    	Boolean exist3 = dutyLogService.isExistByRoomIdAndCompanyId(roomId, companyId);
    	Boolean exist4 = firefightingEquipmentService.isExistByRoomIdAndCompanyId(roomId, companyId);
    	Boolean exist5 = safetyEquipmentService.isExistByRoomIdAndCompanyId(roomId, companyId);
    	Boolean exist6 = isExistTestAndPatrolReport(roomId, uid, ReportType.REPAIR_TEST.getValue());
    	Boolean exist7 = isExistTestAndPatrolReport(roomId, uid, ReportType.PATROL.getValue());
    	Boolean exist8 = isExistDefectRecord(roomId, uid);
    	
    	Boolean exist = exist1 || exist2 || exist3 || exist4 || exist5 || exist6 || exist7 || exist8;
    	return exist;
    }
    
    /**
     * 判断当前配电房是否存在关联修试报告和巡视报告
     * @param roomId
     * @param uid
     * @param type
     * @return
     */
    public Boolean isExistTestAndPatrolReport(Long roomId, Long uid, Integer type){
    	com.edianniu.pscp.mis.bean.worksheetreport.ListReqData reqData = new com.edianniu.pscp.mis.bean.worksheetreport.ListReqData();
    	reqData.setRoomId(roomId);
    	reqData.setUid(uid);
    	reqData.setType(type);
		com.edianniu.pscp.mis.bean.worksheetreport.ListResult listResult = workOrderReportInfoService.pageListReport(reqData);
		List<ReportListVO> patrolRecordList = listResult.getPatrolRecordList();
		List<ReportListVO> repairTestRecordList = listResult.getRepairTestRecordList();
		boolean exist = CollectionUtils.isNotEmpty(patrolRecordList) || CollectionUtils.isNotEmpty(repairTestRecordList);
		
		return exist; 
    }
    
    /**
     * 判断当前配电房是否存在关联缺陷报告
     * @param roomId
     * @param uid
     * @return
     */
    public Boolean isExistDefectRecord(Long roomId, Long uid){
    	com.edianniu.pscp.mis.bean.defectrecord.ListReqData reqData = new com.edianniu.pscp.mis.bean.defectrecord.ListReqData();
    	reqData.setRoomId(roomId);
    	reqData.setUid(uid);
		com.edianniu.pscp.mis.bean.defectrecord.ListResult listResult = workOrderDefectRecordInfoService.pageListDefectRecord(reqData);
		long totalCount = listResult.getTotalCount();
		
    	return (totalCount > 0) ? true : false;
    }

    /**
     * 根据客户ID获取所有配电房
     * @param customerId
     * @param companyId
     * @return
     */
	@Override
	public RoomListResult getRoomsByCustomerId(Long customerId, Long companyId) {
		RoomListResult result = new RoomListResult();
		try {
            if (null == customerId) {
				result.set(ResultCode.ERROR_401, "customerId不能为空");
				return result;
			}
            if (null == companyId) {
				result.set(ResultCode.ERROR_401, "companyId不能为空");
				return result;
			}
            CompanyCustomer companyCustomer = companyCustomerService.getByCustomerId(customerId);
            if (null == companyCustomer) {
            	result.set(ResultCode.ERROR_402, "customerId不合法");
				return result;
			}
            if (null == companyCustomer.getCompanyId()) {
            	result.set(ResultCode.ERROR_402, "companyId不合法");
				return result;
			}
            if (! companyCustomer.getCompanyId().equals(companyId)) {
            	result.set(ResultCode.ERROR_402, "该服务商无此客户");
				return result;
			}
            
            List<RoomVO> roomList = roomService.getRoomListByCustomerId(customerId);
            result.setRoomList(roomList);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("saveOrUpdate :{}", e);
        }
        return result;
	}
	
	/**
	 * 根据RoomIds批量获取room信息
	 */
	@Override
	public RoomListResult getRoomsByIds(List<Long> ids){
		RoomListResult result = new RoomListResult();
		try {
           if (CollectionUtils.isEmpty(ids)) {
			result.set(ResultCode.ERROR_401, "IDs不能为空");
			return result;
           }
            List<RoomVO> roomList = roomService.getRoomsByIds(ids);
            result.setRoomList(roomList);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("saveOrUpdate :{}", e);
        }
        return result;
	}

}
