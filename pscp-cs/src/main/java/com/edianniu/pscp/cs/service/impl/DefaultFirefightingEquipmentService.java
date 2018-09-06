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
import com.edianniu.pscp.cs.bean.firefightingequipment.FirefightingEquipmentInfo;
import com.edianniu.pscp.cs.bean.firefightingequipment.ListReqData;
import com.edianniu.pscp.cs.bean.firefightingequipment.SaveReqData;
import com.edianniu.pscp.cs.bean.firefightingequipment.vo.FirefightingEquipmentVO;
import com.edianniu.pscp.cs.bean.inspectinglog.InspectingLogInfo;
import com.edianniu.pscp.cs.bean.room.vo.RoomVO;
import com.edianniu.pscp.cs.commons.Constants;
import com.edianniu.pscp.cs.commons.PageResult;
import com.edianniu.pscp.cs.dao.FirefightingEquipmentDao;
import com.edianniu.pscp.cs.dao.InspectingLogDao;
import com.edianniu.pscp.cs.dao.RoomDao;
import com.edianniu.pscp.cs.service.FirefightingEquipmentService;
import com.edianniu.pscp.cs.util.DateUtils;
import com.edianniu.pscp.mis.bean.user.UserInfo;

@Service
@Transactional
@Repository("firefightingEquipmentService")
public class DefaultFirefightingEquipmentService implements FirefightingEquipmentService {

	private static final Logger logger = LoggerFactory.getLogger(DefaultFirefightingEquipmentService.class);
	
	@Autowired
	private RoomDao roomDao;
	
	@Autowired
	private InspectingLogDao inspectingLogDao;
	
	@Autowired
	private FirefightingEquipmentDao firefightingEquipmentDao;
	
	/**
	 * 设备检查的宽限天数
	 */
	@Value(value = "${check.grace.time:1}")
	private Integer graceTime;

	/**
	 * 消防设施保存
	 */
	@Override
	public void saveFirefightingEquipment(SaveReqData saveReqData, UserInfo userInfo) {
		if (null == saveReqData || null == userInfo) {
			logger.error("参数不合法");
			return;
		}
		FirefightingEquipmentInfo firefightingEquipmentInfo = new FirefightingEquipmentInfo();
		firefightingEquipmentInfo.setRoomId(saveReqData.getRoomId());
		firefightingEquipmentInfo.setCompanyId(userInfo.getCompanyId());
		firefightingEquipmentInfo.setName(saveReqData.getName());
		firefightingEquipmentInfo.setBoxNumber(saveReqData.getBoxNumber());
		firefightingEquipmentInfo.setSerialNumber(saveReqData.getSerialNumber());
		firefightingEquipmentInfo.setPlacementPosition(saveReqData.getPlacementPosition());
		firefightingEquipmentInfo.setSpecifications(saveReqData.getSpecifications());
		firefightingEquipmentInfo.setIndoorOrOutdoor(saveReqData.getIndoorOrOutdoor());
		Date productionDate = DateUtils.parse(saveReqData.getProductionDate(), DateUtils.DATE_PATTERN);
		Date expiryDate = DateUtils.parse(saveReqData.getExpiryDate(), DateUtils.DATE_PATTERN);
		if (null != productionDate) {
			firefightingEquipmentInfo.setProductionDate(productionDate);
		}
		if (null != expiryDate) {
			firefightingEquipmentInfo.setExpiryDate(expiryDate);
		}
		Integer viewCycle = saveReqData.getViewCycle();
		Integer viewTimeUnit = saveReqData.getViewTimeUnit();
		firefightingEquipmentInfo.setViewCycle(viewCycle);
		firefightingEquipmentInfo.setViewTimeUnit(viewTimeUnit);
		firefightingEquipmentInfo.setWaringStatus(0);
		Date initialTestDate = DateUtils.parse(saveReqData.getInitialTestDate(), DateUtils.DATE_PATTERN);
		if (null != initialTestDate) {
			initialTestDate = DateUtils.getEndDate(initialTestDate);
			firefightingEquipmentInfo.setInitialTestDate(initialTestDate);
			// 新建设备时，初次检查日期与下次检查日期相同
			firefightingEquipmentInfo.setNextTestDate(initialTestDate);
		}
		firefightingEquipmentInfo.setCreateUser(userInfo.getLoginName());
		firefightingEquipmentInfo.setCreateTime(new Date());
		
		firefightingEquipmentDao.save(firefightingEquipmentInfo);
	}

