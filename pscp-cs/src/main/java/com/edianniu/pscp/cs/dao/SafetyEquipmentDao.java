package com.edianniu.pscp.cs.dao;

import java.util.HashMap;
import java.util.List;

import com.edianniu.pscp.cs.bean.safetyequipment.SafetyEquipmentInfo;

public interface SafetyEquipmentDao extends BaseDao<SafetyEquipmentInfo>{

	int getSafetyEquipmentInfoListCount(HashMap<String, Object> queryMap);

	List<SafetyEquipmentInfo> getSafetyEquipmentInfoList(HashMap<String, Object> queryMap);

	SafetyEquipmentInfo getSafetyEquipmentInfo(Long id);

	List<Long> scanCanCheckSafetyEquipments(HashMap<String, Object> map);

	List<Long> scanNoCheckedSafetyEquipments();

}
