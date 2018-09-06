package com.edianniu.pscp.cs.service.dubbo.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.edianniu.pscp.cs.bean.operationrecord.DeleteReqData;
import com.edianniu.pscp.cs.bean.operationrecord.DeleteResult;
import com.edianniu.pscp.cs.bean.operationrecord.DetailsReqData;
import com.edianniu.pscp.cs.bean.operationrecord.DetailsResult;
import com.edianniu.pscp.cs.bean.operationrecord.ListReqData;
import com.edianniu.pscp.cs.bean.operationrecord.ListResult;
import com.edianniu.pscp.cs.bean.operationrecord.SaveReqData;
import com.edianniu.pscp.cs.bean.operationrecord.SaveResult;
import com.edianniu.pscp.cs.bean.operationrecord.vo.OperationRecordVO;
import com.edianniu.pscp.cs.bean.room.vo.RoomVO;
import com.edianniu.pscp.cs.commons.PageResult;
import com.edianniu.pscp.cs.commons.ResultCode;
import com.edianniu.pscp.cs.service.OperationRecordService;
import com.edianniu.pscp.cs.service.RoomService;
import com.edianniu.pscp.cs.service.dubbo.OperationRecordInfoService;
import com.edianniu.pscp.cs.util.BizUtils;
import com.edianniu.pscp.cs.util.DateUtils;
import com.edianniu.pscp.mis.bean.GetUserInfoResult;
import com.edianniu.pscp.mis.bean.company.CompanyInfo;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;

@Service
@Repository("operationRecordInfoService")
public class OperationRecordInfoServiceImpl implements OperationRecordInfoService {

	private static final Logger logger = LoggerFactory.getLogger(OperationRecordInfoServiceImpl.class);
	
	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;
	
	@Autowired
	@Qualifier("roomService")
	private RoomService roomService;
	
	@Autowired
	@Qualifier("operationRecordService")
	private OperationRecordService operationRecordService;
	
	
	/**
	 * 新增和编辑配电房操作记录
	 */
	@Override
	public SaveResult saveOperationRecord(SaveReqData saveReqData) {
		SaveResult result = new SaveResult();
		try {
			Long uid = saveReqData.getUid();
			if (null == uid) {
				result.set(ResultCode.ERROR_201, "uid不能为空");
				return result;
			}
			GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(uid);
			if (!getUserInfoResult.isSuccess()) {
				result.set(ResultCode.ERROR_201, "用户信息不存在");
				return result;
			}
			UserInfo userInfo = getUserInfoResult.getMemberInfo();
			if (null == userInfo) {
				result.set(ResultCode.ERROR_201, "用户信息不存在");
				return result;
			}
			CompanyInfo companyInfo = getUserInfoResult.getCompanyInfo();
			if (null == companyInfo) {
				result.set(ResultCode.ERROR_201, "用户公司信息不存在");
				return result;
			}
			if (null == userInfo.getLoginName() || 0L == userInfo.getCompanyId()) {
                result.set(ResultCode.UNAUTHORIZED_ERROR, "请进行实名认证");
                return result;
            }
            if (!userInfo.isCustomer()) {
                result.set(ResultCode.UNAUTHORIZED_ERROR, "认证为客户后，才能为配电房添加操作记录");
                return result;
            }
			Long id = saveReqData.getId();
			if (null != id) {
				OperationRecordVO operationRecordVO = operationRecordService.getOperationRecordVO(id);
				if (null == operationRecordVO) {
					result.set(ResultCode.ERROR_401, "要编辑的记录不存在");
					return result;
				}
			}
			if (null == saveReqData.getRoomId()) {
				result.set(ResultCode.ERROR_401, "需要关联配电房");
				return result;
			}
			RoomVO roomVO = roomService.queryRoomVO(saveReqData.getRoomId());
			if (null == roomVO ) {
				result.set(ResultCode.ERROR_401, "关联的配电房不存在");
				return result;
			}
			String equipmentTask = saveReqData.getEquipmentTask();
			if (! BizUtils.checkLength(equipmentTask, 200)) {
				result.set(ResultCode.ERROR_402, "设备任务不能为空或超过200个字");
				return result;
			}
			String groundLeadNum = saveReqData.getGroundLeadNum();
			if (! BizUtils.checkLength(groundLeadNum, 30)) {
				result.set(ResultCode.ERROR_403, "接地线编号不能为空或超过30个字");
				return result;
			}
			if (StringUtils.isBlank(saveReqData.getStartTime())) {
				result.set(ResultCode.ERROR_404, "操作开始时间不能为空");
				return result;
			}
			Date startTime = DateUtils.parse(saveReqData.getStartTime(), DateUtils.DATE_PATTERN);
			if (null == startTime) {
				result.set(ResultCode.ERROR_404, "操作开始时间不合法");
				return result;
			}
			if (StringUtils.isBlank(saveReqData.getEndTime())) {
				result.set(ResultCode.ERROR_404, "操作结束时间不能为空");
				return result;
			}
			Date endTime = DateUtils.parse(saveReqData.getEndTime(), DateUtils.DATE_PATTERN);
			if (null == endTime) {
				result.set(ResultCode.ERROR_404, "操作结束时间不合法");
				return result;
			}
			String issuingCommand = saveReqData.getIssuingCommand();
			if (! BizUtils.checkLength(issuingCommand, 10)) {
				result.set(ResultCode.ERROR_405, "发令人不能为空或超过10个字");
				return result;
			}
			String receiveCommand = saveReqData.getReceiveCommand();
			if (! BizUtils.checkLength(receiveCommand, 10)) {
				result.set(ResultCode.ERROR_405, "接令人不能为空或超过10个字");
				return result;
			}
			String operator = saveReqData.getOperator();
			if (! BizUtils.checkLength(operator, 10)) {
				result.set(ResultCode.ERROR_405, "操作人不能为空或超过10个字");
				return result;
			}
			String guardian = saveReqData.getGuardian();
			if (! BizUtils.checkLength(guardian, 10)) {
				result.set(ResultCode.ERROR_405, "监护人不能为空或超过10个字");
				return result;
			}
			String remark = saveReqData.getRemark();
			if (StringUtils.isNotBlank(remark) && remark.trim().length() > 1000) {
				result.set(ResultCode.ERROR_406, "备注不能超过1000个字");
				return result;
			}
			// 保存操作记录信息
			operationRecordService.save(saveReqData, userInfo);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("save:{}", e);
		}
		return result;
	}
	
