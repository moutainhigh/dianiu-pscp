
package com.edianniu.pscp.portal.dao;

import com.edianniu.pscp.portal.entity.ToolEquipmentEntity;

public interface ToolEquipmentDao extends BaseDao<ToolEquipmentEntity>{
	
	public ToolEquipmentEntity getToolById(Long id);
	

}
