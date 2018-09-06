package com.edianniu.pscp.sps.dao;

import com.edianniu.pscp.sps.domain.ElectricianWorkExperience;

import java.util.List;

/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-24 15:18:21
 */
public interface ElectricianWorkExperienceDao extends BaseDao<ElectricianWorkExperience> {

    List<ElectricianWorkExperience> getAllListByResumeId(Long resumeId);
}
