package com.edianniu.pscp.mis.service.impl;

import com.edianniu.pscp.mis.dao.ElectricianResumeDao;
import com.edianniu.pscp.mis.domain.ElectricianResume;
import com.edianniu.pscp.mis.service.ElectricianResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ClassName: DefaultElectricianResumeService
 * Author: tandingbo
 * CreateTime: 2017-04-19 16:04
 */
@Service
@Repository("electricianResumeService")
public class DefaultElectricianResumeService implements ElectricianResumeService {

    @Autowired
    private ElectricianResumeDao electricianResumeDao;

    /**
     * 根据用户ID获取简历信息
     *
     * @param uid
     * @return
     */
    @Override
    public ElectricianResume getEntityByUid(Long uid) {
        return electricianResumeDao.getEntityByUid(uid);
    }

    /**
     * 根据主键ID获取简历信息
     *
     * @param id
     * @return
     */
    @Override
    public ElectricianResume getEntityById(Long id) {
        return electricianResumeDao.getEntityById(id);
    }

    /**
     * 更新简历信息
     *
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public Integer updateEntity(ElectricianResume entity) {
        return electricianResumeDao.updateEntity(entity);
    }

    /**
     * 新增简历
     *
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public int saveEntity(ElectricianResume entity) {
        return electricianResumeDao.saveEntity(entity);
    }
}
