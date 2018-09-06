package com.edianniu.pscp.mis.service;

import com.edianniu.pscp.mis.domain.ElectricianWorkExperience;

import java.util.List;

/**
 * ClassName: ElectricianWorkExperienceService
 * Author: tandingbo
 * CreateTime: 2017-04-19 15:57
 */
public interface ElectricianWorkExperienceService {
    /**
     * 根据简历ID获取用户工作经历信息
     *
     * @param resumeId
     * @return
     */
    List<ElectricianWorkExperience> getAllListByResumeId(Long resumeId);

    /**
     * 保存工作经历
     *
     * @param entity
     * @return
     */
    Integer saveEntity(ElectricianWorkExperience entity);

    /**
     * 修改工作经历
     *
     * @param entity
     * @return
     */
    Integer updateEntity(ElectricianWorkExperience entity);

    /**
     * 根据ID获取工作经历信息
     *
     * @param id
     * @param resumeId
     * @return
     */
    ElectricianWorkExperience getEntityById(Long id, Long resumeId);

    /**
     * 公司名称重复检查
     *
     *
     * @param id
     * @param resumeId
     * @param companyName
     * @return
     */
    int selectCountByCompanyName(Long id, Long resumeId, String companyName);
}
