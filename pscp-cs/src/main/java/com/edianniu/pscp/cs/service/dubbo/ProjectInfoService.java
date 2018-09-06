package com.edianniu.pscp.cs.service.dubbo;

import java.util.List;

import com.edianniu.pscp.cs.bean.engineeringproject.DetailsReqData;
import com.edianniu.pscp.cs.bean.engineeringproject.DetailsResult;
import com.edianniu.pscp.cs.bean.engineeringproject.ListReqData;
import com.edianniu.pscp.cs.bean.engineeringproject.ListResult;

/**
 * 
 * @author zhoujianjian
 * 2017年9月19日下午2:47:02
 */
public interface ProjectInfoService {
	//项目列表
	ListResult projectList(ListReqData listReqData);
	//项目详情
	DetailsResult projectDetails(DetailsReqData detailsReqData);
	
	/**
	 * 客户获取 项目ID
	 * @param uid  客户uid
	 * @return
	 */
	List<Long> getProjectIds(Long uid);
}
