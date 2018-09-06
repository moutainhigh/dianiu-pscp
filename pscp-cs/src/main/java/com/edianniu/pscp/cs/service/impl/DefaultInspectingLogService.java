package com.edianniu.pscp.cs.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.edianniu.pscp.cs.bean.CustomerEquipmentType;
import com.edianniu.pscp.cs.bean.firefightingequipment.FirefightingEquipmentInfo;
import com.edianniu.pscp.cs.bean.inspectinglog.InspectingLogInfo;
import com.edianniu.pscp.cs.bean.inspectinglog.ListReqData;
import com.edianniu.pscp.cs.bean.inspectinglog.SaveReqData;
import com.edianniu.pscp.cs.bean.inspectinglog.vo.InspectingLogVO;
import com.edianniu.pscp.cs.bean.safetyequipment.SafetyEquipmentInfo;
import com.edianniu.pscp.cs.commons.Constants;
import com.edianniu.pscp.cs.commons.PageResult;
import com.edianniu.pscp.cs.dao.FirefightingEquipmentDao;
import com.edianniu.pscp.cs.dao.InspectingLogDao;
import com.edianniu.pscp.cs.dao.SafetyEquipmentDao;
import com.edianniu.pscp.cs.service.InspectingLogService;
import com.edianniu.pscp.cs.util.DateUtils;
import com.edianniu.pscp.mis.bean.user.UserInfo;

@Service
@Transactional
@Repository("inspectingLogService")
public class DefaultInspectingLogService implements InspectingLogService {

	private static final Logger logger = LoggerFactory.getLogger(DefaultInspectingLogService.class);
	
	@Autowired
	private InspectingLogDao inspectingLogDao;
	
	@Autowired
	private SafetyEquipmentDao safetyEquipmentDao;
	
	@Autowired
	private FirefightingEquipmentDao firefightingEquipmentDao;

	/**
	 * 保存检视日志
	 */
	@Override
	public void saveInspectingLog(SaveReqData saveReqData, UserInfo userInfo) {
		if (null == saveReqData || null == userInfo) {
			logger.error("参数不合法");
			return;
		}
		Date today = new Date();
		InspectingLogInfo logInfo = new InspectingLogInfo();
		Long equipmentId = saveReqData.getEquipmentId();
		Integer type = saveReqData.getType();
		logInfo.setEquipmentId(equipmentId);
		logInfo.setType(type);
		logInfo.setCompanyId(userInfo.getCompanyId());
		logInfo.setStatus(Constants.TAG_YES);
		logInfo.setContent(saveReqData.getContent());
		logInfo.setCreateUser(userInfo.getLoginName());
		logInfo.setCreateTime(today);
		logInfo.setActualTestTime(today);
		
		// 更新设备的下一次检视时间
		if (type.equals(CustomerEquipmentType.SAFETY_EQUIPMENT.getValue())) {
			SafetyEquipmentInfo equipmentInfo = safetyEquipmentDao.getSafetyEquipmentInfo(equipmentId);
			if (null != equipmentInfo) {
				Date nextTestDate = equipmentInfo.getNextTestDate();
				// 设置设备的固定检查时间，即设备的下一次检查时间
				logInfo.setFixTestTime(nextTestDate);
				// 设置设备最新的下一次检查时间
				nextTestDate = DateUtils.dateAddTime(equipmentInfo.getNextTestDate(), equipmentInfo.getTestCycle(), equipmentInfo.getTestTimeUnit());
				if (nextTestDate != null) {
					equipmentInfo.setNextTestDate(nextTestDate);
				}
				equipmentInfo.setModifiedTime(today);
				equipmentInfo.setModifiedUser(userInfo.getLoginName());
			}
			safetyEquipmentDao.update(equipmentInfo);
		}
		if (type.equals(CustomerEquipmentType.FIREFIGHTING_EQUIPMENT.getValue())) {
			FirefightingEquipmentInfo equipmentInfo = firefightingEquipmentDao.getFirefightingEquipmentInfo(equipmentId);
			if (null != equipmentInfo) {
				Date nextTestDate = equipmentInfo.getNextTestDate();
				// 设置设备的固定检查时间，即设备的下一次检查时间
				logInfo.setFixTestTime(nextTestDate);
				// 设置设备最新的下一次检查时间
				nextTestDate = DateUtils.dateAddTime(equipmentInfo.getNextTestDate(), equipmentInfo.getViewCycle(), equipmentInfo.getViewTimeUnit());
				if (nextTestDate != null) {
					equipmentInfo.setNextTestDate(nextTestDate);
				}
				equipmentInfo.setModifiedTime(today);
				equipmentInfo.setModifiedUser(userInfo.getLoginName());
			}
			firefightingEquipmentDao.update(equipmentInfo);
		}
		
		// 插入一条日志信息到客户设备检视日志表
		inspectingLogDao.save(logInfo);
	}

	/**
	 * 日志列表
	 */
	@Override
	public PageResult<InspectingLogVO> getInspectingLogVOList(ListReqData listReqData, UserInfo userInfo) {
		PageResult<InspectingLogVO> result = new PageResult<>();
		
		HashMap<String, Object> queryMap = new HashMap<>();
		queryMap.put("pageSize", listReqData.getPageSize());
		queryMap.put("offset", listReqData.getOffset());
		queryMap.put("equipmentId", listReqData.getEquipmentId());
		queryMap.put("type", listReqData.getType());
		
		int total = inspectingLogDao.getInspectingLogVOListCount(queryMap);
		int nextOffset = 0;
		
		if (total > 0) {
			List<InspectingLogVO> inspectingLogVOList = new ArrayList<>();
			List<InspectingLogInfo> entityList = inspectingLogDao.getInspectingLogVOList(queryMap);
			if (CollectionUtils.isNotEmpty(entityList)) {
				for (InspectingLogInfo inspectingLogInfo : entityList) {
					InspectingLogVO inspectingLogVO = new InspectingLogVO();
					inspectingLogVO.setId(inspectingLogInfo.getId());
					inspectingLogVO.setType(inspectingLogInfo.getType());
					inspectingLogVO.setStatus(inspectingLogInfo.getStatus());
					
					String historyFixCheckDate = DateUtils.format(inspectingLogInfo.getFixTestTime(), DateUtils.DATE_PATTERN);
					String historyActualCheckDate = DateUtils.format(inspectingLogInfo.getActualTestTime(), DateUtils.DATE_PATTERN);
					if (null != historyFixCheckDate) {
						inspectingLogVO.setHistoryFixCheckDate(historyFixCheckDate);
					}
					if (null != historyActualCheckDate) {
						inspectingLogVO.setHistoryActualCheckDate(historyActualCheckDate);
					}
					
					inspectingLogVO.setContent(inspectingLogInfo.getContent());
					inspectingLogVOList.add(inspectingLogVO);
				}
			}
			result.setData(inspectingLogVOList);
			nextOffset = listReqData.getOffset() + entityList.size();
			result.setNextOffset(nextOffset);
		}
		if (nextOffset > 0 && nextOffset < total) {
			result.setHasNext(true);
		}
		result.setOffset(listReqData.getOffset());
		result.setNextOffset(nextOffset);
		result.setTotal(total);
		
		return result;
	}
	
}