	/**
	 * 消防设施列表
	 */
	@Override
	public PageResult<FirefightingEquipmentVO> getFirefightingEquipmentVOList(ListReqData listReqData, UserInfo userInfo) {
		PageResult<FirefightingEquipmentVO> result = new PageResult<FirefightingEquipmentVO>();
		
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

		int total = firefightingEquipmentDao.getFirefightingEquipmentInfoListCount(queryMap);
		int nextOffset = 0;
		
		if (total > 0) {
			Date today = new Date();
			List<FirefightingEquipmentVO> fireFightingEquipmentVOList = new ArrayList<>();
			List<FirefightingEquipmentInfo> entityList = firefightingEquipmentDao.getFirefightingEquipmentInfoList(queryMap);
			if (CollectionUtils.isNotEmpty(entityList)) {
				for (FirefightingEquipmentInfo firefightingEquipmentInfo : entityList) {
					FirefightingEquipmentVO fightingEquipmentVO = new FirefightingEquipmentVO();
					fightingEquipmentVO.setId(firefightingEquipmentInfo.getId());
					fightingEquipmentVO.setName(firefightingEquipmentInfo.getName());
					
					Integer viewCycle = firefightingEquipmentInfo.getViewCycle();
					Integer viewTimeUnit = firefightingEquipmentInfo.getViewTimeUnit();
					Date nextTestDate = firefightingEquipmentInfo.getNextTestDate();
					boolean flag = DateUtils.beforeSmallThanAfter(nextTestDate, today);
					for (;flag; ) {
						nextTestDate = DateUtils.dateAddTime(nextTestDate, viewCycle, viewTimeUnit);
						flag = DateUtils.beforeSmallThanAfter(nextTestDate, today);
					}
					fightingEquipmentVO.setNextTestDate(DateUtils.format(nextTestDate, DateUtils.DATE_PATTERN));
					
					Long roomId = firefightingEquipmentInfo.getRoomId();
					RoomVO roomVO = roomDao.queryRoomVO(roomId);
					if (null != roomVO) {
						fightingEquipmentVO.setRoomName(roomVO.getName());
					}
					fireFightingEquipmentVOList.add(fightingEquipmentVO);
				}
			}
			
			result.setData(fireFightingEquipmentVOList);
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
	 * 消防设施详情
	 */
	@Override
	public FirefightingEquipmentVO getFirefightingEquipmentDetails(Long id) {
		if (null == id) {
			return null;
		}
		// 查询设备详情
		Date today = new Date();
		FirefightingEquipmentVO fightingEquipmentVO = new FirefightingEquipmentVO();
		FirefightingEquipmentInfo firefightingEquipmentInfo = firefightingEquipmentDao.getFirefightingEquipmentInfo(id);
		if (null == firefightingEquipmentInfo) {
			return null;
		}
		fightingEquipmentVO.setId(firefightingEquipmentInfo.getId());
		fightingEquipmentVO.setName(firefightingEquipmentInfo.getName());
		Long roomId = firefightingEquipmentInfo.getRoomId();
		RoomVO roomVO = roomDao.queryRoomVO(roomId);
		if (null != roomVO) {
			fightingEquipmentVO.setRoomId(roomId);
			fightingEquipmentVO.setRoomName(roomVO.getName());
		}
		fightingEquipmentVO.setBoxNumber(firefightingEquipmentInfo.getBoxNumber());
		fightingEquipmentVO.setSerialNumber(firefightingEquipmentInfo.getSerialNumber());
		fightingEquipmentVO.setPlacementPosition(firefightingEquipmentInfo.getPlacementPosition());
		fightingEquipmentVO.setSpecifications(firefightingEquipmentInfo.getSpecifications());
		fightingEquipmentVO.setIndoorOrOutdoor(firefightingEquipmentInfo.getIndoorOrOutdoor());
		fightingEquipmentVO.setProductionDate(DateUtils.format(firefightingEquipmentInfo.getProductionDate(), DateUtils.DATE_PATTERN));
		fightingEquipmentVO.setExpiryDate(DateUtils.format(firefightingEquipmentInfo.getExpiryDate(), DateUtils.DATE_PATTERN));
		Integer viewCycle = firefightingEquipmentInfo.getViewCycle();
		Integer viewTimeUnit = firefightingEquipmentInfo.getViewTimeUnit();
		fightingEquipmentVO.setViewCycle(viewCycle);
		fightingEquipmentVO.setViewTimeUnit(viewTimeUnit);
		fightingEquipmentVO.setInitialTestDate(DateUtils.format(firefightingEquipmentInfo.getInitialTestDate(), DateUtils.DATE_PATTERN));
		
		Date nextTestDate = firefightingEquipmentInfo.getNextTestDate();
		int daysBetween = DateUtils.daysBetween(today, nextTestDate);
		// 如果nextTestDate比today小，说明至少有一次没有去检查
		if (daysBetween < 0) {
			List<Date> nextTestDateList = new ArrayList<Date>();
			// 将nextTestDate添加到List集合，因为nextTestDate即是第一次没有去检查的日期
			nextTestDateList.add(nextTestDate);
			for (; daysBetween < 0; ) {
				nextTestDate = DateUtils.dateAddTime(nextTestDate, viewCycle, viewTimeUnit);
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
				info.setEquipmentId(firefightingEquipmentInfo.getId());
				info.setType(CustomerEquipmentType.FIREFIGHTING_EQUIPMENT.getValue());
				info.setCompanyId(0L);
				info.setStatus(Constants.TAG_NO);
				info.setFixTestTime(next);
				info.setCreateUser("系统");
				info.setCreateTime(today);
				infos.add(info);
			}
			inspectingLogDao.saveBatch(infos);
				
			// 将该设备的nextTestDate更新为最后生成的一个nextTestDate
			firefightingEquipmentInfo.setNextTestDate(nextTestDate);
			firefightingEquipmentInfo.setModifiedUser("系统");
			firefightingEquipmentInfo.setModifiedTime(today);
			firefightingEquipmentDao.update(firefightingEquipmentInfo);	
		}
				
		fightingEquipmentVO.setNextTestDate(DateUtils.format(nextTestDate, DateUtils.DATE_PATTERN));
		Date lastTestDate = null;
		// 如果下一次检查日期与初次检查日期相同，说明是第一次添加设备，则上一次检查日期为空
		if (DateUtils.sameDate(firefightingEquipmentInfo.getInitialTestDate(), nextTestDate)) {
			lastTestDate = null;
		} else {
			lastTestDate = DateUtils.dateAddTime(nextTestDate, (-1 * viewCycle), viewTimeUnit);
		}
		fightingEquipmentVO.setLastTestDate(DateUtils.format(lastTestDate, DateUtils.DATE_PATTERN));
		
		// 如果检视周期与宽限时间相同的话，则无宽限
		if (TimeUnit.DAY.getValue() == viewTimeUnit  && graceTime == viewCycle) {
			graceTime = 0;
		}
		
		if (daysBetween <= graceTime && daysBetween >= 0) {
			fightingEquipmentVO.setClickStatus(Constants.TAG_YES);
		} else {
			fightingEquipmentVO.setClickStatus(Constants.TAG_NO);
		}		
		
		// 向用户展示设备详情
		return fightingEquipmentVO;
	}

	/**
	 * 消防设施删除
	 */
	@Override
	public void deleteFirefightingEquipment(Long id, UserInfo userInfo) {
		if (null != id && null != userInfo) {
			FirefightingEquipmentInfo firefightingEquipmentInfo = new FirefightingEquipmentInfo();
			firefightingEquipmentInfo.setId(id);
			firefightingEquipmentInfo.setDeleted(-1);
			firefightingEquipmentInfo.setModifiedUser(userInfo.getLoginName());
			firefightingEquipmentInfo.setModifiedTime(new Date());
			firefightingEquipmentDao.update(firefightingEquipmentInfo);
		}
	}

	/**
	 * 扫描进入检查周期范围内的设备
	 */
	@Override
	public List<Long> scanCanCheckFirefightingEquipments() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("graceTime", graceTime);
		return firefightingEquipmentDao.scanCanCheckSafetyEquipments(map);
	}

	/**
	 * 处理进入检查周期范围内的设备
	 */
	@Override
	public int handleCanCheckFirefightingEquipment(Long id) {
		FirefightingEquipmentInfo firefightingEquipmentInfo = new FirefightingEquipmentInfo();
		firefightingEquipmentInfo.setId(id);
		firefightingEquipmentInfo.setWaringStatus(Constants.TAG_YES);
		firefightingEquipmentInfo.setModifiedTime(new Date());
		firefightingEquipmentInfo.setModifiedUser("系统");
		return firefightingEquipmentDao.update(firefightingEquipmentInfo);
	}
	
	// 判断配电房是否存在消防设施
	@Override
	public Boolean isExistByRoomIdAndCompanyId(Long roomId, Long companyId){
		HashMap<String, Object> map = new HashMap<>();
		map.put("roomId", roomId);
		map.put("companyId", companyId);
		int count = firefightingEquipmentDao.getFirefightingEquipmentInfoListCount(map);
		return (count > 0) ? true : false ;
	}
	
	
}
