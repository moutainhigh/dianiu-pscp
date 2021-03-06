package com.edianniu.pscp.portal.service;

import java.util.List;
import java.util.Map;

import com.edianniu.pscp.portal.entity.ToolEquipmentEntity;
import com.edianniu.pscp.portal.utils.R;

public interface ToolEquipmentService {
	
	ToolEquipmentEntity queryObject(Long id);
	
	List<ToolEquipmentEntity> queryList(Map<String,Object>map);
	
	int queryTotal(Map<String,Object>map);
	
	R save(ToolEquipmentEntity bean);
	
	R update(ToolEquipmentEntity bean);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
	
	public ToolEquipmentEntity getToolById(Long id);
}
