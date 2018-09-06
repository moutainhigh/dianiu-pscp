package com.edianniu.pscp.cs.dao;

import java.util.HashMap;
import java.util.List;

import com.edianniu.pscp.cs.bean.firefightingequipment.FirefightingEquipmentInfo;

public interface FirefightingEquipmentDao extends BaseDao<FirefightingEquipmentInfo>{

	int getFirefightingEquipmentInfoListCount(HashMap<String, Object> queryMap);

	List<FirefightingEquipmentInfo> getFirefightingEquipmentInfoList(HashMap<String, Object> queryMap);

	FirefightingEquipmentInfo getFirefightingEquipmentInfo(Long id);

	List<Long> scanCanCheckSafetyEquipments(HashMap<String, Object> map);

	

}
