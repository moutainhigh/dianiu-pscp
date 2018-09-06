package com.edianniu.pscp.cs.service.dubbo.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.edianniu.pscp.cs.bean.CustomerEquipmentType;
import com.edianniu.pscp.cs.bean.firefightingequipment.vo.FirefightingEquipmentVO;
import com.edianniu.pscp.cs.bean.inspectinglog.ListReqData;
import com.edianniu.pscp.cs.bean.inspectinglog.ListResult;
import com.edianniu.pscp.cs.bean.inspectinglog.SaveReqData;
import com.edianniu.pscp.cs.bean.inspectinglog.SaveResult;
import com.edianniu.pscp.cs.bean.inspectinglog.vo.InspectingLogVO;
import com.edianniu.pscp.cs.bean.safetyequipment.vo.SafetyEquipmentVO;
import com.edianniu.pscp.cs.commons.Constants;
import com.edianniu.pscp.cs.commons.PageResult;
import com.edianniu.pscp.cs.commons.ResultCode;
import com.edianniu.pscp.cs.service.FirefightingEquipmentService;
import com.edianniu.pscp.cs.service.InspectingLogService;
import com.edianniu.pscp.cs.service.SafetyEquipmentService;
import com.edianniu.pscp.cs.service.dubbo.InspectingLogInfoService;
import com.edianniu.pscp.mis.bean.GetUserInfoResult;
import com.edianniu.pscp.mis.bean.company.CompanyInfo;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;

@Service
@Repository("inspectingLogInfoService")
public class InspectingLogInfoServiceImpl implements InspectingLogInfoService {
	private static final Logger logger = LoggerFactory.getLogger(InspectingLogInfoServiceImpl.class);
	
	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;
	
	@Autowired
	@Qualifier("inspectingLogService")
	private InspectingLogService inspectingLogService;
	
	@Autowired
	@Qualifier("safetyEquipmentService")
	private SafetyEquipmentService safetyEquipmentService;
	
	@Autowired
	@Qualifier("firefightingEquipmentService")
	private FirefightingEquipmentService firefightingEquipmentService;
	
	/**
	 * 保存客户设备检视日志
	 * @param saveReqData
	 * @return
	 */
	@Override
	public SaveResult saveInspectingLog(SaveReqData saveReqData){
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
                result.set(ResultCode.UNAUTHORIZED_ERROR, "认证为客户后，才能检查试验");
                return result;
            }
			Long equipmentId = saveReqData.getEquipmentId();
			if (null == equipmentId) {
				result.set(ResultCode.ERROR_401, "设备ID不为空");
				return result;
			}
			Integer type = saveReqData.getType();
			if (null == type) {
				result.set(ResultCode.ERROR_402, "设备类型不为空");
				return result;
			}
			if (! CustomerEquipmentType.isExist(type)) {
				result.set(ResultCode.ERROR_402, "设备类型不合法");
				return result;
			}
   			if (type.equals(CustomerEquipmentType.SAFETY_EQUIPMENT.getValue())) {
				SafetyEquipmentVO safetyEquipmentVO = safetyEquipmentService.getSafetyEquipmentDetails(equipmentId);
				if (null == safetyEquipmentVO ) {
					result.set(ResultCode.ERROR_403, "设备不存在");
					return result;
				}
				Integer clickStatus = safetyEquipmentVO.getClickStatus();
				if (! clickStatus.equals(Constants.TAG_YES)) {
					result.set(ResultCode.ERROR_403, "该设备还不在下一次试验的时间范围内");
					return result;
				}
			}
			if (type.equals(CustomerEquipmentType.FIREFIGHTING_EQUIPMENT.getValue())) {
				FirefightingEquipmentVO firefightingEquipmentVO = firefightingEquipmentService.getFirefightingEquipmentDetails(equipmentId);
				if (null == firefightingEquipmentVO) {
					result.set(ResultCode.ERROR_403, "设备不存在");
					return result;
				}
				Integer clickStatus = firefightingEquipmentVO.getClickStatus();
				if (! clickStatus.equals(Constants.TAG_YES)) {
					result.set(ResultCode.ERROR_403, "该设备还不在下一次检视的时间范围内");
					return result;
				}
			}
			String content = saveReqData.getContent();
			if (null != content && content.trim().length() > 512) {
				result.set(ResultCode.ERROR_404, "日志内容不能超过512个字");
				return result;
			}
			// 保存检视日志
			inspectingLogService.saveInspectingLog(saveReqData, userInfo);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("save:{}", e);
		}
		return result;
	}
	
	/**
	 * 客户设备检视日志列表
	 */
	@Override
	public ListResult getInspectingLogList(ListReqData listReqData){
		ListResult result= new ListResult();
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
            Long equipmentId = listReqData.getEquipmentId();
            if (null == equipmentId) {
				result.set(ResultCode.ERROR_401, "设备ID不为空");
				return result;
			}
            Integer type = listReqData.getType();
            if (null == type) {
				result.set(ResultCode.ERROR_402, "设备类型不为空");
				return result;
			}
            if (! CustomerEquipmentType.isExist(type)) {
				result.set(ResultCode.ERROR_402, "设备类型不合法");
				return result;
			}
            if (type.equals(CustomerEquipmentType.SAFETY_EQUIPMENT.getValue())) {
				SafetyEquipmentVO safetyEquipmentVO = safetyEquipmentService.getSafetyEquipmentDetails(equipmentId);
				if (null == safetyEquipmentVO ) {
					result.set(ResultCode.ERROR_403, "设备不存在");
					return result;
				}
			}
			if (type.equals(CustomerEquipmentType.FIREFIGHTING_EQUIPMENT.getValue())) {
				FirefightingEquipmentVO firefightingEquipmentVO = firefightingEquipmentService.getFirefightingEquipmentDetails(equipmentId);
				if (null == firefightingEquipmentVO) {
					result.set(ResultCode.ERROR_403, "设备不存在");
					return result;
				}
			}
			PageResult<InspectingLogVO> pageResult = inspectingLogService.getInspectingLogVOList(listReqData, userInfo);
			
			result.setLogs(pageResult.getData());
			result.setTotalCount(pageResult.getTotal());
			result.setHasNext(pageResult.isHasNext());
			result.setNextOffset(pageResult.getNextOffset());
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("list:{}", e);
		}
		
		return result;
	}
	
}
