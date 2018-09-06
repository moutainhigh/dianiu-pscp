package com.edianniu.pscp.mis.service;

import com.edianniu.pscp.mis.domain.ElectricianWorkLog;
import com.edianniu.pscp.mis.domain.ElectricianWorkLogAttachment;

import java.util.List;

/**
 * ClassName: ElectricianWorkLogService
 * Author: tandingbo
 * CreateTime: 2017-04-16 18:49
 */
public interface ElectricianWorkLogService {
    /**
     * 保存日志及附件信息
     *
     * @param workLog
     * @param listAttachment
     * @return
     */
    Integer saveEntity(ElectricianWorkLog workLog, List<ElectricianWorkLogAttachment> listAttachment);

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
    Integer deleteEntity(ElectricianWorkLog electricianWorkLog);

    /**
     * 获取用户所有工作记录
     *
     * @param searchEntity
     * @return
     */
    List<ElectricianWorkLog> getAllEntity(ElectricianWorkLog searchEntity);
}
