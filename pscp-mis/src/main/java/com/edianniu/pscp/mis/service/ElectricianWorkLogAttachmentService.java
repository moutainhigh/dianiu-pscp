package com.edianniu.pscp.mis.service;

import com.edianniu.pscp.mis.domain.ElectricianWorkLogAttachment;

import java.util.List;

/**
 * ClassName: ElectricianWorkLogAttachmentService
 * Author: tandingbo
 * CreateTime: 2017-04-16 18:50
 */
public interface ElectricianWorkLogAttachmentService {
    /**
     * 根据主键ID获取工作记录附件信息
     *
     * @param id
     * @return
     */
    ElectricianWorkLogAttachment getEntityById(Long id);

    /**
     * 删除工作记录附件
     *
     * @param workLogAttachment
     * @return
     */
    Integer delete(ElectricianWorkLogAttachment workLogAttachment);

    /**
     * 根据工作记录ID获取附件信息
     *
     * @param workLogId
     * @return
     */
    List<ElectricianWorkLogAttachment> getAllEntity(Long workLogId, Integer offset, Integer limit);
}
