package com.edianniu.pscp.mis.dao;

import com.edianniu.pscp.mis.domain.ElectricianWorkLog;

import java.util.List;

/**
 * ClassName: ElectricianWorkLogDao
 * Author: tandingbo
 * CreateTime: 2017-04-16 18:49
 */
public interface ElectricianWorkLogDao {
    /**
     * 保存日志信息
     *
     * @param workLog
     * @return
     */
    Integer save(ElectricianWorkLog workLog);

    /**
     * 根据主键ID获取工作记录信息
     *
     * @param id
     * @return
     */
    ElectricianWorkLog getEntityById(Long id);

    /**
     * 删除工作记录
     *
     * @param electricianWorkLog
     * @return
     */
    Integer delete(ElectricianWorkLog electricianWorkLog);

    /**
     * 获取用户所有工作记录
     *
     * @param searchEntity
     * @return
     */
    List<ElectricianWorkLog> getAllEntity(ElectricianWorkLog searchEntity);
}
