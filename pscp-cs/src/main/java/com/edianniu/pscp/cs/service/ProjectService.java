package com.edianniu.pscp.cs.service;

import java.util.HashMap;
import java.util.List;

import com.edianniu.pscp.cs.bean.engineeringproject.DetailsResult;
import com.edianniu.pscp.cs.bean.engineeringproject.ListReqData;
import com.edianniu.pscp.cs.bean.engineeringproject.vo.EngineeringProjectVO;
import com.edianniu.pscp.cs.commons.PageResult;
import com.edianniu.pscp.mis.bean.company.CompanyInfo;

/**
 * @author zhoujianjian
 * 2017年9月19日下午2:58:47
 */
public interface ProjectService {
	
	/**
	 * 
	 * @param listReqData
	 * @param memberId  服务商所属公司的memberId
	 * @return
	 */
	PageResult<EngineeringProjectVO> listProject(ListReqData listReqData, Long memberId);

	DetailsResult projectByProjectNo(String projectNo);
	
	List<EngineeringProjectVO> queryProjectList(HashMap<String, Object> queryMap);

	List<Long> getProjectIds(Long uid);

}