	/**
	 * 配电房操作记录列表
	 */
	@Override
	public ListResult operationRecordList(ListReqData listReqData){
		ListResult result = new ListResult();
		try {
			if (null == listReqData.getUid()) {
				result.set(ResultCode.ERROR_201, "uid不能为空");
				return result;
			}
			GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(listReqData.getUid());
			if (!getUserInfoResult.isSuccess()) {
				result.set(ResultCode.ERROR_201, "用户信息不存在");
				return result;
			}
			UserInfo userInfo = getUserInfoResult.getMemberInfo();
            if (null == userInfo) {
				result.set(ResultCode.ERROR_201, "用户信息不存在");
				return result;
			}
			Long roomId = listReqData.getRoomId();
			if (roomId != null && roomId != -1) {  //如果等于-1或null，则查询所有配电房的操作记录
				RoomVO roomVO = roomService.queryRoomVO(roomId);
				if (null == roomVO) {
					result.set(ResultCode.ERROR_402, "关联的配电房不存在");
					return result;
				}
			}
			PageResult<OperationRecordVO> pageResult = operationRecordService.getOperationRecordVOList(listReqData, userInfo);
			
			result.setOperations(pageResult.getData());
			result.setHasNext(pageResult.isHasNext());
			result.setNextOffset(pageResult.getNextOffset());
			result.setTotalCount(pageResult.getTotal());
			
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("list:{}", e);
		}
		return result;
	}
	
	/**
	 * 操作记录详情
	 */
	@Override
	public DetailsResult getOperationRecordDetails(DetailsReqData detailsReqData){
		DetailsResult result = new DetailsResult();
		try {
			if (null == detailsReqData.getUid()) {
                result.set(ResultCode.ERROR_201, "uid不能为空");
                return result;
            }
			GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(detailsReqData.getUid());
			if (!getUserInfoResult.isSuccess()) {
				result.set(ResultCode.ERROR_201, "用户信息不存在");
				return result;
			}
			Long id = detailsReqData.getId();
			if (null == id) {
				result.set(ResultCode.ERROR_401, "记录ID不能为空");
				return result;
			}
			OperationRecordVO operationRecordVO = operationRecordService.getOperationRecordVO(id);
			result.setOperation(operationRecordVO);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("details:{}", e);
		}
		return result;
	}
	
	/**
	 * 配电房操作记录删除
	 */
	@Override
	public DeleteResult deleteOperationRecord(DeleteReqData deleteReqData){
		DeleteResult result = new DeleteResult();
		try {
			if (null == deleteReqData.getUid()) {
                result.set(ResultCode.ERROR_201, "uid不能为空");
                return result;
            }
			GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(deleteReqData.getUid());
			if (!getUserInfoResult.isSuccess()) {
				result.set(ResultCode.ERROR_201, "用户信息不存在");
				return result;
			}
			UserInfo userInfo = getUserInfoResult.getMemberInfo();
			if (null == userInfo) {
				result.set(ResultCode.ERROR_201, "用户信息不存在");
				return result;
			}
			Long id = deleteReqData.getId();
			if (null == id) {
				result.set(ResultCode.ERROR_401, "记录ID不能为空");
				return result;
			}
			OperationRecordVO operationRecordVO = operationRecordService.getOperationRecordVO(id);
			if (null == operationRecordVO) {
				result.set(ResultCode.ERROR_402, "要删除的记录不存在");
				return result;
			}
			operationRecordService.deleteOperationRecord(id, userInfo);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("delete:{}", e);
		}
		return result;
	}
	
	
	

}
