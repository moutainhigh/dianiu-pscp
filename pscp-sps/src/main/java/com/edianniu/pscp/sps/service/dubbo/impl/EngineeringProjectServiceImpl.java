package com.edianniu.pscp.sps.service.dubbo.impl;

import com.edianniu.pscp.cs.bean.OrderIdPrefix;
import com.edianniu.pscp.cs.bean.room.vo.RoomVO;
import com.edianniu.pscp.cs.service.dubbo.RoomInfoService;
import com.edianniu.pscp.mis.bean.GetUserInfoResult;
import com.edianniu.pscp.mis.bean.pay.PayStatus;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.service.dubbo.FileUploadService;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.sps.bean.Attachment;
import com.edianniu.pscp.sps.bean.ProjectStatus;
import com.edianniu.pscp.sps.bean.project.*;
import com.edianniu.pscp.sps.bean.project.vo.ProjectVO;
import com.edianniu.pscp.sps.bean.workorder.chieforder.SelectDataQuery;
import com.edianniu.pscp.sps.bean.workorder.chieforder.WorkOrderStatus;
import com.edianniu.pscp.sps.commons.CacheKey;
import com.edianniu.pscp.sps.commons.ResultCode;
import com.edianniu.pscp.sps.domain.CompanyCustomer;
import com.edianniu.pscp.sps.service.EngineeringProjectService;
import com.edianniu.pscp.sps.service.SpsCompanyCustomerService;
import com.edianniu.pscp.sps.service.WorkOrderService;
import com.edianniu.pscp.sps.service.dubbo.EngineeringProjectInfoService;
import com.edianniu.pscp.sps.util.BizUtils;
import com.edianniu.pscp.sps.util.DateUtils;
import com.edianniu.pscp.sps.util.MoneyUtils;
import stc.skymobi.cache.redis.JedisUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ClassName: EngineeringProjectServiceImpl Author: tandingbo CreateTime:
 * 2017-05-16 09:33
 */
@Service
@Repository("engineeringProjectInfoService")
public class EngineeringProjectServiceImpl implements EngineeringProjectInfoService {
	private static final Logger logger = LoggerFactory.getLogger(EngineeringProjectServiceImpl.class);
	@Autowired
	@Qualifier("workOrderService")
	private WorkOrderService workOrderService;
	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;
	@Autowired
	@Qualifier("engineeringProjectService")
	private EngineeringProjectService engineeringProjectService;
	@Autowired
	@Qualifier("spsCompanyCustomerService")
	private SpsCompanyCustomerService spsCompanyCustomerService;
	@Autowired
	@Qualifier("fileUploadService")
	private FileUploadService fileUploadService;
	@Autowired
	@Qualifier("jedisUtil")
	private JedisUtil jedisUtil;
	@Autowired
	@Qualifier("roomInfoService")
	private RoomInfoService roomInfoService;

	@Override
	public ListProjectsResult getListByCompanyIdAndCustomerId(ListProjectsReqData reqData) {
		ListProjectsResult result = new ListProjectsResult();
		try {
			if (reqData.getCustomerId() == null) {
				result.setResultCode(ResultCode.ERROR_401);
				result.setResultMessage("customerId不能为空");
				return result;
			}
			if (reqData.getUid() == null) {
				result.setResultCode(ResultCode.ERROR_401);
				result.setResultMessage("uid不能为空");
				return result;
			}

			GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(reqData.getUid());
			if (!getUserInfoResult.isSuccess()) {
				result.setResultCode(ResultCode.ERROR_401);
				result.setResultMessage("用户信息不存在");
				return result;
			}
			UserInfo userInfo = getUserInfoResult.getMemberInfo();
			reqData.setCompanyId(userInfo.getCompanyId());

			List<ProjectVO> projectList = engineeringProjectService.selectProjectVOByCompanyIdAndAndCustomerId(
					reqData.getCompanyId(), reqData.getCustomerId(), reqData.isIncludeExpire());
			result.setProjectList(projectList);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
			result.setResultMessage("系统异常");
			logger.error("getProjectListByCompanyIdAndCustomerId :{}", e);
		}
		return result;
	}

	@Override
	public ProjectInfo getById(Long id) {
		return engineeringProjectService.getById(id);
	}

	@Override
	public List<ProjectInfo> queryList(Map<String, Object> map) {
		return engineeringProjectService.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return engineeringProjectService.queryTotal(map);
	}

