package com.edianniu.pscp.mis.service.impl;

import com.edianniu.pscp.mis.dao.ElectricianWorkExperienceDao;
import com.edianniu.pscp.mis.domain.ElectricianWorkExperience;
import com.edianniu.pscp.mis.service.ElectricianWorkExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ClassName: DefaultElectricianWorkExperienceService
 * Author: tandingbo
 * CreateTime: 2017-04-19 15:57
 */
@Service
@Repository("electricianWorkExperienceService")
public class DefaultElectricianWorkExperienceService implements ElectricianWorkExperienceService {

    @Autowired
    private ElectricianWorkExperienceDao electricianWorkExperienceDao;

    /**
     * 根据简历ID获取用户工作经历信息
     *
     * @param resumeId
     * @return
     */
    @Override
    public List<ElectricianWorkExperience> getAllListByResumeId(Long resumeId) {
        return electricianWorkExperienceDao.getAllListByResumeId(resumeId);
    }

    /**
     * 保存工作经历
     *
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public Integer saveEntity(ElectricianWorkExperience entity) {
        return electricianWorkExperienceDao.saveEntity(entity);
    }

    /**
     * 修改工作经历
     *
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public Integer updateEntity(ElectricianWorkExperience entity) {
        return electricianWorkExperienceDao.updateEntity(entity);
    }

    /**
     * 根据ID获取工作经历信息
     *
     * @param id
     * @param resumeId
     * @return
     */
    @Override
    public ElectricianWorkExperience getEntityById(Long id, Long resumeId) {
        return electricianWorkExperienceDao.getEntityById(id, resumeId);
    }

    /**
     * 公司名称重复检查
     *
     * @param id
     * @param resumeId
     * @param companyName
     * @return
     */
    @Override
    public int selectCountByCompanyName(Long id, Long resumeId, String companyName) {
        return electricianWorkExperienceDao.selectCountByCompanyName(id, resumeId, companyName);
    }
}
