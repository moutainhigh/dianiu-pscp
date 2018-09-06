package com.edianniu.pscp.cs.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.edianniu.pscp.cs.bean.equipment.DeleteResult;
import com.edianniu.pscp.cs.bean.equipment.EquipmentInfo;
import com.edianniu.pscp.cs.bean.equipment.ListReqData;
import com.edianniu.pscp.cs.bean.equipment.SaveReqData;
import com.edianniu.pscp.cs.bean.equipment.SaveResult;
import com.edianniu.pscp.cs.bean.equipment.vo.EquipmentVO;
import com.edianniu.pscp.cs.bean.room.vo.RoomVO;
import com.edianniu.pscp.cs.commons.PageResult;
import com.edianniu.pscp.cs.dao.EquipmentDao;
import com.edianniu.pscp.cs.dao.RoomDao;
import com.edianniu.pscp.cs.service.EquipmentService;
import com.edianniu.pscp.cs.util.DateUtils;
import com.edianniu.pscp.mis.bean.user.UserInfo;

@Service
@Repository("equipmentService")
@Transactional
public class DefaultEquipmentService implements EquipmentService {

	@Autowired
	EquipmentDao equipmentDao; 
	
	@Autowired
	RoomDao roomDao;
	
	//保存、编辑设备信息
	@Override
	public SaveResult save(SaveReqData saveReqData, UserInfo userInfo) {
		SaveResult result = new SaveResult();
		
		//封装设备信息
		EquipmentInfo customerEquipmentInfo = new EquipmentInfo();
		customerEquipmentInfo.setRoomId(saveReqData.getRoomId());
		if (null != userInfo) {
			customerEquipmentInfo.setCompanyId(userInfo.getCompanyId());
		}
		customerEquipmentInfo.setName(saveReqData.getName());
		customerEquipmentInfo.setModel(saveReqData.getModel());
		customerEquipmentInfo.setRatedVoltage(saveReqData.getRatedVoltage());
		customerEquipmentInfo.setRatedCurrent(saveReqData.getRatedCurrent());
		customerEquipmentInfo.setRatedBreakingCurrent(saveReqData.getRatedBreakingCurrent());
		customerEquipmentInfo.setRatedCapacity(saveReqData.getRatedCapacity());
		customerEquipmentInfo.setMaxWorkingVoltage(saveReqData.getMaxWorkingVoltage());
		customerEquipmentInfo.setCurrentTransformerRatio(saveReqData.getCurrentTransformerRatio());
		customerEquipmentInfo.setVoltageTransformerRatio(saveReqData.getVoltageTransformerRatio());
		Date productionDate = DateUtils.parse(saveReqData.getProductionDate(), DateUtils.DATE_PATTERN);
		if (productionDate != null) {
			customerEquipmentInfo.setProductionDate(productionDate);
		}
		customerEquipmentInfo.setSerialNumber(saveReqData.getSerialNumber());
		customerEquipmentInfo.setManufacturer(saveReqData.getManufacturer());
		
		//如果设备ID为空，新建设备
		Long equipmentId = saveReqData.getId();
		if (equipmentId == null) {
			customerEquipmentInfo.setCreateUser(userInfo.getLoginName());
			equipmentDao.saveEquipment(customerEquipmentInfo);
		}else { //如果设备ID不为空，编辑设备
			customerEquipmentInfo.setId(equipmentId);
			customerEquipmentInfo.setModifiedUser(userInfo.getLoginName());
			equipmentDao.updateEquipment(customerEquipmentInfo);
		}
		return result;
	}

	// 查询配电房设备列表
	@Override
	public PageResult<EquipmentVO> getEquipmentVOList(ListReqData listReqData, UserInfo userInfo) {
		PageResult<EquipmentVO> result = new PageResult<>();
		
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
		
		int total = equipmentDao.getEquipmentVOListCount(queryMap);
		int nextOffset = 0;
		
		if (total > 0) {
			List<EquipmentInfo> entityList = equipmentDao.getEquipmentList(queryMap);
			List<EquipmentVO> equipmentVOList = new ArrayList<EquipmentVO>();
			for (EquipmentInfo customerEquipmentInfo : entityList) {
				EquipmentVO equipmentVO = new EquipmentVO();
				equipmentVO.setId(customerEquipmentInfo.getId());
				equipmentVO.setName(customerEquipmentInfo.getName());
				Long roomId = customerEquipmentInfo.getRoomId();
				RoomVO roomVO = roomDao.queryRoomVO(roomId);
				if (roomVO != null) {
					equipmentVO.setRoomName(roomVO.getName());
				}
				equipmentVOList.add(equipmentVO);
			}
			
			result.setData(equipmentVOList);
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

	//配电房设备详情
	@Override
	public EquipmentVO getEquipmentVO(Long id) {
		if (null == id) {
			return null;
		}
		EquipmentVO equipmentVO = new EquipmentVO();
		EquipmentInfo equipmentInfo = equipmentDao.getEquipmentInfo(id);
		if (null == equipmentInfo) {
			return null;
		}
		equipmentVO.setId(equipmentInfo.getId());
		equipmentVO.setName(equipmentInfo.getName());
		Long roomId = equipmentInfo.getRoomId();
		equipmentVO.setRoomId(roomId);
		RoomVO roomVO = roomDao.queryRoomVO(roomId);
		if (roomVO != null) {
			equipmentVO.setRoomName(roomVO.getName());
		}
		equipmentVO.setModel(equipmentInfo.getModel());
		equipmentVO.setRatedVoltage(equipmentInfo.getRatedVoltage());
		equipmentVO.setRatedCurrent(equipmentInfo.getRatedCurrent());
		equipmentVO.setRatedBreakingCurrent(equipmentInfo.getRatedBreakingCurrent());
		equipmentVO.setRatedCapacity(equipmentInfo.getRatedCapacity());
		equipmentVO.setMaxWorkingVoltage(equipmentInfo.getMaxWorkingVoltage());
		equipmentVO.setCurrentTransformerRatio(equipmentInfo.getCurrentTransformerRatio());
		equipmentVO.setVoltageTransformerRatio(equipmentInfo.getVoltageTransformerRatio());
		equipmentVO.setProductionDate(DateUtils.format(equipmentInfo.getProductionDate(), DateUtils.DATE_PATTERN));
		equipmentVO.setSerialNumber(equipmentInfo.getSerialNumber());
		equipmentVO.setManufacturer(equipmentInfo.getManufacturer());
		
		return equipmentVO;
	}

	//删除配电房设备
	@Override
	public DeleteResult deleteEquipment(Long id, UserInfo userInfo) {
		DeleteResult result = new DeleteResult();
		if (null != id) {
			EquipmentInfo equipmentInfo = new EquipmentInfo();
			equipmentInfo.setId(id);
			equipmentInfo.setDeleted(-1);
			equipmentInfo.setModifiedUser(userInfo.getLoginName());
			
			equipmentDao.updateEquipment(equipmentInfo);
		}
		return result;
	}
	
	// 判断配电房是否存在设备
	@Override
	public Boolean isExistByRoomIdAndCompanyId(Long roomId, Long companyId){
		HashMap<String, Object> map = new HashMap<>();
		map.put("roomId", roomId);
		map.put("companyId", companyId);
		int count = equipmentDao.getEquipmentVOListCount(map);
		return (count > 0) ? true : false ;
	}
	
}
