package com.edianniu.pscp.cs.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edianniu.pscp.cs.bean.CustomerEquipmentType;
import com.edianniu.pscp.cs.bean.TimeUnit;
import com.edianniu.pscp.cs.bean.inspectinglog.InspectingLogInfo;
import com.edianniu.pscp.cs.bean.room.vo.RoomVO;
import com.edianniu.pscp.cs.bean.safetyequipment.ListReqData;
import com.edianniu.pscp.cs.bean.safetyequipment.SafetyEquipmentInfo;
import com.edianniu.pscp.cs.bean.safetyequipment.SaveReqData;
import com.edianniu.pscp.cs.bean.safetyequipment.vo.SafetyEquipmentVO;
import com.edianniu.pscp.cs.commons.Constants;
import com.edianniu.pscp.cs.commons.PageResult;
import com.edianniu.pscp.cs.dao.InspectingLogDao;
import com.edianniu.pscp.cs.dao.RoomDao;
import com.edianniu.pscp.cs.dao.SafetyEquipmentDao;
import com.edianniu.pscp.cs.service.SafetyEquipmentService;
import com.edianniu.pscp.cs.util.DateUtils;
import com.edianniu.pscp.mis.bean.user.UserInfo;

@Service
@Transactional
@Repository("safetyEquipmentService")
public class DefaultSafetyEquipmentService implements SafetyEquipmentService {

	private static final Logger logger = LoggerFactory.getLogger(DefaultSafetyEquipmentService.class);

	@Autowired
	private RoomDao roomDao;
	
	@Autowired
	private SafetyEquipmentDao safetyEquipmentDao;
	
	@Autowired
	private InspectingLogDao inspectingLogDao;
	
	/**
	 * 设备检查的宽限天数
	 */
	@Value(value = "${check.grace.time:1}")
	private Integer graceTime;
	
	/**
	 * 安全用具保存
	 */
	@Override
	public void saveSafetyEquipment(SaveReqData saveReqData, UserInfo userInfo) {
		if (null == saveReqData || null == userInfo) {
			logger.error("参数不合法");
			return;
		}
		SafetyEquipmentInfo safetyEquipmentInfo = new SafetyEquipmentInfo();
		safetyEquipmentInfo.setRoomId(saveReqData.getRoomId());
		safetyEquipmentInfo.setCompanyId(userInfo.getCompanyId());
		safetyEquipmentInfo.setName(saveReqData.getName());
		safetyEquipmentInfo.setSerialNumber(saveReqData.getSerialNumber());
		safetyEquipmentInfo.setModelNumber(saveReqData.getModelNumber());
		safetyEquipmentInfo.setVoltageLevel(saveReqData.getVoltageLevel());
		safetyEquipmentInfo.setManufacturer(saveReqData.getManufacturer());
		Integer testCycle = saveReqData.getTestCycle();
		Integer testTimeUnit = saveReqData.getTestTimeUnit();
		safetyEquipmentInfo.setTestCycle(testCycle);
		safetyEquipmentInfo.setTestTimeUnit(testTimeUnit);
		safetyEquipmentInfo.setWaringStatus(0);
		Date initialTestDate = DateUtils.parse(saveReqData.getInitialTestDate(), DateUtils.DATE_PATTERN);
		if (null != initialTestDate) {
			initialTestDate = DateUtils.getEndDate(initialTestDate);
			safetyEquipmentInfo.setInitialTestDate(initialTestDate);
			// 新建设备时，初次检查日期与下次检查日期相同
			safetyEquipmentInfo.setNextTestDate(initialTestDate);
		}
		safetyEquipmentInfo.setRemark(saveReqData.getRemark());
		safetyEquipmentInfo.setCreateUser(userInfo.getLoginName());
		safetyEquipmentInfo.setCreateTime(new Date());
		
		safetyEquipmentDao.save(safetyEquipmentInfo);
	}

