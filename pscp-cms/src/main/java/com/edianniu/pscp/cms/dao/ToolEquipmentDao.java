
package com.edianniu.pscp.cms.dao;

import com.edianniu.pscp.cms.entity.ToolEquipmentEntity;

public interface ToolEquipmentDao extends BaseDao<ToolEquipmentEntity>{
	
	public ToolEquipmentEntity getToolById(Long id);
	

}