	@Override
	public SaveResult save(Long uid, ProjectInfo projectInfo) {

		SaveResult result = new SaveResult();
		try {
			if (uid == null) {
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
			if (projectInfo.getCustomerId() == null) {
				result.set(ResultCode.ERROR_202, "客户不能为空");
				return result;
			}
			CompanyCustomer customer = spsCompanyCustomerService.getById(projectInfo.getCustomerId());
			if (customer == null) {
				result.set(ResultCode.ERROR_202, "客户不存在");
				return result;
			}
			if (!customer.getCompanyId().equals(userInfo.getCompanyId())) {
				result.set(ResultCode.ERROR_202, "客户不存在");
				return result;
			}
			String roomIds = projectInfo.getRoomIds();
            if (!BizUtils.checkLength(roomIds, 200)) {
                result.set(ResultCode.ERROR_403, "配电房不能为空或超过200字");
                return result;
            }
            String[] ids = roomIds.trim().split(",");
            if (ArrayUtils.isEmpty(ids)) {
                result.set(ResultCode.ERROR_403, "配电房不能为空");
                return result;
            } else {
                for (String id : ids) {
                    long roomId = Long.parseLong(id);
                    RoomVO roomVO = roomInfoService.getRoomById(roomId);
                    if (null == roomVO) {
                        result.set(ResultCode.ERROR_403, roomId + "配电房不存在");
                        return result;
                    }
                }
            }
			if (StringUtils.isBlank(projectInfo.getName())) {
				result.set(ResultCode.ERROR_204, "项目名称不能为空");
				return result;
			}
			if (engineeringProjectService.existByCompanyIdAndName(userInfo.getCompanyId(),
					projectInfo.getName().trim())) {
				result.set(ResultCode.ERROR_204, "项目名称重复");
				return result;
			}
			Date createTime = DateUtils.parse(projectInfo.getStartTimeConvert(), DateUtils.DATE_PATTERN);
			Date endTime = DateUtils.parse(projectInfo.getEndTimeConvert(), DateUtils.DATE_PATTERN);
			if (null == createTime || null == endTime) {
				result.set(ResultCode.ERROR_204, "时间不合法");
				return result;
			}
			if (createTime.after(endTime)) {
				result.set(ResultCode.ERROR_204, "时间不合法");
				return result;
			}
			endTime = DateUtils.getEndDate(endTime);
			projectInfo.setEndTime(endTime);
			String projectNo = BizUtils.getOrderId(OrderIdPrefix.PROJECTNO_PREFIX);
			projectInfo.setProjectNo(projectNo);
			projectInfo.setSceneInvestigation(0);
			projectInfo.setCompanyId(customer.getCompanyId());
			projectInfo.setCreateUser(userInfo.getLoginName());
			projectInfo.setCreateTime(new Date());
			
			Long rs = jedisUtil.setnx(CacheKey.CACHE_KEY_USER_UID+uid, String.valueOf(uid), 200L);
			if (rs == 0) {
				result.set(ResultCode.ERROR_401, "操作进行中");
				return result;
			}
			engineeringProjectService.save(projectInfo, userInfo);
			jedisUtil.del(CacheKey.CACHE_KEY_USER_UID+uid);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
			result.setResultMessage("系统异常");
			logger.error("save :{}", e);
		}
		return result;
	}

	@Override
	public UpdateResult update(Long uid, ProjectInfo projectInfo) {
		UpdateResult result = new UpdateResult();
		try {
			if (uid == null) {
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
			Long projectId = projectInfo.getId();
			if (projectId == null) {
				result.setResultCode(ResultCode.ERROR_202);
				result.setResultMessage("项目ID不能为空");
				return result;
			}
			if (projectInfo.getCustomerId() == null) {
				result.set(ResultCode.ERROR_203, "客户不能为空");
				return result;
			}
			CompanyCustomer customer = spsCompanyCustomerService.getById(projectInfo.getCustomerId());
			if (customer == null) {
				result.set(ResultCode.ERROR_203, "客户不存在");
				return result;
			}
			if (!customer.getCompanyId().equals(userInfo.getCompanyId())) {
				result.set(ResultCode.ERROR_203, "客户不存在");
				return result;
			}
			String roomIds = projectInfo.getRoomIds();
            if (!BizUtils.checkLength(roomIds, 200)) {
                result.set(ResultCode.ERROR_203, "配电房不能为空或超过200字");
                return result;
            }
            String[] ids = roomIds.trim().split(",");
            if (ArrayUtils.isEmpty(ids)) {
                result.set(ResultCode.ERROR_203, "配电房不能为空");
                return result;
            } else {
                for (String id : ids) {
                    long roomId = Long.parseLong(id);
                    RoomVO roomVO = roomInfoService.getRoomById(roomId);
                    if (null == roomVO) {
                        result.set(ResultCode.ERROR_203, roomId + "配电房不存在");
                        return result;
                    }
                }
            }
			ProjectInfo project = engineeringProjectService.getById(projectId);
			if (project == null) {
				result.setResultCode(ResultCode.ERROR_202);
				result.setResultMessage("项目不存在");
				return result;
			}
			if (projectInfo.getCompanyId() == null) {
				result.setResultCode(ResultCode.ERROR_202);
				result.setResultMessage("项目不存在");
				return result;
			}
			if (!project.getCompanyId().equals(userInfo.getCompanyId())) {
				result.setResultCode(ResultCode.ERROR_202);
				result.setResultMessage("项目不存在");
				return result;
			}
			if (!projectInfo.getCompanyId().equals(project.getCompanyId())) {
				result.setResultCode(ResultCode.ERROR_202);
				result.setResultMessage("项目不存在");
				return result;
			}
			if (StringUtils.isBlank(projectInfo.getName())) {
				result.set(ResultCode.ERROR_203, "项目名称不能为空");
				return result;
			}
			if (engineeringProjectService.existByCompanyIdAndIdAndName(userInfo.getCompanyId(), projectId,
					projectInfo.getName().trim())) {
				result.set(ResultCode.ERROR_204, "项目名称重复");
				return result;
			}
			/*
			 * if (project.getSceneInvestigation().intValue() != 0 &&
			 * projectInfo.getSceneInvestigation().intValue() == 0) {
			 * result.set(ResultCode.ERROR_205, "勘查状态不能修改"); return result; }
			 */
			if (project.getStatus() == ProjectStatus.ONGOING.getValue()
					&& projectInfo.getStatus() == ProjectStatus.CONFIRMING.getValue()) {
				result.set(ResultCode.ERROR_206, "项目进行中，状态不可更改");
				return result;
			}
			Date createTime = DateUtils.parse(projectInfo.getStartTimeConvert(), DateUtils.DATE_PATTERN);
			Date endTime = DateUtils.parse(projectInfo.getEndTimeConvert(), DateUtils.DATE_PATTERN);
			if (null == createTime || null == endTime) {
				result.set(ResultCode.ERROR_207, "时间不合法");
				return result;
			}
			if (createTime.after(endTime)) {
				result.set(ResultCode.ERROR_207, "时间不合法");
				return result;
			}
			endTime = DateUtils.getEndDate(endTime);
			projectInfo.setEndTime(endTime);
			projectInfo.setModifiedUser(userInfo.getLoginName());
			projectInfo.setModifiedTime(new Date());
			
			Long rs = jedisUtil.setnx(CacheKey.CACHE_KEY_PROJECT_ID+projectId, String.valueOf(projectId), 500L);
			if (rs == 0) {
				result.set(ResultCode.ERROR_208, "操作进行中，请勿重复操作");
				return result;
			}
			engineeringProjectService.update(projectInfo, userInfo);
			jedisUtil.del(CacheKey.CACHE_KEY_PROJECT_ID+projectId);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
			result.setResultMessage("系统异常");
			logger.error("update :{}", e);
		}
		return result;
	}

	/**
	 * 项目结算（门户使用，需求转的项目）
	 * 项目结束（门户使用，服务商自建的项目）
	 * @param uid
	 * @param projectInfo
	 * @return
	 */
	public SettleResult settle(Long uid, ProjectInfo projectInfo) {
		SettleResult result = new SettleResult();
		try {
			if (uid == null) {
				result.set(ResultCode.ERROR_201, "uid不能为空");
				return result;
			}
			GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(uid);
			if (!getUserInfoResult.isSuccess()) {
				result.set(getUserInfoResult.getResultCode(), getUserInfoResult.getResultMessage());
				return result;
			}
			UserInfo userInfo = getUserInfoResult.getMemberInfo();
			Long projectId = projectInfo.getId();
			ProjectInfo project = engineeringProjectService.getById(projectId);
			if (project == null) {
				result.set(ResultCode.ERROR_201, "项目不存在");
				return result;
			}
			// 如有该项目还有工单在未确认、已确认、进行中、未评价状态时，不可结算(结束)
			SelectDataQuery selectDataQuery = new SelectDataQuery();
			Integer[] status = { WorkOrderStatus.UNCONFIRMED.getValue(), WorkOrderStatus.CONFIRMED.getValue(),
					WorkOrderStatus.EXECUTING.getValue(), WorkOrderStatus.UN_EVALUATE.getValue() };
			selectDataQuery.setStatus(status);
			selectDataQuery.setProjectId(projectId);
			int count = workOrderService.querySelectDataCount(selectDataQuery);
			if (count > 0) {
				result.set(ResultCode.ERROR_202, "该项目还有工单尚未结束");
				return result;
			}
			
			// 由需求转换的的项目，可结算，状态改为费用待确认
			if (null != project.getNeedsId()) {
				// 如果项目的支付状态在支付确认中和支付成功是,结算金额和附件将不能再次编辑
				Integer payStatus = project.getPayStatus();
				if (payStatus.equals(PayStatus.INPROCESSING.getValue()) ||
						payStatus.equals(PayStatus.SUCCESS.getValue())) {
					// 控制结算金额
					Double oldAmount = project.getActualSettlementAmount();
					Double newAmount = projectInfo.getActualSettlementAmount();
					boolean equals = MoneyUtils.equals(oldAmount, newAmount);
					if (!equals) {
						result.set(ResultCode.ERROR_203, "客户已确认结算金额，项目已进入支付阶段，结算金额不可编辑");
						return result;
					}
					// 控制结算附件
					String removedImgs = projectInfo.getRemovedImgs();
					List<Attachment> newAddAttachmentList = projectInfo.getActualPriceAttachmentList();
					if (CollectionUtils.isNotEmpty(newAddAttachmentList) || 
							StringUtils.isNotBlank(removedImgs)) {
						result.set(ResultCode.ERROR_203, "客户已确认结算金额，项目已进入支付阶段，结算附件不可编辑");
						return result;
					}
				}
				projectInfo.setStatus(ProjectStatus.CONFIRM_COST.getValue());
			}
			// 服务商自建的项目，只能结束，状态直接改为已结束
			if (null == project.getNeedsId()) {
				projectInfo.setStatus(ProjectStatus.SETTLED.getValue());
			}
			projectInfo.setModifiedUser(userInfo.getLoginName());
			projectInfo.setModifiedTime(new Date());
			Long rs = jedisUtil.setnx(CacheKey.CACHE_KEY_PROJECT_ID+projectId, String.valueOf(projectId), 500L);
			if (rs == 0) {
				result.set(ResultCode.ERROR_208, "操作进行中，请勿重复操作");
				return result;
			}
			engineeringProjectService.settle(projectInfo, userInfo);
			jedisUtil.del(CacheKey.CACHE_KEY_PROJECT_ID+projectId);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
			result.setResultMessage("系统异常");
			logger.error("settle :{}", e);
		}
		return result;
	}

	@Override
	public DeleteResult delete(Long uid, Long id) {
		DeleteResult result = new DeleteResult();
		try {
			GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(uid);
			if (!getUserInfoResult.isSuccess()) {
				result.set(getUserInfoResult.getResultCode(), getUserInfoResult.getResultMessage());
				return result;
			}
			UserInfo userInfo = getUserInfoResult.getMemberInfo();
			ProjectInfo project = engineeringProjectService.getById(id);
			if (project == null) {
				result.set(ResultCode.ERROR_201, "项目不存在");
				return result;
			}
			if (!userInfo.getCompanyId().equals(project.getCompanyId())) {
				result.set(ResultCode.ERROR_202, "没有权限删除:" + project.getName());
				return result;
			}
			if (workOrderService.existByProjectId(id)) {
				result.set(ResultCode.ERROR_203, "项目(" + project.getName() + "):有工单关联，无法删除");
				return result;
			}
			Long rs = jedisUtil.setnx(CacheKey.CACHE_KEY_PROJECT_ID+id, String.valueOf(id), 500L);
			if (rs == 0) {
				result.set(ResultCode.ERROR_208, "操作进行中，请勿重复操作");
				return result;
			}
			engineeringProjectService.delete(id);
			jedisUtil.del(CacheKey.CACHE_KEY_PROJECT_ID+id);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
			result.setResultMessage("系统异常");
			logger.error("delete :{}", e);
		}
		return result;

	}

	@Override
	public DeleteResult deleteBatch(Long uid, Long[] ids) {
		DeleteResult result = new DeleteResult();
		try {
			GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(uid);
			if (!getUserInfoResult.isSuccess()) {
				result.set(getUserInfoResult.getResultCode(), getUserInfoResult.getResultMessage());
				return result;
			}
			UserInfo userInfo = getUserInfoResult.getMemberInfo();
			for (Long id : ids) {
				ProjectInfo project = engineeringProjectService.getById(id);
				if (project == null) {
					result.set(ResultCode.ERROR_201, "项目不存在");
					return result;
				}
				if (!userInfo.getCompanyId().equals(project.getCompanyId())) {
					result.set(ResultCode.ERROR_202, "没有权限删除:" + project.getName());
					return result;
				}
				if (workOrderService.existByProjectId(id)) {
					result.set(ResultCode.ERROR_203, "项目(" + project.getName() + "):有工单关联，无法删除");
					return result;
				}
			}
			engineeringProjectService.deleteBatch(ids);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
			result.setResultMessage("系统异常");
			logger.error("deleteBatch :{}", e);
		}
		return result;

	}

}
