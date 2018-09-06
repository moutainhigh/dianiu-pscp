package com.edianniu.pscp.sps.service;

import com.edianniu.pscp.sps.domain.ElectricianWorkExperience;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-24 15:18:21
 */
public interface ElectricianWorkExperienceService {

    ElectricianWorkExperience queryObject(Long id);

    List<ElectricianWorkExperience> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(ElectricianWorkExperience electricianWorkExperience);

    void update(ElectricianWorkExperience electricianWorkExperience);

    void delete(Long id);

    void deleteBatch(Long[] ids);

    List<ElectricianWorkExperience> getAllListByResumeId(Long resumeId);
}
