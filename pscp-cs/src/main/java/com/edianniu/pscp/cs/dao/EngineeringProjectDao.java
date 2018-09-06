package com.edianniu.pscp.cs.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edianniu.pscp.cs.bean.engineeringproject.EngineerProjectInfo;
import com.edianniu.pscp.cs.bean.engineeringproject.vo.EngineeringProjectVO;
import com.edianniu.pscp.cs.domain.EngineeringProject;

/**
 * EngineeringProjectDao
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-05 16:30:15
 */
public interface EngineeringProjectDao extends BaseDao<EngineeringProject> {
	
	//创建需求
	void createProject(EngineerProjectInfo engineerProjectInfo);
	
	//查询项目
	EngineeringProjectVO queryProjectInfo(HashMap<String, Object> map);

	//获得列表总条数
	Integer projectListCount(HashMap<String, Object> queryMap);

	//获得列表
	List<EngineeringProjectVO> queryProjectList(HashMap<String, Object> queryMap);
	
	//修改项目
	void updateProject(HashMap<String, Object> updateMap);
	
	//获取项目IDs
	List<Long> getProjectId(@Param("uid")Long uid);
	
	
}
