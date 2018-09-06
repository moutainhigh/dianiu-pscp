package com.edianniu.pscp.sps.service;

import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.sps.bean.project.ProjectInfo;
import com.edianniu.pscp.sps.bean.project.vo.ProjectVO;
import com.edianniu.pscp.sps.domain.EngineeringProject;

import java.util.List;
import java.util.Map;

/**
 * ClassName: EngineeringProjectService
 * Author: tandingbo
 * CreateTime: 2017-05-15 16:40
 */
public interface EngineeringProjectService {
    List<EngineeringProject> getListByCompanyIdAndCustomerId(Long companyId, Long customerId, boolean includeExpire);

    ProjectInfo getById(Long id);
    
    boolean existByCustomerId(Long customerId);
    
    boolean existByCompanyIdAndName(Long companyId,String name);
    
    boolean existByCompanyIdAndIdAndName(Long companyId,Long id,String name);

    List<ProjectVO> selectProjectVOByCompanyIdAndAndCustomerId(Long companyId, Long customerId, boolean includeExpire);

    ProjectInfo getById(Long id, boolean hasCustomer);

    List<ProjectInfo> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(ProjectInfo projectInfo, UserInfo userInfo);

    void update(ProjectInfo projectInfo, UserInfo userInfo);

    int delete(Long id);

    int deleteBatch(Long[] ids);

    ProjectInfo getProjectInfoByNeedsId(Map<String, Object> map);

	void settle(ProjectInfo projectInfo, UserInfo userInfo);

	void batchUpdatePayment(List<ProjectInfo> projectList);

	

}
