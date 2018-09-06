package com.edianniu.pscp.sps.dao;

import com.edianniu.pscp.sps.bean.project.ProjectInfo;
import com.edianniu.pscp.sps.domain.EngineeringProject;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-05 16:30:15
 */
public interface EngineeringProjectDao extends BaseDao<ProjectInfo> {
    int getCountByCustomerId(Long customerId);

    int getCountByCompanyIdAndName(@Param("companyId") Long companyId, @Param("name") String name);

    int getCountByCompanyIdAndIdAndName(@Param("companyId") Long companyId, @Param("id") Long id, @Param("name") String name);

    List<EngineeringProject> getListByCompanyIdAndCustomerId(Map<String, Object> queryMap);

    ProjectInfo getProjectInfoByNeedsId(Map<String, Object> map);

	void batchUpdatePayment(List<ProjectInfo> projectList);
}