	/**
	 * 配电房安全用具列表
	 */
	@Override
	public PageResult<SafetyEquipmentVO> getSafetyEquipmentVOList(ListReqData listReqData, UserInfo userInfo) {
		PageResult<SafetyEquipmentVO> result = new PageResult<SafetyEquipmentVO>();
		
		HashMap<String, Object> queryMap = new HashMap<>();
		queryMap.put("pageSize", listReqData.getPageSize());
		queryMap.put("offset", listReqData.getOffset());
		if (null != userInfo) {
			queryMap.put("companyId", userInfo.getCompanyId());
		}
		if (listReqData.getRoomId() == null || listReqData.getRoomId() == -1) {
			queryMap.put("roomId", null);
		}else {
			queryMap.put("roomId", listReqData.getRoomId());
		}

		int total = safetyEquipmentDao.getSafetyEquipmentInfoListCount(queryMap);
		int nextOffset = 0;
		
		if (total > 0) {
			Date today = new Date();
			List<SafetyEquipmentVO> safetyEquipmentVOList = new ArrayList<>();
			List<SafetyEquipmentInfo> entityList = safetyEquipmentDao.getSafetyEquipmentInfoList(queryMap);
			if (CollectionUtils.isNotEmpty(entityList)) {
				for (SafetyEquipmentInfo safetyEquipmentInfo : entityList) {
					SafetyEquipmentVO safetyEquipmentVO = new SafetyEquipmentVO();
					safetyEquipmentVO.setId(safetyEquipmentInfo.getId());
					safetyEquipmentVO.setName(safetyEquipmentInfo.getName());
					
					Integer testCycle = safetyEquipmentInfo.getTestCycle();
					Integer testTimeUnit = safetyEquipmentInfo.getTestTimeUnit();
					Date nextTestDate = safetyEquipmentInfo.getNextTestDate();
					boolean flag = DateUtils.beforeSmallThanAfter(nextTestDate, today);
					for (;flag; ) {
						nextTestDate = DateUtils.dateAddTime(nextTestDate, testCycle, testTimeUnit);
						flag = DateUtils.beforeSmallThanAfter(nextTestDate, today);
					}
					safetyEquipmentVO.setNextTestDate(DateUtils.format(nextTestDate, DateUtils.DATE_PATTERN));
					
					Long roomId = safetyEquipmentInfo.getRoomId();
					RoomVO roomVO = roomDao.queryRoomVO(roomId);
					if (null != roomVO) {
						safetyEquipmentVO.setRoomName(roomVO.getName());
					}
					safetyEquipmentVOList.add(safetyEquipmentVO);
				}
			}
			
			result.setData(safetyEquipmentVOList);
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

	/**
	 * 安全用具详情
	 */
	@Override
	public SafetyEquipmentVO getSafetyEquipmentDetails(Long id) {
		if (null == id) {
			return null;
		}
		// 查询设备详情
		Date today = new Date();
		SafetyEquipmentVO safetyEquipmentVO = new SafetyEquipmentVO();
		SafetyEquipmentInfo safetyEquipmentInfo = safetyEquipmentDao.getSafetyEquipmentInfo(id);
		if (null == safetyEquipmentInfo) {
			return null;
		}
		safetyEquipmentVO.setId(safetyEquipmentInfo.getId());
		safetyEquipmentVO.setName(safetyEquipmentInfo.getName());
		Long roomId = safetyEquipmentInfo.getRoomId();
		safetyEquipmentVO.setRoomId(roomId);
		RoomVO roomVO = roomDao.queryRoomVO(roomId);
		if (null != roomVO) {
			safetyEquipmentVO.setRoomName(roomVO.getName());
		}
		safetyEquipmentVO.setSerialNumber(safetyEquipmentInfo.getSerialNumber());
		safetyEquipmentVO.setModelNumber(safetyEquipmentInfo.getModelNumber());
		safetyEquipmentVO.setVoltageLevel(safetyEquipmentInfo.getVoltageLevel());
		safetyEquipmentVO.setManufacturer(safetyEquipmentInfo.getManufacturer());
		Integer testCycle = safetyEquipmentInfo.getTestCycle();
		Integer testTimeUnit = safetyEquipmentInfo.getTestTimeUnit();
		safetyEquipmentVO.setTestCycle(testCycle);
		safetyEquipmentVO.setTestTimeUnit(testTimeUnit);
		safetyEquipmentVO.setInitialTestDate(DateUtils.format(safetyEquipmentInfo.getInitialTestDate(), DateUtils.DATE_PATTERN));
		
		Date nextTestDate = safetyEquipmentInfo.getNextTestDate();
		int daysBetween = DateUtils.daysBetween(today, nextTestDate);
		// 如果nextTestDate比today小，说明至少有一次没有去检查
		if (daysBetween < 0) {
			List<Date> nextTestDateList = new ArrayList<Date>();
			// 将nextTestDate添加到List集合，因为nextTestDate即是第一次没有去检查的日期
			nextTestDateList.add(nextTestDate);
			for (; daysBetween < 0; ) {
				nextTestDate = DateUtils.dateAddTime(nextTestDate, testCycle, testTimeUnit);
				Date nextDate = new Date();
				nextDate = nextTestDate;
				nextTestDateList.add(nextDate);
				daysBetween = DateUtils.daysBetween(today, nextTestDate);
			}
			
			// 将最后一个生成的nextDate剔除，因为最后一个nextDate是即将到来的规定检查日期
			nextTestDateList.remove(nextTestDateList.size()-1);
			// 系统后台批量插入没有检查的记录
			List<InspectingLogInfo> infos = new ArrayList<>();
			for (Date next : nextTestDateList) {
				InspectingLogInfo info = new InspectingLogInfo();
				info.setEquipmentId(safetyEquipmentInfo.getId());
				info.setType(CustomerEquipmentType.SAFETY_EQUIPMENT.getValue());
				info.setCompanyId(0L);
				info.setStatus(Constants.TAG_NO);
				info.setFixTestTime(next);
				info.setCreateUser("系统");
				info.setCreateTime(today);
				infos.add(info);
			}
			inspectingLogDao.saveBatch(infos);
				
			// 将该设备的nextTestDate更新为最后生成的一个nextTestDate
			safetyEquipmentInfo.setNextTestDate(nextTestDate);
			safetyEquipmentInfo.setModifiedUser("系统");
			safetyEquipmentInfo.setModifiedTime(today);
			safetyEquipmentDao.update(safetyEquipmentInfo);	
		}
		
		safetyEquipmentVO.setNextTestDate(DateUtils.format(nextTestDate, DateUtils.DATE_PATTERN));
		Date lastTestDate = null;
		// 如果下一次检查日期与初次检查日期相同，说明是第一次添加设备，则上一次检查日期为空
		if (DateUtils.sameDate(safetyEquipmentInfo.getInitialTestDate(), nextTestDate)) {
			lastTestDate = null;
		} else {
			lastTestDate = DateUtils.dateAddTime(nextTestDate, (-1 * testCycle), testTimeUnit);
		}
		safetyEquipmentVO.setLastTestDate(DateUtils.format(lastTestDate, DateUtils.DATE_PATTERN));
		safetyEquipmentVO.setRemark(safetyEquipmentInfo.getRemark());
		
		// 如果检查周期与宽限时间相同的话，则无宽限
		if (TimeUnit.DAY.getValue() == testTimeUnit  && graceTime == testCycle) {
			graceTime = 0;
		} 
		
		if (daysBetween <= graceTime && daysBetween >= 0) {
			safetyEquipmentVO.setClickStatus(Constants.TAG_YES);
		} else {
			safetyEquipmentVO.setClickStatus(Constants.TAG_NO);
		}
		
		// 向用户展示设备详情
		return safetyEquipmentVO;
	}

	/**
	 * 安全用具删除
	 */
	@Override
	public void deleteSafetyEquipment(Long id, UserInfo userInfo) {
		if (null != id && null != userInfo) {
			SafetyEquipmentInfo safetyEquipmentInfo = new SafetyEquipmentInfo();
			safetyEquipmentInfo.setId(id);
			safetyEquipmentInfo.setDeleted(-1);
			safetyEquipmentInfo.setModifiedUser(userInfo.getLoginName());
			safetyEquipmentInfo.setModifiedTime(new Date());
			safetyEquipmentDao.update(safetyEquipmentInfo);
		}
	}
	
	// 判断配电房是否存在安全用具
	@Override
	public Boolean isExistByRoomIdAndCompanyId(Long roomId, Long companyId){
		HashMap<String, Object> map = new HashMap<>();
		map.put("roomId", roomId);
		map.put("companyId", companyId);
		int count = safetyEquipmentDao.getSafetyEquipmentInfoListCount(map);
		return (count > 0) ? true : false ;
	}

	/**
	 * 扫描进入检查周期范围内的设备
	 */
	@Override
	public List<Long> scanCanCheckSafetyEquipments() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("graceTime", graceTime);
		map.put("waringStatus", 0);
		return safetyEquipmentDao.scanCanCheckSafetyEquipments(map);
	}

	/**
	 * 处理进入检查周期范围内的设备
	 */
	@Override
	public int handleCanCheckSafetyEquipment(Long id) {
		SafetyEquipmentInfo safetyEquipmentInfo = new SafetyEquipmentInfo();
		safetyEquipmentInfo.setId(id);
		safetyEquipmentInfo.setWaringStatus(Constants.TAG_YES);
		safetyEquipmentInfo.setModifiedTime(new Date());
		safetyEquipmentInfo.setModifiedUser("系统");
		return safetyEquipmentDao.update(safetyEquipmentInfo);
	}

	/**
	 * 扫描所有到达检查截止日期还未检查的安全用具
	 */
	@Override
	public List<Long> scanNoCheckedSafetyEquipments() {
		return safetyEquipmentDao.scanNoCheckedSafetyEquipments();
	}

	/**
	 * 处理到达检查截止日期还未检查的设备
	 */
	@Override
	public int handleNoCheckSafetyEquipment(Long id) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

}
