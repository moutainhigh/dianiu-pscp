package com.edianniu.pscp.mis.service;

import com.edianniu.pscp.mis.domain.ElectricianResume;

/**
 * ClassName: ElectricianResumeService
 * Author: tandingbo
 * CreateTime: 2017-04-19 16:04
 */
public interface ElectricianResumeService {
    /**
     * 根据用户ID获取简历信息
     *
     * @param uid
     * @return
     */
    ElectricianResume getEntityByUid(Long uid);

    /**
     * 根据主键ID获取简历信息
     *
     * @param id
     * @return
     */
    ElectricianResume getEntityById(Long id);

    /**
     * 更新简历信息
     *
     * @param entity
     * @return
     */
    Integer updateEntity(ElectricianResume entity);

    /**
     * 新增简历
     *
     * @param entity
     * @return
     */
    int saveEntity(ElectricianResume entity);
}
