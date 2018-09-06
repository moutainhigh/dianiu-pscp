package com.edianniu.pscp.cs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edianniu.pscp.cs.dao.BaseDao;
import com.edianniu.pscp.cs.domain.CommonAttachment;

public interface CommonAttachmentDao extends BaseDao<CommonAttachment>{

	void saveBatch(List<CommonAttachment> commonAttachments);
	
	//int delete(String removedImg);

	//根据业务外键和业务类型查询附件
	List<CommonAttachment> select(@Param("foreignKey")Long foreignKey, @Param("businessType")Integer businessType);
	
	
	
	
}
