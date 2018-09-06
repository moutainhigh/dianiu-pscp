package com.edianniu.pscp.mis.dao;

import com.edianniu.pscp.mis.domain.EngineeringProject;

/**
 * ClassName: EngineeringProjectDao
 * Author: tandingbo
 * CreateTime: 2017-04-18 10:06
 */
public interface EngineeringProjectDao {
    /**
     * 根据主键ID获取项目信息
     *
     * @param id
     * @return
     */
    EngineeringProject getById(Long id);
    /**
     * 根据主键Id查询roomIds
     * @param id
     * @return
     */
    String getRoomIdsById(Long id);
    /**
     * 根据主键projectNo查询项目信息
     * @param projectNo
     * @return
     */
    EngineeringProject getByProjectNo(String projectNo);
    
    int update(EngineeringProject engineeringProject);
}
