package com.edianniu.pscp.cs.dao;

import java.util.HashMap;
import java.util.List;

import com.edianniu.pscp.cs.bean.equipment.EquipmentInfo;

public interface EquipmentDao {

	void saveEquipment(EquipmentInfo customerEquipmentInfo);

	void updateEquipment(EquipmentInfo customerEquipmentInfo);

	int getEquipmentVOListCount(HashMap<String, Object> queryMap);

	List<EquipmentInfo> getEquipmentList(HashMap<String, Object> queryMap);

	EquipmentInfo getEquipmentInfo(Long id);


}
