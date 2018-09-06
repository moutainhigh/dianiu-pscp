package com.edianniu.pscp.sps.service.impl;

import com.edianniu.pscp.sps.dao.ElectricianWorkExperienceDao;
import com.edianniu.pscp.sps.domain.ElectricianWorkExperience;
import com.edianniu.pscp.sps.service.ElectricianWorkExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service("electricianWorkExperienceService")
public class DefaultElectricianWorkExperienceServiceImpl implements ElectricianWorkExperienceService {
    @Autowired
    private ElectricianWorkExperienceDao electricianWorkExperienceDao;

    @Override
    public ElectricianWorkExperience queryObject(Long id) {
        return electricianWorkExperienceDao.queryObject(id);
    }

    @Override
    public List<ElectricianWorkExperience> queryList(Map<String, Object> map) {
        return electricianWorkExperienceDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return electricianWorkExperienceDao.queryTotal(map);
    }

    @Override
    @Transactional
    public void save(ElectricianWorkExperience electricianWorkExperience) {
        electricianWorkExperienceDao.save(electricianWorkExperience);
    }

    @Override
    @Transactional
    public void update(ElectricianWorkExperience electricianWorkExperience) {
        electricianWorkExperienceDao.update(electricianWorkExperience);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        electricianWorkExperienceDao.delete(id);
    }

    @Override
    @Transactional
    public void deleteBatch(Long[] ids) {
        electricianWorkExperienceDao.deleteBatch(ids);
    }

    @Override
    public List<ElectricianWorkExperience> getAllListByResumeId(Long resumeId) {
        return electricianWorkExperienceDao.getAllListByResumeId(resumeId);
    }

}
